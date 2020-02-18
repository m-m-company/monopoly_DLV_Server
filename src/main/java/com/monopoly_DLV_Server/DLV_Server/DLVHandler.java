package com.monopoly_DLV_Server.DLV_Server;

import com.monopoly_DLV_Server.DLV_Server.DTO.Number;
import com.monopoly_DLV_Server.DLV_Server.DTO.Player;
import com.monopoly_DLV_Server.DLV_Server.DTO.Property;
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

import java.io.File;
import java.util.Collection;
import java.util.List;

@Slf4j
public class DLVHandler {
    private static String pathToExe = null;
    private static DLVHandler instance = null;
    private static final String pathToEncodings = "src/main/resources/encodings/";
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
        ASPMapper.getInstance().registerClass(Player.class);
        ASPMapper.getInstance().registerClass(Property.class);
        ASPMapper.getInstance().registerClass(Number.class);
    }

    private void setPathToExe(){
        if (DLVHandler.pathToExe == null) {
            //Cannot do this on constructor 'cause File(".") doesn't exist, java reasons
            StringBuilder path = new StringBuilder(new File(".").getAbsolutePath());
            path.deleteCharAt(path.indexOf("."));
            pathToExe = path + File.separator + "src" + File.separator + "main" +
                    File.separator + "resources" + File.separator + "libs" + File.separator + "dlv2.win.x64_5";
        }
    }

    public List<AnswerSet> startGuess(Collection<Object> facts, String encoding){
        this.setEncoding(DLVHandler.pathToEncodings+encoding);
        for (Object obj : facts)
            this.addFact(obj);
        this.addFactsToHandler();
        return this.getAnswerSets();
    }

    private DLVHandler(){
        setPathToExe();
        handler = new DesktopHandler(new DLV2DesktopService(DLVHandler.pathToExe));
        facts = new ASPInputProgram();
        try {
            registerClasses();
        } catch (ObjectNotValidException | IllegalAnnotationException e) {
            e.printStackTrace();
        }
        encoding = new ASPInputProgram();
    }

    private void addFact(Object object){
        try {
            facts.addObjectInput(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addFactsToHandler(){
        handler.addProgram(facts);
    }

    private void setEncoding(String path){
        facts.clearAll();
        handler.removeAll();
        encoding.clearAll();
        encoding.addFilesPath(path);
        handler.addProgram(encoding);
    }

    private List<AnswerSet> getAnswerSets(){
        Output o = handler.startSync();
        AnswerSets answerSets = (AnswerSets) o;
        return answerSets.getAnswersets();
    }

}
