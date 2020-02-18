package com.monopoly_DLV_Server.DLV_Server.DTO;

import it.unical.mat.embasp.languages.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Id("propertyNumberWithSameColor")
public class Number {
    String number;
}
