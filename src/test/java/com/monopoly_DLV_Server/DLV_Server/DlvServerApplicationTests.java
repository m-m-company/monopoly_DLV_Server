package com.monopoly_DLV_Server.DLV_Server;

import com.monopoly_DLV_Server.DLV_Server.DTO.Player;
import com.monopoly_DLV_Server.DLV_Server.DTO.Property;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;

@SpringBootTest
class DlvServerApplicationTests {

	@InjectMocks
	ServletCalls servlet;

	@Test
	void contextLoads() {
		testBuyOrNotBuy();
	}

	public void testBuyOrNotBuy(){
		try {
			buyWithSameColor();
			//dontBuyForLimitMoney();
			dontBuy();
			buy();
			buyForTheGroup1();
			buyForTheGroup2();
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void buyWithSameColor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Player player = new Player("name", "color", 0, 300, 0, 0, "jail", 0, false, false, false, false, "dlv");
		Property property = new Property("name", "color", 0, "mortgage", 0, 0, 0, 200, 0, 0, 0, 0, 0, 0, 0);
		int numberOfTheSameColor = 2;
		String playerJson = JsonConverter.getInstance().toJson(player);
		String propertyJson = JsonConverter.getInstance().toJson(property);
		Assert.assertEquals(true, servlet.buyOrNotBuy(playerJson, propertyJson, numberOfTheSameColor));
	}

	//TODO: this test doesn't work
	public void dontBuyForLimitMoney() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Player player = new Player("name", "color", 0, 300, 0, 0, "jail", 0, false, false, false, false, "dlv");
		Property property = new Property("name", "color", 0, "mortgage", 0, 0, 0, 200, 0, 0, 0, 0, 0, 0, 0);
		int numberOfTheSameColor = 0;
		String playerJson = JsonConverter.getInstance().toJson(player);
		String propertyJson = JsonConverter.getInstance().toJson(property);
		Assert.assertEquals(false, servlet.buyOrNotBuy(playerJson, propertyJson, numberOfTheSameColor));
	}

	public void dontBuy() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Player player = new Player("name", "color", 0, 300, 0, 0, "jail", 0, false, false, false, false, "dlv");
		Property property = new Property("name", "color", 0, "mortgage", 0, 0, 0, 400, 0, 0, 0, 0, 0, 0, 0);
		int numberOfTheSameColor = 1;
		String playerJson = JsonConverter.getInstance().toJson(player);
		String propertyJson = JsonConverter.getInstance().toJson(property);
		Assert.assertEquals(false, servlet.buyOrNotBuy(playerJson, propertyJson, numberOfTheSameColor));
	}

	public void buy() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Player player = new Player("name", "color", 0, 600, 0, 0, "jail", 0, false, false, false, false, "dlv");
		Property property = new Property("name", "color", 0, "mortgage", 0, 0, 0, 200, 0, 0, 0, 0, 0, 0, 0);
		int numberOfTheSameColor = 0;
		String playerJson = JsonConverter.getInstance().toJson(player);
		String propertyJson = JsonConverter.getInstance().toJson(property);
		Assert.assertEquals(true, servlet.buyOrNotBuy(playerJson, propertyJson, numberOfTheSameColor));
	}

	public void buyForTheGroup1() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Player player = new Player("name", "color", 0, 300, 0, 0, "jail", 0, false, false, false, false, "dlv");
		Property property = new Property("name", "color", 0, "mortgage", 0, 0, 1, 200, 0, 0, 0, 0, 0, 0, 0);
		int numberOfTheSameColor = 0;
		String playerJson = JsonConverter.getInstance().toJson(player);
		String propertyJson = JsonConverter.getInstance().toJson(property);
		Assert.assertEquals(true, servlet.buyOrNotBuy(playerJson, propertyJson, numberOfTheSameColor));
	}

	public void buyForTheGroup2() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Player player = new Player("name", "color", 0, 300, 0, 0, "jail", 0, false, false, false, false, "dlv");
		Property property = new Property("name", "color", 0, "mortgage", 0, 0, 2, 200, 0, 0, 0, 0, 0, 0, 0);
		int numberOfTheSameColor = 0;
		String playerJson = JsonConverter.getInstance().toJson(player);
		String propertyJson = JsonConverter.getInstance().toJson(property);
		Assert.assertEquals(true, servlet.buyOrNotBuy(playerJson, propertyJson, numberOfTheSameColor));
	}

}
