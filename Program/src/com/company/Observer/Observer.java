package com.company.Observer;
/*==============================================================
Author: Observer
Datum:  
ProjektName:    Program
Beschreibung: 
==============================================================*/

import com.company.Stock;

import java.util.ArrayList;

public class Observer {
    ArrayList<Stock> stocks = new ArrayList<>();

    public void update(){
        for(Stock s:stocks){
            s.changeValue();
        }
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }
}
