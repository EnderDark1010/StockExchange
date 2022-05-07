package com.company.Project.BonusAndUtillity;
/*==============================================================
Author: SafeScanner
Datum:  
ProjektName:    Program
Beschreibung: 
==============================================================*/

import java.util.Scanner;

public class SafeScanner {
    private Scanner scan = new Scanner(System.in);
    public int nextIntPositive(){
        int number;
        do {
            while (!scan.hasNextInt()) {
                System.out.println("That's not an integer!");
                scan.next();
            }
            number = scan.nextInt();
            scan.nextLine();
            if(number < 0){
                System.out.println("Number must at least be 0!");
            }
        } while (number < 0);

    return number;
    }
    public String nextString(){
        return scan.nextLine();
    }
}
