package com.monopoly_DLV_Server.DLV_Server;

import com.monopoly_DLV_Server.DLV_Server.DTO.Player;
import com.monopoly_DLV_Server.DLV_Server.DTO.Property;
import com.monopoly_DLV_Server.DLV_Server.DTO.Trade;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@RunWith(SpringRunner.class)
public class ProposeTradeTests {

    ServletCalls servletCalls = new ServletCalls();

    @Test
    public void offerProperty() {
        Property property1 = new Property("name", 1, "false", 0, 0, 3, 100, 0, 1);
        Property property2 = new Property("name", 1, "false", 0, 0, 3, 100, 0, 2);
        Property property3 = new Property("name", 1, "false", 0, 0, 4, 100, 0, 3);
        Property property4 = new Property("name", 2, "false", 0, 0, 3, 100, 0, 4);
        ArrayList<Property> properties = new ArrayList<>(Arrays.asList(property1, property2, property3, property4));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        Player player1 = new Player("name", "", 0, 100, 0, "false", 0, false, false, "true", 1, false, "dlv");
        Player player2 = new Player("name", "", 0, 1000, 0, "false", 0, false, false, "true", 2, false, "dlv");
        ArrayList<Player> players = new ArrayList<>(Arrays.asList(player1, player2));
        String playersJson = JsonConverter.getInstance().toJson(players);
        ArrayList<Integer> propertyOffered = new ArrayList<>(Collections.singletonList(3));
        ArrayList<Integer> propertyRequested = new ArrayList<>(Collections.singletonList(4));
        Trade expected = new Trade(1, 2, 0, propertyOffered, propertyRequested, 0, 0);
        Trade actual = servletCalls.proposeTrade(1, propertiesJson, playersJson);
        Assert.assertEquals(expected, actual);
    }

}
