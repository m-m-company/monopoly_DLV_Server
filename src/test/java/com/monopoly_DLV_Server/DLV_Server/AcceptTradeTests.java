package com.monopoly_DLV_Server.DLV_Server;

import com.monopoly_DLV_Server.DLV_Server.DTO.Property;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


@RunWith(SpringRunner.class)
public class AcceptTradeTests {

    ServletCalls servletCalls = new ServletCalls();

    @Test
    public void giveMoney() {
        boolean result = servletCalls.acceptTrade(100,"empty", "empty", 0, 0, 0);
        Assert.assertTrue(result);
    }

    @Test
    public void giveMeMoney() {
        boolean result = servletCalls.acceptTrade(-100,"empty", "empty", 0, 0, 0);
        Assert.assertFalse(result);
    }

    @Test
    public void giveCommunityChestJailCard() {
        boolean result = servletCalls.acceptTrade(0,"empty", "empty", 0, 1, 0);
        Assert.assertTrue(result);
    }

    @Test
    public void giveMeCommunityChestJailCard() {
        boolean result = servletCalls.acceptTrade(0,"empty", "empty", 0, -1, 0);
        Assert.assertFalse(result);
    }

    @Test
    public void giveChanceJailCard() {
        boolean result = servletCalls.acceptTrade(0,"empty", "empty", 0, 0, 1);
        Assert.assertTrue(result);
    }

    @Test
    public void giveMeChanceJailCard() {
        boolean result = servletCalls.acceptTrade(0,"empty", "empty", 0, 0, -1);
        Assert.assertFalse(result);
    }

    @Test
    public void giveProperty() {
        Property property = new Property("name", 0, "false", 0, 0, 0, 200, 0, 0);
        ArrayList<Property> propertiesTrade = new ArrayList<>(Collections.singletonList(property));
        String propertiesTradeJson = JsonConverter.getInstance().toJson(propertiesTrade);
        boolean result = servletCalls.acceptTrade(-100,"empty", propertiesTradeJson, 1000, 0, 0);
        Assert.assertTrue(result);
    }

    @Test
    public void giveMeProperty() {
        Property property = new Property("name", 0, "false", 0, 0, 0, 200, 0, 0);
        ArrayList<Property> propertiesTrade = new ArrayList<>(Collections.singletonList(property));
        String propertiesTradeJson = JsonConverter.getInstance().toJson(propertiesTrade);
        boolean result = servletCalls.acceptTrade(300,"empty", propertiesTradeJson, 0, 0, 0);
        Assert.assertTrue(result);
    }

    @Test
    public void giveMortgagedProperty() {
        Property property = new Property("name", 0, "true", 0, 0, 0, 200, 0, 0);
        ArrayList<Property> propertiesTrade = new ArrayList<>(Collections.singletonList(property));
        String propertiesTradeJson = JsonConverter.getInstance().toJson(propertiesTrade);
        boolean result = servletCalls.acceptTrade(-150,"empty", propertiesTradeJson, 1000, 0, 0);
        Assert.assertFalse(result);
    }

    @Test
    public void giveMeMortgagedProperty() {
        Property property = new Property("name", 0, "true", 0, 0, 0, 200, 0, 0);
        ArrayList<Property> propertiesTrade = new ArrayList<>(Collections.singletonList(property));
        String propertiesTradeJson = JsonConverter.getInstance().toJson(propertiesTrade);
        boolean result = servletCalls.acceptTrade(50,"empty", propertiesTradeJson, 0, 0, 0);
        Assert.assertFalse(result);
    }

    @Test
    public void givePropertyForGroup() {
        Property p1 = new Property("name", 0, "false", 0, 0, 3, 150, 0, 1);
        ArrayList<Property> propertiesTrade = new ArrayList<>(Collections.singletonList(p1));
        String propertiesTradeJson = JsonConverter.getInstance().toJson(propertiesTrade);
        Property p2 = new Property("name", 1, "false", 0, 0, 3, 150, 0, 2);
        ArrayList<Property> myProperties = new ArrayList<>(Collections.singletonList(p2));
        String myPropertiesJson = JsonConverter.getInstance().toJson(myProperties);
        boolean result = servletCalls.acceptTrade(-200, myPropertiesJson, propertiesTradeJson, 1, 0, 0);
        Assert.assertTrue(result);
    }

    @Test
    public void giveMePropertyFromGroup() {
        Property p1 = new Property("name", 1, "false", 0, 0, 3, 150, 0, 1);
        ArrayList<Property> propertiesTrade = new ArrayList<>(Collections.singletonList(p1));
        String propertiesTradeJson = JsonConverter.getInstance().toJson(propertiesTrade);
        Property p2 = new Property("name", 1, "false", 0, 0, 3, 150, 0, 2);
        ArrayList<Property> myProperties = new ArrayList<>(Arrays.asList(p1, p2));
        String myPropertiesJson = JsonConverter.getInstance().toJson(myProperties);
        boolean result = servletCalls.acceptTrade(200, myPropertiesJson, propertiesTradeJson, 1, 0, 0);
        Assert.assertFalse(result);
    }

    @Test
    public void givePropertyForImportantGroup() {
        Property p1 = new Property("name", 1, "false", 0, 0, 3, 150, 0, 1);
        Property p3 = new Property("name", 0, "false", 0, 0, 9, 250, 0, 3);
        ArrayList<Property> propertiesTrade = new ArrayList<>(Arrays.asList(p1, p3));
        String propertiesTradeJson = JsonConverter.getInstance().toJson(propertiesTrade);
        Property p2 = new Property("name", 1, "false", 0, 0, 3, 150, 0, 2);
        Property p4 = new Property("name", 1, "false", 0, 0, 9, 250, 0, 4);
        ArrayList<Property> myProperties = new ArrayList<>(Arrays.asList(p1, p2, p4));
        String myPropertiesJson = JsonConverter.getInstance().toJson(myProperties);
        boolean result = servletCalls.acceptTrade(-100, myPropertiesJson, propertiesTradeJson, 1, 0, 0);
        Assert.assertTrue(result);
    }

    @Test
    public void giveMePropertyFromImportantGroup() {
        Property p1 = new Property("name", 0, "false", 0, 0, 3, 150, 0, 1);
        Property p3 = new Property("name", 1, "false", 0, 0, 9, 250, 0, 3);
        ArrayList<Property> propertiesTrade = new ArrayList<>(Arrays.asList(p1, p3));
        String propertiesTradeJson = JsonConverter.getInstance().toJson(propertiesTrade);
        Property p2 = new Property("name", 1, "false", 0, 0, 3, 150, 0, 2);
        Property p4 = new Property("name", 1, "false", 0, 0, 9, 250, 0, 4);
        ArrayList<Property> myProperties = new ArrayList<>(Arrays.asList(p2, p3, p4));
        String myPropertiesJson = JsonConverter.getInstance().toJson(myProperties);
        boolean result = servletCalls.acceptTrade(100, myPropertiesJson, propertiesTradeJson, 1, 0, 0);
        Assert.assertFalse(result);
    }

    @Test
    public void nobodyWins() {
        boolean result = servletCalls.acceptTrade(0,"empty", "empty", 0, 1, -1);
        Assert.assertFalse(result);
    }

    @Test
    public void complexTest1() {
        Property p1 = new Property("name", 0, "false", 0, 0, 3, 150, 0, 1);
        Property p3 = new Property("name", 1, "false", 0, 0, 9, 250, 0, 3);
        Property p5 = new Property("name", 0, "false", 0, 0, 6, 200, 0, 5);
        Property p6 = new Property("name", 1, "true", 0, 0, 6, 200, 0, 6);
        ArrayList<Property> propertiesTrade = new ArrayList<>(Arrays.asList(p1, p3, p5, p6));
        String propertiesTradeJson = JsonConverter.getInstance().toJson(propertiesTrade);
        Property p2 = new Property("name", 1, "false", 0, 0, 3, 150, 0, 2);
        Property p4 = new Property("name", 1, "false", 0, 0, 9, 250, 0, 4);
        ArrayList<Property> myProperties = new ArrayList<>(Arrays.asList(p2, p3, p4));
        String myPropertiesJson = JsonConverter.getInstance().toJson(myProperties);
        boolean result = servletCalls.acceptTrade(200, myPropertiesJson, propertiesTradeJson, 1, -1, 0);
        Assert.assertFalse(result);
    }

}
