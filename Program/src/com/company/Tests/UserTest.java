package com.company.Tests;

import com.company.Project.Stock.Stock;
import com.company.Project.Stock.StockManager;
import com.company.Project.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/*==============================================================
Author: UserTest
Datum:  
ProjektName:    Program
Beschreibung: 
==============================================================*/
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserTest {
    private User user;
    private StockManager sm;

    private String testStockName = "Testing";
    private double testStockValue = 100;

    @BeforeAll
    public void setUp() {
        user = new User(500);
        sm = new StockManager(0, 0, 0);
        Stock s = new Stock(testStockName, testStockValue, sm.getObserver());
        sm.addStock(s);
    }

    @Test
    @DisplayName("Checks if the user can buy 5 Stocks that cost 500 cash(total) when the user has 500 cash")
    public void testBuy_Successful() {
        user.buyStock(sm, testStockName, 5);
        assertEquals(user.getMoney(), 0);
        assertEquals(user.getStocks().get(testStockName), 5);
    }

    @Test
    @DisplayName("Checks that the user can not buy 10 stocks that cost 1000 cash(total) when the user has 500 cash")
    public void testBuy_Unsuccessful() {
        user.buyStock(sm, testStockName, 10);
        assertEquals(user.getMoney(), 500);
        assertFalse(user.getStocks().containsKey(testStockName));
    }



}