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
}
