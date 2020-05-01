package com.monopoly_DLV_Server.DLV_Server;

import com.monopoly_DLV_Server.DLV_Server.DTO.Player;
import com.monopoly_DLV_Server.DLV_Server.DTO.Property;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@RunWith(SpringRunner.class)
public class AuctionTests {

    ServletCalls servletCalls = new ServletCalls();

    @Test
    public void cantOffer() {
        Property property1 = new Property("name", -1, "false", 0, 0, 4, 200, 0, 0);
        ArrayList<Property> properties = new ArrayList<>(Collections.singleton(property1));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        Player player1 = new Player("name", "", 0, 150, 0, "false", 0, false, false, "true", 0, false, "dlv");
        ArrayList<Player> players = new ArrayList<>(Collections.singleton(player1));
        String playersJson = JsonConverter.getInstance().toJson(players);
        Integer result = this.servletCalls.auction(playersJson, propertiesJson, 0, 200, 0, 2);
        Integer expected = -1;
        Assert.assertEquals(expected, result);
    }

    @Test
    public void dontWantOffer() {
        Property property1 = new Property("name", -1, "false", 0, 0, 4, 200, 0, 0);
        ArrayList<Property> properties = new ArrayList<>(Collections.singleton(property1));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        Player player1 = new Player("name", "", 0, 300, 0, "false", 0, false, false, "true", 0, false, "dlv");
        ArrayList<Player> players = new ArrayList<>(Collections.singleton(player1));
        String playersJson = JsonConverter.getInstance().toJson(players);
        Integer result = this.servletCalls.auction(playersJson, propertiesJson, 0, 150, 0, 2);
        Integer expected = 0;
        Assert.assertEquals(expected, result);
    }

    @Test
    public void offer() {
        Property property1 = new Property("name", 0, "false", 0, 0, 1, 10, 0, 0);
        Property property2 = new Property("name", 0, "false", 0, 0, 1, 10, 0, 2);
        Property property3 = new Property("name", 1, "false", 0, 0, 1, 10, 0, 3);
        Property property4 = new Property("name", 2, "false", 0, 0, 1, 10, 0, 4);
        Property property5 = new Property("name", 3, "false", 0, 0, 1, 10, 0, 5);
        Property property6 = new Property("name", 1, "false", 0, 0, 1, 10, 0, 6);
        Property property7 = new Property("name", 2, "false", 0, 0, 1, 10, 0, 7);
        Property property8 = new Property("name", 0, "false", 0, 0, 1, 10, 0, 8);

        ArrayList<Property> properties = new ArrayList<>(Arrays.asList(property1, property2, property3, property4, property5, property6, property7, property8));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        Player player1 = new Player("name", "", 0, 300, 0, "false", 0, false, false, "true", 0, false, "dlv");
        Player player2 = new Player("name", "", 0, 300, 0, "false", 0, false, false, "true", 1, false, "dlv");
        Player player3 = new Player("name", "", 0, 300, 0, "false", 0, false, false, "true", 2, false, "dlv");
        Player player4 = new Player("name", "", 0, 300, 0, "false", 0, false, false, "true", 3, false, "dlv");
        ArrayList<Player> players = new ArrayList<>(Arrays.asList(player1, player2, player3, player4));
        String playersJson = JsonConverter.getInstance().toJson(players);
        Integer result = this.servletCalls.auction(playersJson, propertiesJson, 0, 150, 0, 2);
        System.out.println("RES: " + result);
    }

}
