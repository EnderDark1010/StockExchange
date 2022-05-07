package com.company.Project;
/*==============================================================
Author: Simulation
Datum:  
ProjektName:    Program
Beschreibung: 
==============================================================*/

import com.company.Project.BonusAndUtillity.Draw;
import com.company.Project.Game.StockManager;
import com.company.Project.Game.User;

public class Simulation {
   private StockManager sm = StockManager.getInstance();

    private User user = new User(500);
    public void start() {
        boolean cont = true;
        while (cont) {
            printOptions();
            cont = user.inputController(sm);
        }
        System.out.println("Please wait a few seconds!\n" +
                "The program will now generate the stock graphs!");
        Draw.draw(sm);
        System.out.println("finished");
    }

    public void printOptions() {
        System.out.println(
                "\n-------------\n" +
                        "0: Wait\n" +
                        "1: Buy\n" +
                        "2: Sell\n" +
                        "3: show owned stocks\n" +
                        "4: show money available\n" +
                        "5: show total value of stocks\n" +
                        "6: show stocks that I can buy\n" +
                        "7: Show all stocks \n" +
                        "10: Quit program\n");
    }

}
