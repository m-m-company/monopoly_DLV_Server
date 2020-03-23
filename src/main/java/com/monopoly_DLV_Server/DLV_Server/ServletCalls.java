package com.monopoly_DLV_Server.DLV_Server;

import com.monopoly_DLV_Server.DLV_Server.DTO.Number;
import com.monopoly_DLV_Server.DLV_Server.DTO.*;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class ServletCalls {

    @GetMapping(value = "/buyOrNotBuy")
    public Boolean buyOrNotBuy(String playerJson, String propertyJson, Integer numberOfTheSameGroup) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (playerJson == null || propertyJson == null || numberOfTheSameGroup == null)
            return null;
        Object player = JsonConverter.getInstance().getObject(playerJson, Player.class);
        Object property = JsonConverter.getInstance().getObject(propertyJson, Property.class);
        Number number = new Number(numberOfTheSameGroup, "sameGroup");
        ArrayList<Object> facts = new ArrayList<>();
        facts.add(player);
        facts.add(property);
        facts.add(number);
        List<AnswerSet> answerSets = DLVHandler.getInstance().startGuess(facts, "buyOrNotBuy.dlv");
        for (AnswerSet a :
                answerSets) {
            for (Object o : a.getAtoms()) {
                if (o instanceof BooleanValue) {
                    return Boolean.valueOf(((BooleanValue) o).getBooleanValue());
                }
            }
        }
        return false;
    }

    @GetMapping(value = "/manage")
    public ArrayList<BuyHouse> manage(String propertiesJson, String playerJson) {
        ArrayList<Object> facts = JsonConverter.getInstance().getArray(propertiesJson, Property.class);
        facts.add(JsonConverter.getInstance().getObject(playerJson, Player.class));
        ArrayList<BuyHouse> as = new ArrayList<>();
        List<AnswerSet> answerSets = DLVHandler.getInstance().startGuess(facts, "manage.dlv");
        for (AnswerSet a : answerSets) {
            try {
                for (Object o : a.getAtoms()) {
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

    @GetMapping(value = "/unmortgage")
    public ArrayList<Integer> unmortgage(String propertiesJson, Integer money) {
        ArrayList<Object> facts = JsonConverter.getInstance().getArray(propertiesJson, Property.class);
        facts.add(new Number(money, "actualMoney"));
        List<AnswerSet> answerSets = DLVHandler.getInstance().startGuess(facts, "unmortgage.dlv");
        ArrayList<Integer> unmortgages = new ArrayList<>();
        for (AnswerSet a : answerSets) {
            try {
                for (Object o : a.getAtoms()) {
                    if (o instanceof Unmortgage)
                        unmortgages.add(((Unmortgage) o).getIndex());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return unmortgages;
        }
        return null;
    }

    @GetMapping(value = "/payDebt")
    public ArrayList<ActionProperty> payDebt(String propertiesJson, Integer actualMoney) {
        ArrayList<ActionProperty> actionProperties = new ArrayList<>();
        if (propertiesJson == null || actualMoney == null)
            return null;
        ArrayList<Object> facts = JsonConverter.getInstance().getArray(propertiesJson, Property.class);
        facts.add(new Number(actualMoney * -1, "moneyToReach"));
        List<AnswerSet> answerSets = DLVHandler.getInstance().startGuess(facts, "payDebt.dlv");
        for (AnswerSet answerSet : answerSets) {
            try {
                for (Object o : answerSet.getAtoms()) {
                    if (o instanceof ActionProperty) {
                        actionProperties.add((ActionProperty) o);
                    }
                }
            } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        log.error("Mortgage: " + actionProperties);
        return actionProperties;
    }

    @GetMapping(value = "/auction")
    public Integer auction(String playersArrayJson, String property, Integer highestBid, Integer whoAmI) {
        ArrayList<Object> players = JsonConverter.getInstance().getArray(playersArrayJson, Player.class);
        Object p = JsonConverter.getInstance().getObject(property, Property.class);
        Object highBid = new Number(highestBid, "highestBid");
        Object mySelf = new Number(whoAmI, "mySelf");
        ArrayList<Object> facts = new ArrayList<>(players);
        facts.add(p);
        facts.add(highBid);
        facts.add(mySelf);
        List<AnswerSet> answerSets = DLVHandler.getInstance().startGuess(facts, "bid.dlv");
        for (AnswerSet a : answerSets) {
            try {
                for (Object o : a.getAtoms()) {
                    if (o instanceof Number) {
                        if (((Number) o).getSemantic().equals("offer"))
                            return ((Number) o).getNumber();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

}