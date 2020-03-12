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
public class UnmortageTests {

    ServletCalls servletCalls = new ServletCalls();

    @Test
    public void prova() {
        Property p1 = new Property("name", 0, "true", 0, 0, 1, 100, 0, 1);
        Property p2 = new Property("name", 0, "false", 0, 0, 1, 200, 0, 2);
        Property p3 = new Property("name", 0, "true", 0, 0, 1, 300, 0, 3);
        Property p4 = new Property("name", 0, "true", 0, 0, 2, 400, 0, 4);
        ArrayList<Property> properties = new ArrayList<Property>(Arrays.asList(p1, p2, p3, p4));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        ArrayList<Integer> result = servletCalls.unmortgage(propertiesJson, 1000);
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(3,1,4));
        Collections.sort(result);
        Collections.sort(expected);
        for(int i=0 ; i<result.size(); ++i){
            Assert.assertEquals(result.get(i),expected.get(i));
        }
    }

}
