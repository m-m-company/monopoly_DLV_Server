package com.monopoly_DLV_Server.DLV_Server;

import com.monopoly_DLV_Server.DLV_Server.DTO.Player;
import com.monopoly_DLV_Server.DLV_Server.DTO.Property;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;

@RunWith(SpringRunner.class)
public class AuctionTests {

    ServletCalls servletCalls = new ServletCalls();

    @Test
    public void cantOffer() {
        Property property1 = new Property("name", -1, "false", 0, 0, 4, 200, 0, 0);
        ArrayList<Property> properties = new ArrayList<>(Collections.singleton(property1));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        Player player1 = new Player("name", "", 0, 150, 0, "false", 0, false, false, true, 0, false, "dlv");
        ArrayList<Player> players = new ArrayList<>(Collections.singleton(player1));
        String playersJson = JsonConverter.getInstance().toJson(players);
        Integer result = servletCalls.auction(playersJson, propertiesJson, 0, 200, 0, 2);
        Integer expected = -1;
        Assert.assertEquals(expected, result);
    }

    /*@Test
    public void dontWantOffer() {
        Property property1 = new Property("name", 10, "false", 0, 0, 4, 200, 0, 0);
        ArrayList<Property> properties = new ArrayList<>(Collections.singleton(property1));
        String propertiesJson = JsonConverter.getInstance().toJson(properties);
        Player player1 = new Player("name", "", 0, 300, 0, "false", 0, false, false, true, 0, false, "dlv");
        ArrayList<Player> players = new ArrayList<>(Collections.singleton(player1));
        String playersJson = JsonConverter.getInstance().toJson(players);
        Integer result = servletCalls.auction(playersJson, propertiesJson, 0, 150, 0, 2);
        Integer expected = 0;
        Assert.assertEquals(expected, result);
    }*/

}
