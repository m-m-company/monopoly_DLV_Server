package com.monopoly_DLV_Server.DLV_Server.DTO;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//Questa annotazione genera: Getter, Setter, ToString, Equals, Hashcode e il costruttore con tutti
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Id("player")
public class Player {
	@Param(0)
   	String name;
	String color;
	@Param(1)
	int position;
	@Param(2)
	int money;
	int creditor;
	int index;
	@Param(3)
	String jail;
	int jailroll;
	boolean communityChestJailCard;
	boolean chanceJailCard;
	boolean bidding;
	boolean human;
	@Param(4)
	String dlv;
}
