package com.paypal.pos;

import com.paypal.pos.model.*;
import org.apache.commons.lang.StringUtils;

import java.util.Random;

/**
 * Created by pderoxas on 2/25/14.
 */
public class Utils {

    /**
     * Generates a "random" transaction id
     * @return int
     */
    public static int generateTransactionId() {
        int min=1000000, max=9999999;
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    /**
     * Returns a formatted string for display
     * @param item An Item Object
     * @param action The action; add/remove
     * @return
     */
    public static String getDisplayName(Item item, String action){
        StringBuilder sb = new StringBuilder();
        String id = item.getId().toString();
        String desc = item.getDescription();
        String unitPrice = item.getUnitPrice().toPlainString();

        if(desc.length()> 20) { desc = desc.substring(0,19); }


        if(action.equals("remove")){
            sb.append(StringUtils.leftPad(id, 9));
            sb.append(StringUtils.leftPad(desc, 30));
            sb.append(StringUtils.leftPad("(" + unitPrice + ")", 11));
            sb.append(StringUtils.leftPad(item.getCurrency().getCurrencyCode(), 5));
        } else {
            sb.append(StringUtils.leftPad(id, 10));
            sb.append(StringUtils.leftPad(desc, 30));
            sb.append(StringUtils.leftPad(unitPrice, 10));
            sb.append(StringUtils.leftPad(item.getCurrency().getCurrencyCode(), 6));
        }

        return sb.toString();
    }
}
