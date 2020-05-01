package com.monopoly_DLV_Server.DLV_Server;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BuyOrNotBuyTests.class,
        UnmortgageTests.class,
        PayDebtTests.class,
        AcceptTradeTests.class,
        AuctionTests.class
})
public class Tests {
}