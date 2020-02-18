package com.monopoly_DLV_Server.DLV_Server.DTO;

import it.unical.mat.embasp.languages.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Id("property")
public class Property {
    String name;
	String pricetext;
	String color;
	int owner;
	boolean mortgage;
	int house;
	int hotel;
	int groupNumber;
	int price;
	int baserent;
	int rent1;
	int rent2;
	int rent3;
	int rent4;
	int rent5;
	int landcount;
	int houseprice;
	int [] group;
	int index;
}
