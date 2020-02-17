package com.monopoly_DLV_Server.DLV_Server;

import com.monopoly_DLV_Server.DLV_Server.DTO.IsThereAplayer;
import com.monopoly_DLV_Server.DLV_Server.DTO.Price;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monopoly_DLV_Server.DLV_Server.DTO.Player;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j //Questa genera una variabile 'log'
public class ServletCalls {

    private ArrayList<Player> generatePlayers(String players){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //E' così complicato solo perché voglio usare un ArrayList, con Player[] è molto più semplice ma poi dovremmo usarlo come un array semplice
            // Player[] array = objectMapper.readValue(players, Player[].class);
            return objectMapper.readValue(players,
                    objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Player.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/buyOrNotBuy")
    public String buyOrNotBuy(String playersArray, Integer cost){
        DLVHandler.getInstance().setEncoding("src/main/resources/encodings/buyOrNotBuy.dlv");
        DLVHandler.getInstance().addFact(new Price(cost));
        ArrayList<Player> gamers = generatePlayers(playersArray);
        gamers.forEach(gamer -> {
            log.error(gamer.toString());
            DLVHandler.getInstance().addFact(gamer);
        });
        DLVHandler.getInstance().addFactsToHandler();
        List<AnswerSet> answerSets = DLVHandler.getInstance().getAnswerSets();
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