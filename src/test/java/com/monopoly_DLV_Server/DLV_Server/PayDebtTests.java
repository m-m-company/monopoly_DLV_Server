package com.monopoly_DLV_Server.DLV_Server;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PayDebtTests {
    ServletCalls servletCalls = new ServletCalls();

    @Test
    public void reachable(){
        Assert.assertNull(servletCalls.payDebt(null, null));
    }
}
