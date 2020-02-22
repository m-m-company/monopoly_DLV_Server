package com.monopoly_DLV_Server.DLV_Server;

import com.monopoly_DLV_Server.DLV_Server.DTO.*;
import com.monopoly_DLV_Server.DLV_Server.DTO.Number;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class ServletCalls {

    @GetMapping(value = "/buyOrNotBuy")
    public Boolean buyOrNotBuy(String playerJson, String propertyJson, Integer numberOfTheSameGroup) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object player = JsonConverter.getInstance().getObject(playerJson, Player.class);
        Object property = JsonConverter.getInstance().getObject(propertyJson, Property.class);
        Number number = new Number(numberOfTheSameGroup);
        ArrayList<Object> facts = new ArrayList<>();
        facts.add(player);
        facts.add(property);
        facts.add(number);
        List<AnswerSet> answerSets = DLVHandler.getInstance().startGuess(facts, "buyOrNotBuy.dlv");
        for (AnswerSet a:
             answerSets) {
            for (Object o : a.getAtoms()){
                if (o instanceof BooleanValue){
                   return Boolean.valueOf(((BooleanValue) o).getBooleanValue());
                }
            }
        }
        return false;
    }

    @GetMapping(value = "/manage")
    public ArrayList<BuyHouse> manage(String propertiesJson, String playerJson) {
        log.error(propertiesJson);
        ArrayList<Object> facts = JsonConverter.getInstance().getArray(propertiesJson, Property.class);
        facts.add(JsonConverter.getInstance().getObject(playerJson, Player.class));
        ArrayList<BuyHouse> as = new ArrayList<>();
        log.info(String.valueOf(facts));
        List<AnswerSet> answerSets = DLVHandler.getInstance().startGuess(facts, "manage.dlv");
        log.error("ASS: "+ String.valueOf(answerSets));
        for (AnswerSet a : answerSets){
            try {
                for (Object o : a.getAtoms()){
                    if (o instanceof BuyHouse)
                        as.add((BuyHouse) o);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return as;
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