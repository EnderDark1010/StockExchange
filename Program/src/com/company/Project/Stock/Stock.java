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
    private int changesSinceLastTendencyReset = 0;

    public Stock(String name, double value, Observer observer) {
        this.name = name;
        this.value = value;
        observer.addStock(this);
    }

    public void changeValue() {
        Random rand = new Random();
        //1 to 100 -50 + tendency
        if (rand.nextInt(100) + 1 - 50 + tendency < 0) {
            //lower price
            decreaseValue(rand.nextInt(5));
            adjustTendency(false);
        } else {
            //increase price
            increaseValue(rand.nextInt(5));
            adjustTendency(true);
        }
    }

    private void increaseValue(double percent) {
        value = value * (1 + percent / 100);
    }

    private void decreaseValue(double percent) {
        value = value * (1 - percent / 100);
    }

    private void adjustTendency(boolean increase) {
        changesSinceLastTendencyReset++;
        if (changesSinceLastTendencyReset == 7) {
            tendency = 0;
        } else {
            switch (tendency) {
                case 5:
                case -5:
                    tendency = 0;
                    break;
                default:
                    if (increase) {
                        tendency+=1;
                    } else {
                        tendency-=1;
                    }
            }
        }


    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
}
