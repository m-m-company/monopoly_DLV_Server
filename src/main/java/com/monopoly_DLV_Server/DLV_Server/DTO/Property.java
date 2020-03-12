package com.monopoly_DLV_Server.DLV_Server.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Id("property")
@JsonIgnoreProperties(ignoreUnknown = true) // Se gli arriva qualcosa che non conosce lo ignora
public class Property {
	@Param(0)
	private String name;
	@Param(1)
	private Integer owner = 0;
	@Param(2)
	private String mortgage = "false";
	@Param(3)
	private Integer house = 0;
	@Param(4)
	private Integer hotel = 0;
	@Param(5)
	private Integer groupNumber = 0;
	@Param(6)
	private Integer price;
	@Param(7)
	private Integer baserent = 0;
	@Param(8)
	private Integer index = 0;
}
