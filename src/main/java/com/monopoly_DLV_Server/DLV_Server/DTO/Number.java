package com.monopoly_DLV_Server.DLV_Server.DTO;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Id("number")
public class Number {
    @Param(0)
    public Integer number;
    @Param(1)
    public String semantic;
}
