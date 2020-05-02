package com.monopoly_DLV_Server.DLV_Server;

import com.monopoly_DLV_Server.DLV_Server.DTO.Number;
import com.monopoly_DLV_Server.DLV_Server.DTO.*;
import com.monopoly_DLV_Server.DLV_Server.DTO.proposeTradeOutput.PropertyCounterPart;
import com.monopoly_DLV_Server.DLV_Server.DTO.proposeTradeOutput.PropertyOffer;
import com.monopoly_DLV_Server.DLV_Server.DTO.proposeTradeOutput.TradeMoney;
import com.monopoly_DLV_Server.DLV_Server.DTO.proposeTradeOutput.TradeProperty;
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
    private static final String pathToEncodings = "src/main/resources/encodings/";
    private static String pathToExe = null;
    private static DLVHandler instance = null;
    private Handler handler;
    private InputProgram facts;
    private InputProgram encoding;

    private DLVHandler() {
        this.setPathToExe();
        this.handler = new DesktopHandler(new DLV2DesktopService(DLVHandler.pathToExe));
        this.facts = new ASPInputProgram();
        try {
            this.registerClasses();
        } catch (ObjectNotValidException | IllegalAnnotationException e) {
            e.printStackTrace();
        }
        this.encoding = new ASPInputProgram();
    }

    private void setPathToExe() {
        if (DLVHandler.pathToExe == null) {
            //Cannot do this on constructor 'cause File(".") doesn't exist, java reasons
            StringBuilder path = new StringBuilder(new File(".").getAbsolutePath());
            path.deleteCharAt(path.indexOf("."));
            pathToExe = path + File.separator + "src" + File.separator + "main" +
                    File.separator + "resources" + File.separator + "libs" + File.separator + "dlv2.win.x64_5";
        }
    }

    private void registerClasses() throws ObjectNotValidException, IllegalAnnotationException {
        ASPMapper.getInstance().registerClass(Player.class);
        ASPMapper.getInstance().registerClass(Property.class);
        ASPMapper.getInstance().registerClass(PropertyTrade.class);
        ASPMapper.getInstance().registerClass(Number.class);
        ASPMapper.getInstance().registerClass(BooleanValue.class);
        ASPMapper.getInstance().registerClass(BuyHouse.class);
        ASPMapper.getInstance().registerClass(Unmortgage.class);
        ASPMapper.getInstance().registerClass(ActionProperty.class);
        ASPMapper.getInstance().registerClass(PropertyCounterPart.class);
        ASPMapper.getInstance().registerClass(TradeProperty.class);
        ASPMapper.getInstance().registerClass(TradeMoney.class);
        ASPMapper.getInstance().registerClass(PropertyOffer.class);
    }

    public static DLVHandler getInstance() {
        if (instance == null) {
            instance = new DLVHandler();
        }
        return instance;
    }

    public List<AnswerSet> startGuess(Collection<Object> facts, String encoding) {
        this.setEncoding(DLVHandler.pathToEncodings + encoding);
        for (Object obj : facts)
            this.addFact(obj);
        this.addFactsToHandler();
        return this.getAnswerSets();
    }

    private void setEncoding(String path) {
        this.facts.clearAll();
        this.handler.removeAll();
        this.encoding.clearAll();
        this.encoding.addFilesPath(path);
        this.handler.addProgram(this.encoding);
    }

    private void addFact(Object object) {
        try {
            this.facts.addObjectInput(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addFactsToHandler() {
        this.handler.addProgram(this.facts);
    }

    private List<AnswerSet> getAnswerSets() {
        Output o = this.handler.startSync();
        AnswerSets answerSets = (AnswerSets) o;
        return answerSets.getAnswersets();
    }

}
