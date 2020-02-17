package com.monopoly_DLV_Server.DLV_Server;

import com.monopoly_DLV_Server.DLV_Server.DTO.IsThereAplayer;
import com.monopoly_DLV_Server.DLV_Server.DTO.Price;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monopoly_DLV_Server.DLV_Server.DTO.Player;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

@RestController
@Slf4j //Questa genera una variabile 'log'
public class BuyOrNot {
    private static Handler handler;
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
    public String prova(String playersArray, Integer cost){
        ArrayList<Player> gamers = generatePlayers(playersArray);
        //TODO: scoprire come mettere un path relativo
        handler = new DesktopHandler(new DLV2DesktopService("C:\\Users\\matte\\Desktop\\AI\\monopoly_DLV_Server\\src\\main\\resources\\libs\\dlv2.win.x64_5"));
        InputProgram facts = new ASPInputProgram();
        try {
            ASPMapper.getInstance().registerClass(IsThereAplayer.class);
            ASPMapper.getInstance().registerClass(Player.class);
            ASPMapper.getInstance().registerClass(Price.class);
        } catch (ObjectNotValidException | IllegalAnnotationException e) {
            e.printStackTrace();
        }
        gamers.forEach(gamer -> {
            try {
                facts.addObjectInput(gamer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        try {
            facts.addObjectInput(new Price(cost));
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.addProgram(facts);
        InputProgram encoding = new ASPInputProgram();
        encoding.addFilesPath("src/main/resources/encodings/buyOrNotBuy.dlv");
        handler.addProgram(encoding);
        Output o =handler.startSync();
        AnswerSets answerSets = (AnswerSets) o;
        IsThereAplayer is = null;
        log.error(answerSets.getAnswerSetsString());
        for (AnswerSet a: answerSets.getAnswersets()){
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
}