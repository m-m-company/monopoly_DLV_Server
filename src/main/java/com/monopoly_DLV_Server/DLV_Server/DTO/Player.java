package com.monopoly_DLV_Server.DLV_Server.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Questa annotazione genera: Getter, Setter, ToString, Equals, Hashcode e il costruttore con tutti
@Data
@AllArgsConstructor
@NoArgsConstructor
@Id("player")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {
    @Param(0)
    String name;
    String color;
    @Param(1)
    int position;
    @Param(2)
    int money;
    int creditor;
    @Param(3)
    String jail;
    int jailroll;
    boolean communityChestJailCard;
    boolean chanceJailCard;
    @Param(4)
    String bidding;
    @Param(5)
    int index;
    boolean human;
    String dlv;
}
