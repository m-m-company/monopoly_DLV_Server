package com.monopoly_DLV_Server.DLV_Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class StartGame {
    @GetMapping("/")
    public String startGame(){
        return "index";
    }
    @GetMapping("/provaOggetto")
    public String provaOggetto(ProvaDTO prova){
        System.out.println(prova.ciao);
        System.out.println(prova.c);
        return "index";
    }
}
