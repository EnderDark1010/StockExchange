package com.company.Project.Stock;
/*==============================================================
Author: Stock
Datum:  
ProjektName:    Stock
Beschreibung: 
==============================================================*/

import com.company.Project.Observer.Observer;

import java.util.Random;

public class Stock {
    private int tendency = 0;
    private String name;
    private double value;

    public Stock(String name, double value, Observer observer){
        this.name=name;
        this.value=value;
        observer.addStock(this);
    }
    public void changeValue(){
        Random rand = new Random();
        if(rand.nextInt(100)+1-50+tendency <0){
            //lower price
            decreaseValue(rand.nextInt(8));
            adjustTendency(false);
        }else{
            //increase price
            increaseValue(rand.nextInt(8));
            adjustTendency(true);
        }
    }

    private void increaseValue(double percent){
        value=value*(1+percent/100);
    }

    private void decreaseValue(double percent){
        value=value*(1-percent/100);
    }

    private void adjustTendency(boolean increase){
        if (increase){
            tendency++;
        }else{
            tendency--;
        }
        if(tendency==10){
            tendency=0-2;
        }
        if (tendency==-10){
            tendency=2;
        }
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
}
