package com.company.Project.Game;
/*==============================================================
Author: Observer
Datum:  
ProjektName:    Program
Beschreibung: 
==============================================================*/

import java.util.ArrayList;

public class Observer {
    private ArrayList<Stock> stocks = new ArrayList<>();
    private double highestPriceRecorded = 0;
    private double lowestPriceRecorded = -1;
    private int numOfUpdates = 0;

    public void update() {
        numOfUpdates++;
        for (Stock s : stocks) {
            s.changeValue();
            if (s.getValue() > highestPriceRecorded) {
                highestPriceRecorded = s.getValue();
            } else if (s.getValue() < lowestPriceRecorded || lowestPriceRecorded==-1) {
                lowestPriceRecorded = s.getValue();
            }


        }
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public double getHighestPriceRecorded() {
        return highestPriceRecorded;
    }

    public double getLowestPriceRecorded() {
        return lowestPriceRecorded;
    }

    public int getNumOfUpdates() {
        return numOfUpdates;
    }
}
