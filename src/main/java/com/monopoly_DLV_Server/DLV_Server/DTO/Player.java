package com.monopoly_DLV_Server.DLV_Server.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.Data;
import lombok.NoArgsConstructor;

//Questa annotazione genera: Getter, Setter, ToString, Equals, Hashcode e il costruttore con tutti
@Data
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
    boolean bidding;
    @Param(5)
    int index;
    boolean human;
    String dlv;

    public Player(String name, String color, int position, int money, int creditor, String jail, int jailroll, boolean communityChestJailCard, boolean chanceJailCard, boolean bidding, int index, boolean human, String dlv) {
        this.name = name;
        this.color = color;
        this.position = position;
        this.money = money;
        this.creditor = creditor;
        this.jail = jail;
        this.jailroll = jailroll;
        this.communityChestJailCard = communityChestJailCard;
        this.chanceJailCard = chanceJailCard;
        this.bidding = bidding;
        this.index = index;
        this.human = human;
        this.dlv = dlv;
    }

    public boolean getBidding() {
        return this.bidding;
    }

    public void setBidding(boolean bidding) {
        this.bidding = bidding;
    }
}
