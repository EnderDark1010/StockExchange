package com.company.Project.Stock;
/*==============================================================
Author: Memento
Datum:  
ProjektName:    Program
Beschreibung: 
==============================================================*/

import com.company.Project.Observer.Observer;

import java.util.HashMap;
import java.util.Stack;

public class StockManager {
    private HashMap<Stock, Stack<Double>> memory = new HashMap<>();
    private HashMap<String, Stock> stocks = new HashMap<>();
    private Observer o = new Observer();
    private StockFactory sf = new StockFactory(o);

    public StockManager(int numOfCheap, int numOfMiddle, int numOfExpensive) {
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

    public void nextDay() {
        for (Stock s : stocks.values()) {
            if (!memory.containsKey(s)) {
                memory.put(s, new Stack<Double>());
            }
            memory.get(s).push(s.getValue());
        }
        o.update();
    }

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

    public Stock getStock(String s) {
        if (checkIfStockExists(s)) {
            return stocks.get(s);
        } else {
            return null;
        }
    }
    public boolean checkIfStockExists(String s){
        if(stocks.containsKey(s)){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkIfBuyable(double money, String selectedStock, int numOfSelectedStock) {
        if (money < calculatePrice(selectedStock,numOfSelectedStock)) {
            return false;
        } else {
            return true;
        }
    }

    public double calculatePrice(String selectedStock, int numOfSelectedStock) {
    return getStock(selectedStock).getValue() * numOfSelectedStock;
    }

    public void printAllStocks() {
        for (Stock s : stocks.values()) {
                System.out.println(s.getName() + ": " + s.getValue());
        }
    }
    public void addStock(Stock s){
        stocks.put(s.getName(),s);
    }

    public Observer getObserver() {
        return o;
    }

    public HashMap<Stock, Stack<Double>> getMemory() {
        return memory;
    }
}
