package com.monopoly_DLV_Server.DLV_Server;

import com.monopoly_DLV_Server.DLV_Server.DTO.Number;
import com.monopoly_DLV_Server.DLV_Server.DTO.Player;
import com.monopoly_DLV_Server.DLV_Server.DTO.Property;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j //Questa genera una variabile 'log'
public class ServletCalls {

    @GetMapping(value = "/buyOrNotBuy")
    public String buyOrNotBuy(String playerJson, String propertyJson, String number) {
        Object player = JsonConverter.getInstance().getObject(playerJson, Player.class);
        Object property = JsonConverter.getInstance().getObject(propertyJson, Property.class);
        ArrayList<Object> facts = new ArrayList<>();
        facts.add(player);
        facts.add(property);
        facts.add(new Number(number));
        List<AnswerSet> answerSets = DLVHandler.getInstance().startGuess(facts, "buyOrNotBuy.dlv");
        return "buyOrNotBuyResponse";
    }

    @GetMapping(value = "/manage")
    public String manage(String propertiesJson) {
        ArrayList<Object> facts = JsonConverter.getInstance().getArray(propertiesJson, Property.class);
        List<AnswerSet> answerSets = DLVHandler.getInstance().startGuess(facts, "manage.dlv");
        return "manageResponse";
    }

    @GetMapping(value = "/proposeTrade")
    public String trade(String playersJson, String propertiesJson) {
        ArrayList<Object> facts = JsonConverter.getInstance().getArray(playersJson, Player.class);
        ArrayList<Object> properties = JsonConverter.getInstance().getArray(propertiesJson, Property.class);
        facts.addAll(properties);
        List<AnswerSet> answerSets = DLVHandler.getInstance().startGuess(facts, "proposeTrade.dlv");
        return "proposeTradeResponse";
    }

    @GetMapping(value = "/auction")
    public String auction() {
        return "auction";
    }

}