package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;

public class RemainingChange extends Menu{


    public RemainingChange(InputStream input, OutputStream output) {
        super(input, output);
    }

    public void remainingChange(double change){
            int quarters = 0;
            int dimes = 0;
            int nickels = 0;
            while (change >= 0.25){
                quarters = quarters + 1;
                change = change - 0.25;
            }
            while (change >= 0.10){
                nickels = dimes + 1;
                change = change - 0.10;
            }
            while (change >= 0.05){
                nickels = nickels + 1;
                change = change - 0.05;
            }


            System.out.println("Your change: " + quarters +  " quarters "
                    + dimes + " dimes " + nickels + " nickels ");
        }
    }
