package com.monopoly_DLV_Server.DLV_Server;

import com.monopoly_DLV_Server.DLV_Server.DTO.ActionProperty;
import com.monopoly_DLV_Server.DLV_Server.DTO.Property;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

@RunWith(SpringRunner.class)
public class PayDebtTests {

    ServletCalls servletCalls = new ServletCalls();
    Comparator<ActionProperty> comparator = (o1, o2) -> {
        int result = Integer.compare(o1.getIndex(), o2.getIndex());
        if (result == 0) {
            return Integer.compare(o1.getTimes(), o2.getTimes());
        }
        return result;
    };

    @Test
    public void sellOnlyWhatYouCan() {
        Property p1 = new Property("name", 0, "false", 0, 0, 1, 200, 0, 1);
        Property p2 = new Property("name", 0, "false", 0, 0, 2, 200, 0, 2);
        Property p3 = new Property("name", 0, "true", 0, 0, 3, 400, 0, 3);
        Property p4 = new Property("name", 0, "true", 0, 0, 3, 400, 0, 4);
        ArrayList<Property> properties = new ArrayList<>(Arrays.asList(p1, p2, p3, p4));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        ArrayList<ActionProperty> result = this.servletCalls.payDebt(propertiesJson, -200);
        ArrayList<ActionProperty> expected = new ArrayList<>(Arrays.asList(new ActionProperty(1, 0), new ActionProperty(2, 0)));
        result.sort(this.comparator);
        expected.sort(this.comparator);
        for (int i = 0; i < result.size(); ++i) {
            Assert.assertEquals(expected.get(i), result.get(i));
        }
        if (result.size() == 0) {
            Assert.fail();
        }
    }

    @Test
    public void reachMoney() {
        Property p1 = new Property("name", 0, "false", 0, 0, 1, 200, 0, 1);
        ArrayList<Property> properties = new ArrayList<>(Collections.singletonList(p1));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        ArrayList<ActionProperty> result = this.servletCalls.payDebt(propertiesJson, -100);
        ArrayList<ActionProperty> expected = new ArrayList<>(Collections.singletonList(new ActionProperty(1, 0)));
        for (int i = 0; i < result.size(); ++i) {
            Assert.assertEquals(expected.get(i), result.get(i));
        }
        if (result.size() == 0) {
            Assert.fail();
        }
    }

    @Test
    public void recursiveSell() {
        Property p1 = new Property("name", 0, "false", 1, 0, 3, 100, 0, 1);
        Property p2 = new Property("name", 0, "false", 1, 0, 3, 100, 0, 2);
        ArrayList<Property> properties = new ArrayList<>(Arrays.asList(p1, p2));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        ArrayList<ActionProperty> result = this.servletCalls.payDebt(propertiesJson, -100);
        ArrayList<ActionProperty> expected = new ArrayList<>(Arrays.asList(new ActionProperty(1, 1), new ActionProperty(2, 1), new ActionProperty(2, 0)));
        result.sort(this.comparator);
        expected.sort(this.comparator);
        for (int i = 0; i < result.size(); ++i) {
            Assert.assertEquals(expected.get(i), result.get(i));
        }
        if (result.size() == 0) {
            Assert.fail();
        }
    }

    @Test
    public void dontSellBestGroup() {
        Property p1 = new Property("name", 0, "false", 0, 0, 1, 200, 0, 1);
        Property p2 = new Property("name", 0, "false", 0, 0, 2, 200, 0, 2);
        Property p3 = new Property("name", 0, "false", 0, 0, 3, 200, 0, 3);
        Property p4 = new Property("name", 0, "false", 0, 0, 3, 200, 0, 4);
        ArrayList<Property> properties = new ArrayList<>(Arrays.asList(p1, p2, p3, p4));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        ArrayList<ActionProperty> result = this.servletCalls.payDebt(propertiesJson, -200);
        ArrayList<ActionProperty> expected = new ArrayList<>(Arrays.asList(new ActionProperty(3, 0), new ActionProperty(4, 0)));
        result.sort(this.comparator);
        expected.sort(this.comparator);
        for (int i = 0; i < result.size(); ++i) {
            Assert.assertEquals(expected.get(i), result.get(i));
        }
        if (result.size() == 0) {
            Assert.fail();
        }
    }

    @Test
    public void preferPropertyWithoutGroup() {
        Property p1 = new Property("name", 0, "false", 0, 0, 3, 100, 0, 1);
        Property p2 = new Property("name", 0, "false", 0, 0, 4, 100, 0, 2);
        Property p3 = new Property("name", 0, "false", 0, 0, 4, 100, 0, 3);
        Property p4 = new Property("name", 0, "false", 0, 0, 4, 100, 0, 4);
        Property p5 = new Property("name", 0, "false", 0, 0, 5, 100, 0, 5);
        Property p6 = new Property("name", 0, "false", 0, 0, 5, 100, 0, 6);
        ArrayList<Property> properties = new ArrayList<>(Arrays.asList(p1, p2, p3, p4, p5, p6));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        ArrayList<ActionProperty> result = this.servletCalls.payDebt(propertiesJson, -150);
        ArrayList<ActionProperty> expected = new ArrayList<>(Arrays.asList(new ActionProperty(1, 0), new ActionProperty(5, 0), new ActionProperty(6, 0)));
        result.sort(this.comparator);
        expected.sort(this.comparator);
        for (int i = 0; i < result.size(); ++i) {
            Assert.assertEquals(expected.get(i), result.get(i));
        }
        if (result.size() == 0) {
            Assert.fail();
        }
    }

    @Test
    public void minimizeLevelDifference() {
        Property p1 = new Property("name", 0, "false", 2, 0, 4, 200, 0, 1);
        Property p2 = new Property("name", 0, "false", 2, 0, 4, 200, 0, 2);
        Property p3 = new Property("name", 0, "false", 2, 0, 4, 200, 0, 3);
        Property p4 = new Property("name", 0, "false", 3, 0, 3, 200, 0, 4);
        Property p5 = new Property("name", 0, "false", 3, 0, 3, 200, 0, 5);
        ArrayList<Property> properties = new ArrayList<>(Arrays.asList(p1, p2, p3, p4, p5));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        ArrayList<ActionProperty> result = this.servletCalls.payDebt(propertiesJson, -100);
        ArrayList<ActionProperty> expected = new ArrayList<>(Arrays.asList(new ActionProperty(3, 1), new ActionProperty(4, 2), new ActionProperty(5, 1)));
        result.sort(this.comparator);
        expected.sort(this.comparator);
        for (int i = 0; i < result.size(); ++i) {
            Assert.assertEquals(expected.get(i), result.get(i));
        }
        if (result.size() == 0) {
            Assert.fail();
        }
    }

    @Test
    @Ignore
    public void preferPropertyHighRent() {
        Property p1 = new Property("name", 0, "false", 0, 0, 3, 100, 50, 1);
        Property p2 = new Property("name", 0, "false", 0, 0, 4, 100, 40, 2);
        ArrayList<Property> properties = new ArrayList<>(Arrays.asList(p1, p2));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        ArrayList<ActionProperty> result = this.servletCalls.payDebt(propertiesJson, -50);
        ArrayList<ActionProperty> expected = new ArrayList<>(Collections.singletonList(new ActionProperty(2, 0)));
        result.sort(this.comparator);
        expected.sort(this.comparator);
        for (int i = 0; i < result.size(); ++i) {
            Assert.assertEquals(expected.get(i), result.get(i));
        }
        if (result.size() == 0) {
            Assert.fail();
        }
    }

}
