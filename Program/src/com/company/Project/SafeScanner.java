package com.company.Project;
/*==============================================================
Author: SafeScanner
Datum:  
ProjektName:    Program
Beschreibung: 
==============================================================*/

import java.util.Scanner;

public class SafeScanner {
    private Scanner scan = new Scanner(System.in);
    public int nextInt(){
        int number;
        do {
            while (!scan.hasNextInt()) {
                System.out.println("That's not a number!");
                scan.next();
            }
            number = scan.nextInt();
            scan.nextLine();
        } while (number < 0);

    return number;
    }
    public String nextString(){
        return scan.nextLine();
    }
}
