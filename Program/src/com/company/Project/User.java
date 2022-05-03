package com.company.Project;
/*==============================================================
Author: User
Datum:  
ProjektName:    Program
Beschreibung: 
==============================================================*/

import com.company.Project.Drawing.Draw;
import com.company.Project.Stock.Stock;
import com.company.Project.Stock.StockManager;

import java.util.HashMap;



public class User {
    //Stock Name, number of the stocks held
    private HashMap<String, Integer> stocks = new HashMap<>();
    private double money;


    public User(double startingMoney) {
        this.money = startingMoney;
    }

    public boolean inputController(StockManager sm) {
        SafeScanner scan = new SafeScanner();
        switch (scan.nextInt()) {
            case 0:
                System.out.println("How long do you want to wait(days)");
                System.out.println("Enter the number f.e. \"2\" for to wait two days");
                waitTime(sm, scan.nextInt());
                return true;
            case 1:
                buy(sm);
                return true;
            case 2:
                sell(sm);
                return true;
            case 3:
                printOwnedStocks(sm);
                return true;
            case 4:
                System.out.println("Available Money: " + money);
                return true;
            case 5:
                System.out.println("Total value of all stocks: " + calculateValueOfAllOwnedStocks(sm));
                return true;
            case 6:
                System.out.println("The Stocks that you can buy are the following:");
                getBuyableStocks(sm);
                return true;
            case 7:
                System.out.println("All stocks:");
                sm.printAllStocks();
                return true;
            case 10:
                return false;
        }
        return true;
    }

    private void waitTime(StockManager sm, int time) {
        for (int i = 0; i < time; i++) {
            sm.nextDay();
        }
    }

    private void buy(StockManager sm) {
        SafeScanner scan = new SafeScanner();
        String selectedStock = "";
        int numOfSelectedStock;
        getBuyableStocks(sm);
        do {
            System.out.println("Enter the Name of the stock that you want to buy!(0 to quit)");
            selectedStock = scan.nextString();
            if(selectedStock.equals("0")){
                return;
            }
            if (!sm.checkIfStockExists(selectedStock)) {
                System.out.println("The stock does not exist");
                selectedStock = "";
            }
        } while (selectedStock.equals(""));

        System.out.println("Enter how many times do you want to buy that stock(max: " + calcMaxBuyable(selectedStock,sm)+")");
        numOfSelectedStock = scan.nextInt();
        if (sm.checkIfBuyable(money, selectedStock, numOfSelectedStock)) {
            buyStock(sm,selectedStock, numOfSelectedStock);
        } else {
            System.out.println("You don't have enough money to but this stock in that amount");
        }
    }

    public void buyStock(StockManager sm,String selectedStock, int numOfSelectedStock) {
        if(numOfSelectedStock !=0 && (sm.calculatePrice(selectedStock, numOfSelectedStock))<=money){
            addStocks(selectedStock, numOfSelectedStock);
            money = money - sm.calculatePrice(selectedStock, numOfSelectedStock);
        }
    }

    public void sellStock(StockManager sm,String selectedStock, int numOfSelectedStock){
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
        SafeScanner scan = new SafeScanner();
        String stockToSell;
        int numToSell = 0;

        printOwnedStocks(sm);
        System.out.println("Which stock do you want to sell?(enter 0 to quit)");
        do{
         stockToSell = scan.nextString();
            System.out.println("Case 1:" + (sm.checkIfStockExists(stockToSell) && hasMinStocksOf(stockToSell,1)));
            System.out.println("Case 2:" + (stockToSell.equals("0")==false));
            if((sm.checkIfStockExists(stockToSell) && hasMinStocksOf(stockToSell,1)) ==false){
                System.out.println("Error. Following Problems occured:\n"+
                        "The stock name you entered does not exist or you are not in possesion of this stock");
            }
        }while ((sm.checkIfStockExists(stockToSell) && hasMinStocksOf(stockToSell,1)) ==false || (stockToSell.equals("0")));
        System.out.println("finished loop");
        if(sm.checkIfStockExists(stockToSell)){
            System.out.println("How many do you want to sell?(max: "+stocks.get(stockToSell)+ ")");
            do{
              numToSell=scan.nextInt();
            }while (hasMinStocksOf(stockToSell,numToSell)==false);
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
                        "|Name: " + stock.getName() + "\n"+
                        "|Current Value: " + stock.getValue() +"\n"+
                        "|Num: " + stocks.get(s) +"\n"+
                        "|Total Current value: " + stock.getValue() * stocks.get(s) +"\n"+
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

    private int calcMaxBuyable(String selectedStock, StockManager sm){
       Stock s = sm.getStock(selectedStock);
       return (int) (money/s.getValue());
    }

    public double getMoney() {
        return money;
    }

    public HashMap<String, Integer> getStocks() {
        return stocks;
    }
}
