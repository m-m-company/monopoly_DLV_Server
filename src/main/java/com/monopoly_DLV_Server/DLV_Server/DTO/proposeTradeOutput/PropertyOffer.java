package com.monopoly_DLV_Server.DLV_Server.DTO.proposeTradeOutput;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Id("propertyOffer")
public class PropertyOffer {
    @Param(0)
    public int price;
    @Param(1)
    public int property;
}
