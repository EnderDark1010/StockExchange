package com.company;
/*==============================================================
Author: User
Datum:  
ProjektName:    Program
Beschreibung: 
==============================================================*/

import java.util.HashMap;



public class User {
    //Stock Name, number of the stocks held
    private HashMap<String, Integer> stocks = new HashMap<>();
    private double money;
    SafeScanner scan = new SafeScanner();

    public User(double startingMoney) {
        this.money = startingMoney;
    }

    public void inputController(StockManager sm) {

        switch (scan.nextInt()) {
            case 0:
                System.out.println("How long do you want to wait(days)");
                System.out.println("Enter the number f.e. \"2\" for to wait two days");
                waitTime(sm, scan.nextInt());
                break;
            case 1:
                buy(sm);
                break;
            case 2:
                sell(sm);
                break;
            case 3:
                printOwnedStocks(sm);
                break;
            case 4:
                System.out.println("Available Money: " + money);
                break;
            case 5:
                System.out.println("Total value of all stocks: " + calculateValueOfAllOwnedStocks(sm));
                break;
        }
    }

    private void waitTime(StockManager sm, int time) {
        for (int i = 0; i < time; i++) {
            sm.nextDay();
        }
    }

    private void buy(StockManager sm) {
        String selectedStock = "";
        int numOfSelectedStock;
        getBuyableStocks(sm);
        do {
            System.out.println("Enter the Name of the stock that you want to buy!");
            selectedStock = scan.nextString();
            if (!sm.checkIfStockExists(selectedStock)) {
                System.out.println("The stock does not exist");
                selectedStock = "";
            }
        } while (selectedStock.equals(""));

        System.out.println("Enter how many times do you want to buy that stock");
        numOfSelectedStock = scan.nextInt();
        if (sm.checkIfBuyable(money, selectedStock, numOfSelectedStock)) {
            buyStock(sm,selectedStock, numOfSelectedStock);
        } else {
            System.out.println("You don't have enough money to but this stock in that amount");
        }
    }

    private void buyStock(StockManager sm,String selectedStock, int numOfSelectedStock) {
        addStocks(selectedStock, numOfSelectedStock);
        money = money - sm.calculatePrice(selectedStock, numOfSelectedStock);
    }
    private void sellStock(StockManager sm,String selectedStock, int numOfSelectedStock){
        removeStock(selectedStock,numOfSelectedStock);
        money = money + sm.calculatePrice(selectedStock, numOfSelectedStock);

    }

    private void addStocks(String selectedStock, int numOfSelectedStock) {
        if (!stocks.containsKey(selectedStock)) {
            stocks.put(selectedStock, 0);
        }
        stocks.put(selectedStock, stocks.get(selectedStock) + numOfSelectedStock);
    }
    private void removeStock(String selectedStock, int numOfSelectedStock){
        if(stocks.containsKey(selectedStock)){
            stocks.put(selectedStock,stocks.get(selectedStock).intValue()-numOfSelectedStock);
            if(stocks.get(selectedStock) <=0){
                stocks.remove(selectedStock);
            }
        }
    }

    private void sell(StockManager sm) {
        String stockToSell;
        int numToSell = 0;

        printOwnedStocks(sm);
        System.out.println("Which stock do you want to sell?(enter 0 to quit)");
        do{
         stockToSell = scan.nextString();
        }while ((sm.checkIfStockExists(stockToSell)&& hasMinStocksOf(stockToSell,1)) || stockToSell.equals("0"));
        if(sm.checkIfStockExists(stockToSell)){
            System.out.println("How many do you want to sell?");
            do{
              numToSell=scan.nextInt();
            }while (hasMinStocksOf(stockToSell,numToSell));
            sellStock(sm,stockToSell,numToSell);
        }
    }

    private void getBuyableStocks(StockManager sm) {
        sm.printStocksInPriceRange(0, money);
    }

    private void printOwnedStocks(StockManager sm) {
        Stock stock;
        for (String s : stocks.keySet()) {
            stock = sm.getStock(s);
            if (stock != null) {
                System.out.println(
                        "----------\n" +
                                "|Name: " + stock.getName() + stock.getValue() +
                                "|Current Value: " + stock.getValue() +
                                "|Num: " + stocks.get(s) +
                                "|Total Current value: " + stock.getValue() * stocks.get(s) +
                                "----------");
            }
        }
    }

    private double calculateValueOfAllOwnedStocks(StockManager sm) {
        double totalValue = 0;
        Stock stock;
        for (String s : stocks.keySet()) {
            stock = sm.getStock(s);
            if (stock != null) {
                totalValue = totalValue + sm.getStock(s).getValue() * stocks.get(s);
            }
        }
        return totalValue;
    }

    private boolean hasMinStocksOf(String s,int minStocks){
        if(stocks.containsKey(s)){
            return stocks.get(s) >= minStocks;
        }
        return false;
    }
}
