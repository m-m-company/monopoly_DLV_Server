package com.monopoly_DLV_Server.DLV_Server;

import com.monopoly_DLV_Server.DLV_Server.DTO.ActionProperty;
import com.monopoly_DLV_Server.DLV_Server.DTO.Property;
import org.junit.Assert;
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
    Comparator<ActionProperty> comparator = Comparator.comparingInt(ActionProperty::getIndex);

    @Test
    public void sellOnlyWhatYouCan() {
        Property p1 = new Property("name", 0, "false", 0, 0, 1, 200, 0, 1);
        Property p2 = new Property("name", 0, "false", 0, 0, 2, 200, 0, 2);
        Property p3 = new Property("name", 0, "true", 0, 0, 3, 400, 0, 3);
        Property p4 = new Property("name", 0, "true", 0, 0, 3, 400, 0, 4);
        ArrayList<Property> properties = new ArrayList<Property>(Arrays.asList(p1, p2, p3, p4));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        ArrayList<ActionProperty> result = servletCalls.payDebt(propertiesJson, -200);
        ArrayList<ActionProperty> expected = new ArrayList<>(Arrays.asList(new ActionProperty(1, 0), new ActionProperty(2,0)));
        result.sort(comparator);
        expected.sort(comparator);
        for(int i = 0; i < result.size(); ++i){
            Assert.assertEquals(expected.get(i), result.get(i));
        }
        if (result.size() == 0){
            Assert.fail();
        }
    }

    @Test
    public void dontSellBestGroup() {
        Property p1 = new Property("name", 0, "false", 0, 0, 1, 200, 0, 1);
        Property p2 = new Property("name", 0, "false", 0, 0, 2, 200, 0, 2);
        Property p3 = new Property("name", 0, "false", 0, 0, 3, 200, 0, 3);
        Property p4 = new Property("name", 0, "false", 0, 0, 3, 200, 0, 4);
        ArrayList<Property> properties = new ArrayList<Property>(Arrays.asList(p1, p2, p3, p4));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        ArrayList<ActionProperty> result = servletCalls.payDebt(propertiesJson, -200);
        ArrayList<ActionProperty> expected = new ArrayList<>(Arrays.asList(new ActionProperty(3, 0), new ActionProperty(4,0)));
        result.sort(comparator);
        expected.sort(comparator);
        for(int i = 0; i < result.size(); ++i){
            Assert.assertEquals(expected.get(i), result.get(i));
        }
        if (result.size() == 0){
            Assert.fail();
        }
    }

    @Test
    public void reachMoney() {
        Property p1 = new Property("name", 0, "false", 0, 0, 1, 200, 0, 1);
        ArrayList<Property> properties = new ArrayList<Property>(Collections.singletonList(p1));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        ArrayList<ActionProperty> result = servletCalls.payDebt(propertiesJson, -100);
        ArrayList<ActionProperty> expected = new ArrayList<>(Collections.singletonList(new ActionProperty(1, 0)));
        for(int i = 0; i < result.size(); ++i){
            Assert.assertEquals(expected.get(i), result.get(i));
        }
        if (result.size() == 0){
            Assert.fail();
        }
    }

}
