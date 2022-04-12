package com.company;
/*==============================================================
Author: Simulation
Datum:  
ProjektName:    Program
Beschreibung: 
==============================================================*/

public class Simulation {
    private StockManager sm = new StockManager(5,2,5);
    private User user = new User(500);
    public void start(){
        while(true){
            printOptions();
            user.inputController(sm);

        }
    }

    public void printOptions(){
        System.out.println(
                "0: Wait\n"+
                "1: Buy\n"+
                "2: Sell\n"+
                "3: show owned stocks\n"+
                "4: show money available\n"+
                "5: show total value of stocks");
    }

}
