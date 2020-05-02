package com.monopoly_DLV_Server.DLV_Server.DTO.proposeTradeOutput;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Id("propertyCounterPart")
public class PropertyCounterPart {
    @Param(0)
    public int propertyOffered;
    @Param(1)
    public int propertyRequested;
}
