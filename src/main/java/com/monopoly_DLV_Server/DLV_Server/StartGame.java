package com.monopoly_DLV_Server.DLV_Server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartGame {

    @GetMapping("/")
    public String startGame(){
        return "index";
    }

}
