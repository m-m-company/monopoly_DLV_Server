package com.monopoly_DLV_Server.DLV_Server.wrappers;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Id("booleanValue")
public class BooleanValue {
    @Param(0)
    private String booleanValue;
}
