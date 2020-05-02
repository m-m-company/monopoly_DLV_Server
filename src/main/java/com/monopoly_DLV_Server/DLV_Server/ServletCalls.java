package com.monopoly_DLV_Server.DLV_Server;

import com.monopoly_DLV_Server.DLV_Server.DTO.Number;
import com.monopoly_DLV_Server.DLV_Server.DTO.*;
import com.monopoly_DLV_Server.DLV_Server.DTO.proposeTradeOutput.PropertyCounterPart;
import com.monopoly_DLV_Server.DLV_Server.DTO.proposeTradeOutput.PropertyOffer;
import com.monopoly_DLV_Server.DLV_Server.DTO.proposeTradeOutput.TradeMoney;
import com.monopoly_DLV_Server.DLV_Server.DTO.proposeTradeOutput.TradeProperty;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class ServletCalls {

    @GetMapping(value = "/buyOrNotBuy")
    public Boolean buyOrNotBuy(String playerJson, String propertyJson, Integer numberOfTheSameGroup) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (playerJson == null || propertyJson == null || numberOfTheSameGroup == null)
            return null;
        Object player = JsonConverter.getInstance().getObject(playerJson, Player.class);
        Number actualMoney = new Number(((Player) player).getMoney(), "actualMoney");
        Object property = JsonConverter.getInstance().getObject(propertyJson, Property.class);
        Number number = new Number(numberOfTheSameGroup, "sameGroup");
        ArrayList<Object> facts = new ArrayList<>();
        facts.add(actualMoney);
        facts.add(property);
        facts.add(number);
        List<AnswerSet> answerSets = DLVHandler.getInstance().startGuess(facts, "buyOrNotBuy.dlv");
        for (AnswerSet a : answerSets) {
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
        Player player = (Player) JsonConverter.getInstance().getObject(playerJson, Player.class);
        Number limit = new Number((int) (player.getMoney() * 0.3), "limit");
        facts.add(limit);
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
    public Trade proposeTrade(Integer whoAmI, String propertiesJson, String playersJson) {
        ArrayList<Object> properties = JsonConverter.getInstance().getArray(propertiesJson, Property.class);
        ArrayList<Object> players = JsonConverter.getInstance().getArray(playersJson, Player.class);
        Object mySelf = null;
        for (Object o : players) {
            if (((Player) o).getIndex() == whoAmI) {
                mySelf = o;
                players.remove(o);
                break;
            }
        }
        assert mySelf != null;
        ArrayList<Object> myProperties = new ArrayList<>();
        for (Object property : properties) {
            if (((Property) property).getOwner().equals(whoAmI) && ((Property) property).getHouse() == 0) {
                myProperties.add(property);
            }
        }
        properties.removeAll(myProperties);
        ArrayList<Object> facts = new ArrayList<>(myProperties);
        facts.add(mySelf);
        facts.add(new Number(whoAmI, "whoAmI"));
        facts.add(new Number((int) (((Player) mySelf).getMoney() * 0.6), "limit"));
        players.sort(Comparator.comparingInt(player -> ((Player) player).getMoney()));
        for (Object player : players) {
            ArrayList<Object> propertiesInvolved = new ArrayList<>();
            for (Object property : properties) {
                if (((Property) property).getOwner().equals(((Player) player).getIndex()) && ((Property) property).getHouse() == 0) {
                    propertiesInvolved.add(property);
                }
            }
            facts.addAll(propertiesInvolved);
            List<AnswerSet> answerSets = DLVHandler.getInstance().startGuess(facts, "proposeTrade.dlv");
            String trade = "false";
            int money = 0;
            ArrayList<Integer> propertyOffered = new ArrayList<>();
            ArrayList<Integer> propertyRequested = new ArrayList<>();
            for (AnswerSet answerSet : answerSets) {
                try {
                    for (Object o : answerSet.getAtoms()) {
                        if (o instanceof PropertyOffer) {
                            money += ((PropertyOffer) o).price;
                        }
                        if (o instanceof TradeMoney) {
                            propertyRequested.add(((TradeMoney) o).property);
                        }
                        if (o instanceof TradeProperty) {
                            propertyRequested.add(((TradeProperty) o).property);
                        }
                        if (o instanceof PropertyCounterPart) {
                            propertyOffered.add(((PropertyCounterPart) o).propertyOffered);
                        }
                        if (o instanceof BooleanValue) {
                            trade = ((BooleanValue) o).getBooleanValue();
                        }
                    }
                } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            if (trade.equals("\"true\"")) {
                return new Trade(whoAmI, ((Player) player).getIndex(), money, propertyOffered, propertyRequested, 0, 0);
            }
            facts.remove(player);
            facts.removeAll(propertiesInvolved);
        }
        return new Trade();
    }

    @GetMapping(value = "/acceptTrade")
    public Boolean acceptTrade(Integer money, String myPropertiesJson, String propertiesTradeJson, Integer owner,
                               Integer communityChestJailCard, Integer chanceJailCard) {
        ArrayList<Object> facts = new ArrayList<>();
        if (!myPropertiesJson.equals("empty")) {
            facts.addAll(JsonConverter.getInstance().getArray(myPropertiesJson, Property.class));
        }
        if (!propertiesTradeJson.equals("empty")) {
            facts.addAll(JsonConverter.getInstance().getArray(propertiesTradeJson, PropertyTrade.class));
        }
        Number m = null;
        if (money >= 0) {
            m = new Number(money, "offered");
        } else {
            m = new Number(-money, "requested");
        }
        facts.add(m);
        facts.add(new Number(owner, "owner"));
        facts.add(new Number(communityChestJailCard, "communityChestJailCard"));
        facts.add(new Number(chanceJailCard, "chanceJailCard"));
        List<AnswerSet> answerSets = DLVHandler.getInstance().startGuess(facts, "acceptTrade.dlv");
        AnswerSet answerSet = answerSets.get(0);
        Map<Integer, Integer> weights = answerSet.getWeights();
        Integer positive = weights.get(1);
        Integer negative = weights.get(2);
        return positive > negative;
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
        System.out.println(answerSets.toString());
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
        return actionProperties;
    }

    @GetMapping(value = "/auction")
    public Integer auction(String playersArrayJson, String propertiesJson, Integer propertyIndex, Integer highestBid,
                           Integer whoAmI, Integer numberOfTheSameGroup) {
        ArrayList<Object> players = JsonConverter.getInstance().getArray(playersArrayJson, Player.class);
        ArrayList<Object> properties = JsonConverter.getInstance().getArray(propertiesJson, Property.class);
        Object p = new Number(propertyIndex, "propertyIndex");
        Object highBid = new Number(highestBid, "highestBid");
        Object mySelf = new Number(whoAmI, "mySelf");
        Object sg = new Number(numberOfTheSameGroup, "sameGroup");
        int range = 0;
        for (Object player : players) {
            if (((Player) player).getIndex() == whoAmI) {
                range = ((Player) player).getMoney();
            }
        }
        if (range < highestBid) {
            return -1;
        }
        Object limit = new Number((int) (range * 0.30), "limit");
        ArrayList<Object> facts = new ArrayList<>(players);
        facts.addAll(properties);
        facts.add(limit);
        facts.add(p);
        facts.add(highBid);
        facts.add(mySelf);
        facts.add(sg);
        List<AnswerSet> answerSets = DLVHandler.getInstance().startGuess(facts, "bid.dlv");
        Number bestOffer = new Number(0, "\"offer\"");
        for (AnswerSet a : answerSets) {
            try {
                for (Object o : a.getAtoms()) {
                    if (o instanceof Number) {
                        if (((Number) o).semantic.equals("\"offer\"")) {
                            if (((Number) o).getNumber() > bestOffer.getNumber()) {
                                log.info(String.valueOf(((Number) o).getNumber()));
                                bestOffer = (Number) o;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bestOffer.getNumber();
        }
        return -1;
    }

    @GetMapping(value = "/postBail")
    public Boolean postBail() {

        return true;
    }
}