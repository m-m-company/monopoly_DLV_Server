package com.monopoly_DLV_Server.DLV_Server.DTO;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Id("price")
public class Price {
    @Param(0)
    int price;

    public Price(int price){
        this.price = price;
    }
}
