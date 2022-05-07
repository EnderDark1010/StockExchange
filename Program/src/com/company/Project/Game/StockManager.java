package com.company.Project.Game;
/*==============================================================
Author: Memento
Datum:  
ProjektName:    Program
Beschreibung: 
==============================================================*/

import java.util.HashMap;
import java.util.Stack;

public class StockManager {
    private static StockManager singleton= null;
    private HashMap<Stock, Stack<Double>> memory = new HashMap<>();
    private HashMap<String, Stock> stocks = new HashMap<>();
    private Observer o = new Observer();
    private StockFactory sf = new StockFactory(o);


    public static StockManager getInstance(){
        if (singleton == null)
            singleton = new StockManager(5,5,5);
        return singleton;
    }


    /**
     * Generates the Stock Manager
     * Parameters decide the price starting class
     * @param numOfCheap number of cheap stocks
     * @param numOfMiddle number of middle value stocks
     * @param numOfExpensive number of expensive stocks
     */
    private StockManager(int numOfCheap, int numOfMiddle, int numOfExpensive) {
        Stock s;
        for (int i = 0; i < numOfCheap; i++) {
            s = sf.generateStock(1, 10);
            stocks.put(s.getName(), s);
        }
        for (int i = 0; i < numOfMiddle; i++) {
            s = sf.generateStock(50, 100);
            stocks.put(s.getName(), s);
        }
        for (int i = 0; i < numOfExpensive; i++) {
            s = sf.generateStock(300, 750);
            stocks.put(s.getName(), s);
        }
    }

    /**
     * updates all stocks once
     */
    public void nextDay() {
        for (Stock s : stocks.values()) {
            if (!memory.containsKey(s)) {
                memory.put(s, new Stack<Double>());
            }
            memory.get(s).push(s.getValue());
        }
        o.update();
    }

    /**
     * Prints socks in a price range
     * @param start min cost
     * @param end max cost
     */
    public void printStocksInPriceRange(double start, double end) {
        if (!(start < end)) {
            double temp = end;
            end = start;
            start = temp;
        }
        for (Stock s : stocks.values()) {
            if (s.getValue() < end && start < s.getValue()) {
                System.out.println(s.getName() + ": " + s.getValue());
            }
        }
    }

    /**
     * returns the stock with a specific name
     * @param s name of the stock that should be returned
     * @return  returns the stock if found or null if it does not exist
     */
    public Stock getStock(String s) {
        if (checkIfStockExists(s)) {
            return stocks.get(s);
        } else {
            return null;
        }
    }

    /**
     * checks if a stock is inside the stocks hashmap
     * @param s the stock that is checked for
     * @return if the stock is found or not
     */
    public boolean checkIfStockExists(String s) {
        if (stocks.containsKey(s)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the amount of money is enough to buy a specific amount of a stock
     * @param money the money that is being checked with
     * @param selectedStock the stock being tested for
     * @param numOfSelectedStock the amount of stocks to check for
     * @return true if the money is enough to buy the amount
     */
    public boolean checkIfBuyable(double money, String selectedStock, int numOfSelectedStock) {
        if (checkIfStockExists(selectedStock)) {
            if (money < calculatePrice(selectedStock, numOfSelectedStock)) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public double calculatePrice(String selectedStock, int numOfSelectedStock) {
        return getStock(selectedStock).getValue() * numOfSelectedStock;
    }

    public void printAllStocks() {
        for (Stock s : stocks.values()) {
            System.out.println(s.getName() + ": " + s.getValue());
        }
    }

    public Observer getObserver() {
        return o;
    }

    public HashMap<Stock, Stack<Double>> getMemory() {
        return memory;
    }
}
