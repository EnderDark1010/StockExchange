package com.company.Project.Game;
/*==============================================================
Author: User
Datum:  
ProjektName:    Program
Beschreibung: 
==============================================================*/

import com.company.Project.BonusAndUtillity.SafeScanner;

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
        switch (scan.nextIntPositive()) {
            case 0:
                System.out.println("How long do you want to wait(days)");
                System.out.println("Enter the number f.e. \"2\" for to wait two days");
                waitTime(sm, scan.nextIntPositive());
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
                System.out.println("Available Money: " + money + "\nTotal value of all stocks: " + calculateValueOfAllOwnedStocks(sm));
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

    /**
     * waits x days
     *
     * @param sm   the stock manager whichs nextDay methode should be called
     * @param time how many times the methode should be called
     */
    private void waitTime(StockManager sm, int time) {
        for (int i = 0; i < time; i++) {
            sm.nextDay();
        }
    }

    /**
     * Input methode for the buy
     *
     * @param sm the stockmanager to check stuff
     */
    private void buy(StockManager sm) {
        SafeScanner scan = new SafeScanner();
        String selectedStock = "";
        int numOfSelectedStock;
        getBuyableStocks(sm);
        do {
            System.out.println("Enter the Name of the stock that you want to buy!(0 to quit)");
            selectedStock = scan.nextString();
            selectedStock = selectedStock.toUpperCase();
            if (selectedStock.equals("0")) {
                return;
            }
            if (!sm.checkIfStockExists(selectedStock)) {
                System.out.println("The stock does not exist");
                selectedStock = "";
            }
            if (!sm.checkIfBuyable(money, selectedStock, 1)) {
                System.out.println("You do not have the money to buy this stock");
                selectedStock = "";
            }
        } while (selectedStock.equals(""));

        System.out.println("Enter how many times do you want to buy that stock(max: " + calcMaxBuyable(selectedStock, sm) + ")");
        numOfSelectedStock = scan.nextIntPositive();
        if (sm.checkIfBuyable(money, selectedStock, numOfSelectedStock)) {
            buyStock(sm, selectedStock, numOfSelectedStock);
        } else {
            System.out.println("You don't have enough money to buy this stock in that amount");
        }
    }

    /**
     * buy methode
     *
     * @param sm                 stockmanager for getting stock
     * @param selectedStock      name of the selected stock to buy
     * @param numOfSelectedStock count to buy
     */
    public void buyStock(StockManager sm, String selectedStock, int numOfSelectedStock) {
        if (numOfSelectedStock != 0 && (sm.calculatePrice(selectedStock, numOfSelectedStock)) <= money) {
            addStocks(selectedStock, numOfSelectedStock);
            money = money - sm.calculatePrice(selectedStock, numOfSelectedStock);
        }
    }

    /**
     * sells a selected stock specific number of times
     *
     * @param sm                 stock manager to get the price of the stock
     * @param selectedStock      name of the stock
     * @param numOfSelectedStock number of the stocks to sell
     */
    public void sellStock(StockManager sm, String selectedStock, int numOfSelectedStock) {
        removeStock(selectedStock, numOfSelectedStock);
        money = money + sm.calculatePrice(selectedStock, numOfSelectedStock);

    }

    /**
     * input methode for the sell methode
     *
     * @param sm
     */
    private void sell(StockManager sm) {
        SafeScanner scan = new SafeScanner();
        String stockToSell;
        int numToSell = 0;
        boolean cont = true;
        printOwnedStocks(sm);
        System.out.println("Which stock do you want to sell?(enter 0 to quit)");
        do {
            stockToSell = scan.nextString().toUpperCase();
            if (stockToSell.equals("0")) {
                cont = false;
            } else if ((sm.checkIfStockExists(stockToSell) && hasMinStocksOf(stockToSell, 1)) == false) {
                System.out.println("Error. Following Problems occured:\n" +
                        "The stock name you entered does not exist or you are not in possesion of this stock");
            } else if (sm.checkIfStockExists(stockToSell) && hasMinStocksOf(stockToSell, 1)) {
                cont = false;
            }
        } while (cont);
        if (sm.checkIfStockExists(stockToSell)) {
            System.out.println("How many do you want to sell?(max: " + stocks.get(stockToSell) + ")");
            do {
                numToSell = scan.nextIntPositive();
            } while (hasMinStocksOf(stockToSell, numToSell) == false);
            sellStock(sm, stockToSell, numToSell);
        }
    }

    private void addStocks(String selectedStock, int numOfSelectedStock) {
        if (!stocks.containsKey(selectedStock)) {
            stocks.put(selectedStock, 0);
        }
        stocks.put(selectedStock, stocks.get(selectedStock) + numOfSelectedStock);
    }

    private void removeStock(String selectedStock, int numOfSelectedStock) {
        if (stocks.containsKey(selectedStock)) {
            stocks.put(selectedStock, stocks.get(selectedStock).intValue() - numOfSelectedStock);
            if (stocks.get(selectedStock) <= 0) {
                stocks.remove(selectedStock);
            }
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
                                "|Name: " + stock.getName() + "\n" +
                                "|Current Value: " + stock.getValue() + "\n" +
                                "|Num: " + stocks.get(s) + "\n" +
                                "|Total Current value: " + stock.getValue() * stocks.get(s) + "\n" +
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

    private boolean hasMinStocksOf(String s, int minStocks) {
        if (stocks.containsKey(s)) {
            return stocks.get(s) >= minStocks;
        }
        return false;
    }

    private int calcMaxBuyable(String selectedStock, StockManager sm) {
        Stock s = sm.getStock(selectedStock);
        return (int) (money / s.getValue());
    }
}
