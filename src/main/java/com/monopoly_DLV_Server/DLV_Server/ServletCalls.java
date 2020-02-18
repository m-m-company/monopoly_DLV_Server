package com.monopoly_DLV_Server.DLV_Server;

import com.monopoly_DLV_Server.DLV_Server.DTO.IsThereAplayer;
import com.monopoly_DLV_Server.DLV_Server.DTO.Number;
import com.monopoly_DLV_Server.DLV_Server.DTO.Property;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monopoly_DLV_Server.DLV_Server.DTO.Player;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j //Questa genera una variabile 'log'
public class ServletCalls {

    @GetMapping(value = "/buyOrNotBuy")
    public String buyOrNotBuy(String playerJson, String propertyJson, String number){
        ObjectMapper objectMapper = new ObjectMapper();
        Player player = null;
        Property property = null;
        try {
            player = objectMapper.readValue(playerJson, Player.class);
            property = objectMapper.readValue(propertyJson, Property.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assert player != null;
        assert property != null;
        ArrayList<Object> facts = new ArrayList<>();
        facts.add(player);
        facts.add(property);
        facts.add(new Number(number));
        List<AnswerSet> answerSets = DLVHandler.getInstance().startGuess(facts, "buyOrNotBuy.dlv");
        IsThereAplayer is = null;
        for (AnswerSet a: answerSets){
            try {
                for (Object obj : a.getAtoms()){
                    if (obj instanceof IsThereAplayer) {
                        is = (IsThereAplayer) obj;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        assert is != null;
        return is.getName();
    }

    @GetMapping(value = "/manage")
    public String manage(){
        return "manage";
    }

    @GetMapping(value = "/trade")
    public String trade(){
        return "trade";
    }

    @GetMapping(value = "/auction")
    public String auction(){
        return "auction";
    }

}