package com.paypal.pos;

import java.util.Random;

/**
 * Created by pderoxas on 2/25/14.
 */
public class Utils {

    public static int generateTransactionId() {
        int min=1000000, max=9999999;
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
