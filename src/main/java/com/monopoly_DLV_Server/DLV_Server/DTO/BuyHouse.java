package com.monopoly_DLV_Server.DLV_Server.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Id("buyHouse")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuyHouse {
    @Param(0)
    private Integer index;
    @Param(1)
    private Integer times;
}
