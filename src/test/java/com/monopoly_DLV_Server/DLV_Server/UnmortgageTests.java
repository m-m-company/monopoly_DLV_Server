package com.monopoly_DLV_Server.DLV_Server;

import com.monopoly_DLV_Server.DLV_Server.DTO.Property;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@RunWith(SpringRunner.class)
public class UnmortgageTests {

    ServletCalls servletCalls = new ServletCalls();

    @Test
    public void unMortgageOnlyTrue() {
        Property p1 = new Property("name", 0, "true", 0, 0, 1, 100, 0, 1);
        Property p2 = new Property("name", 0, "false", 0, 0, 1, 100, 0, 2);
        ArrayList<Property> properties = new ArrayList<Property>(Arrays.asList(p1, p2));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        ArrayList<Integer> result = servletCalls.unmortgage(propertiesJson, 1000);
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1));
        Collections.sort(result);
        Collections.sort(expected);
        for(int i=0 ; i<result.size(); ++i){
            Assert.assertEquals(result.get(i),expected.get(i));
        }
    }

    @Test
    public void dontUnmortgage() {
        Property p1 = new Property("name", 0, "true", 0, 0, 1, 1000, 0, 1);
        Property p2 = new Property("name", 0, "true", 0, 0, 1, 1000, 0, 2);
        Property p3 = new Property("name", 0, "true", 0, 0, 1, 3000, 0, 3);
        Property p4 = new Property("name", 0, "true", 0, 0, 2, 4000, 0, 4);
        ArrayList<Property> properties = new ArrayList<Property>(Arrays.asList(p1, p2, p3, p4));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        ArrayList<Integer> result = servletCalls.unmortgage(propertiesJson, 1000);
        ArrayList<Integer> expected = new ArrayList<>();
        Collections.sort(result);
        Collections.sort(expected);
        for(int i=0 ; i<result.size(); ++i){
            Assert.assertEquals(result.get(i),expected.get(i));
        }
    }

    @Test
    public void respectLimitMoney() {
        Property p1 = new Property("name", 0, "true", 0, 0, 1, 100, 0, 1);
        Property p2 = new Property("name", 0, "true", 0, 0, 1, 100, 0, 2);
        Property p3 = new Property("name", 0, "true", 0, 0, 1, 300, 0, 3);
        Property p4 = new Property("name", 0, "true", 0, 0, 2, 400, 0, 4);
        ArrayList<Property> properties = new ArrayList<Property>(Arrays.asList(p1, p2, p3, p4));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        ArrayList<Integer> result = servletCalls.unmortgage(propertiesJson, 1000);
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1,2));
        Collections.sort(result);
        Collections.sort(expected);
        for(int i=0 ; i<result.size(); ++i){
            Assert.assertEquals(result.get(i),expected.get(i));
        }
    }

    @Test
    public void preferBestGroup() {
        Property p1 = new Property("name", 0, "true", 0, 0, 1, 300, 0, 1);
        Property p2 = new Property("name", 0, "true", 0, 0, 3, 100, 0, 2);
        Property p3 = new Property("name", 0, "true", 0, 0, 4, 100, 0, 3);
        Property p4 = new Property("name", 0, "true", 0, 0, 5, 100, 0, 4);
        ArrayList<Property> properties = new ArrayList<Property>(Arrays.asList(p1, p2, p3, p4));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        ArrayList<Integer> result = servletCalls.unmortgage(propertiesJson, 1000);
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1));
        Collections.sort(result);
        Collections.sort(expected);
        for(int i=0 ; i<result.size(); ++i){
            Assert.assertEquals(result.get(i),expected.get(i));
        }
    }

    @Test
    public void preferSameGroup() {
        Property p1 = new Property("name", 0, "true", 0, 0, 3, 200, 0, 1);
        Property p2 = new Property("name", 0, "true", 0, 0, 3, 100, 0, 2);
        Property p3 = new Property("name", 0, "false", 0, 0, 3, 300, 0, 3);
        Property p4 = new Property("name", 0, "true", 0, 0, 4, 100, 0, 4);
        ArrayList<Property> properties = new ArrayList<Property>(Arrays.asList(p1, p2, p3, p4));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        ArrayList<Integer> result = servletCalls.unmortgage(propertiesJson, 1000);
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1,2));
        Collections.sort(result);
        Collections.sort(expected);
        for(int i=0 ; i<result.size(); ++i){
            Assert.assertEquals(result.get(i),expected.get(i));
        }
    }

}
