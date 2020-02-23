package com.monopoly_DLV_Server.DLV_Server.DTO;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.Data;

@Data
@Id("unmortgage")
public class Unmortgage {
    @Param(0)
    Integer index;
}
