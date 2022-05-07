package com.company.Project.Game;
/*==============================================================
Author: Stock
Datum:  
ProjektName:    Stock
Beschreibung: 
==============================================================*/

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

    /**
     * Changes the value of the stock.
     *  The base chance for it to go up or down is 50%.
     *  BUT the stocks also have a tendency to go up or down!
     *  This is done to make it more equivalent to the real stock market system.
     *  This also means that the system is not 100% random.
     */
    public void changeValue() {
        Random rand = new Random();
        //1 to 100 -50 + tendency
        if (rand.nextInt(100) + 1 - 50 + tendency*2 < 0) {
            //lower price
            decreaseValue(rand.nextInt(5));
            adjustTendency(false);
        } else {
            //increase price
            increaseValue(rand.nextInt(5));
            adjustTendency(true);
        }
    }

    /**
     * Adjusts the tendency.
     * every 7th call, the tendency is reset.
     * the tendency is also reset once it reaches 5 or -5
     * a tendency of 2 will increase the chance of the value to go up by 4%
     * @param increase if the tendency should be increased or decreased
     */
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

    private void increaseValue(double percent) {
        value = value * (1 + percent / 100);
    }

    private void decreaseValue(double percent) {
        value = value * (1 - percent / 100);
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
}
