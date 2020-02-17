package com.monopoly_DLV_Server.DLV_Server;

import com.monopoly_DLV_Server.DLV_Server.DTO.IsThereAplayer;
import com.monopoly_DLV_Server.DLV_Server.DTO.Player;
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

import java.util.List;

public class DLVHandler {

    private static DLVHandler instance = null;
    private Handler handler;
    private InputProgram facts;
    private InputProgram encoding;

    public static DLVHandler getInstance(){
        if(instance == null){
            instance = new DLVHandler();
        }
        return instance;
    }

    //TODO: Is it really needed?
    private void registerClasses() throws ObjectNotValidException, IllegalAnnotationException {
        ASPMapper.getInstance().registerClass(IsThereAplayer.class);
        ASPMapper.getInstance().registerClass(Player.class);
        ASPMapper.getInstance().registerClass(Price.class);
    }

    private DLVHandler(){
        handler = new DesktopHandler(new DLV2DesktopService("C:\\Users\\mlori\\Desktop\\monopoly_DLV_Server\\src\\main\\resources\\libs\\dlv2.win.x64_5"));
        facts = new ASPInputProgram();
        try {
            registerClasses();
        } catch (ObjectNotValidException | IllegalAnnotationException e) {
            e.printStackTrace();
        }
        encoding = new ASPInputProgram();
    }

    public void addFact(Object object){
        try {
            facts.addObjectInput(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addFactsToHandler(){
        handler.addProgram(facts);
    }

    public void setEncoding(String path){
        facts.clearAll();
        handler.removeAll();
        encoding.clearAll();
        encoding.addFilesPath(path);
        handler.addProgram(encoding);
    }

    public List<AnswerSet> getAnswerSets(){
        Output o = handler.startSync();
        AnswerSets answerSets = (AnswerSets) o;
        return answerSets.getAnswersets();
    }

}
