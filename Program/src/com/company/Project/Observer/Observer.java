package com.company.Project.Observer;
/*==============================================================
Author: Observer
Datum:  
ProjektName:    Program
Beschreibung: 
==============================================================*/

import com.company.Project.Stock.Stock;

import java.util.ArrayList;

public class Observer {
    private ArrayList<Stock> stocks = new ArrayList<>();
    private double highestPriceRecord = 0;
    private int numOfUpdates = 0;

    public void update(){
        numOfUpdates++;
        for(Stock s:stocks){
            s.changeValue();
            if(s.getValue()>highestPriceRecord){
                highestPriceRecord= s.getValue();
            }
        }
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public double getHighestPriceRecord() {
        return highestPriceRecord;
    }

    public int getNumOfUpdates() {
        return numOfUpdates;
    }
}
