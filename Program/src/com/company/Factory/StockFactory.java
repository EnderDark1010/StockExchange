package com.company.Factory;
/*==============================================================
Author: StockFactory
Datum:  
ProjektName:    Program
Beschreibung: 
==============================================================*/

import com.company.Observer.Observer;
import com.company.Stock;

import java.util.ArrayList;
import java.util.Random;

public class StockFactory {
    private Observer o;
    private Random random = new Random();
    private ArrayList<String> names = new ArrayList<String>();

    public StockFactory(Observer o) {
        this.o = o;
    }

    public Stock generateStock(int priceStart, int priceEnd) {
        return new Stock(generateName(), generatePrice(priceStart,priceEnd), o);
    }

    private String generateName() {
        int leftLimit = 97 - 32; // letter 'A' remove - 32 to make it 'a'
        int rightLimit = 122 - 32; // letter 'Z' remove -32 to make it 'z'
        int targetStringLength = 4;
        String generatedString = "";
        do {
            StringBuilder buffer = new StringBuilder(targetStringLength);
            for (int i = 0; i < targetStringLength; i++) {
                int randomLimitedInt = leftLimit + (int)
                        (random.nextFloat() * (rightLimit - leftLimit + 1));
                buffer.append((char) randomLimitedInt);
            }
            generatedString = buffer.toString();

        } while (names.contains(generatedString));
        names.add(generatedString);
        return generatedString;
    }

    private double generatePrice(int start,int end) {
        return random.nextInt(end-start)+start;
    }
}
