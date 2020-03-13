package com.monopoly_DLV_Server.DLV_Server;

import com.monopoly_DLV_Server.DLV_Server.DTO.ActionProperty;
import com.monopoly_DLV_Server.DLV_Server.DTO.Property;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringRunner.class)
public class PayDebtTests {

    ServletCalls servletCalls = new ServletCalls();

    @Test
    public void reachMoney() {
        Property p1 = new Property("name", 0, "false", 0, 0, 1, 200, 0, 1);
        ArrayList<Property> properties = new ArrayList<Property>(Arrays.asList(p1));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        ArrayList<ActionProperty> result = servletCalls.payDebt(propertiesJson, -100);
        ArrayList<ActionProperty> expected = new ArrayList<>(Arrays.asList(new ActionProperty(1, 0)));
        for(int i = 0; i < result.size(); ++i){
            Assert.assertEquals(expected.get(i), result.get(i));
        }
        if (result.size() == 0){
            Assert.fail();
        }
    }

}
