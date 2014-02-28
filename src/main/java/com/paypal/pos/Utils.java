package com.paypal.pos;

import com.paypal.pos.model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.util.Duration;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
     * Helper function to populate list of items.  Will replace with bar code scanner
     * @return
     */
    public static ObservableList<String> getItemSkuList(){
        Collection<String> items = PayPalPos.inventoryMap.keySet();
        return FXCollections.observableList(new ArrayList(items));
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

    public static String getReceiptHeader(String dateTime, String transactionId, String cashierName){
        StringBuilder sb = new StringBuilder();
        sb.append(getCenteredString("Acme Auto Parts\n", 29));
        sb.append(getCenteredString("Location Number: 12345678\n", 29));
        sb.append(getCenteredString("Providence, RI 02903\n", 29));
        sb.append(getCenteredString("Phone: 401-555-5555\n", 29));
        sb.append(getJustifiedString(dateTime, transactionId, 57));
        sb.append(getJustifiedString("Cashier: " + cashierName, "Location Number: 123", 57));
        sb.append("*********************************************************");
        return sb.toString();
    }

    public static String getReceiptFooter(String subTotal, String discountPercent, String discountAmount, String tax, String total){
        StringBuilder sb = new StringBuilder();
        sb.append("\n*********************************************************\n");
        sb.append(getJustifiedString("                         SubTotal", subTotal + " ", 57));
        sb.append(getJustifiedString("                         " + discountPercent + " Discount", "(" + discountAmount + ")", 57));
        sb.append(getJustifiedString("                         Tax", tax + " ", 57));
        sb.append(getJustifiedString("                         Total", total + " ", 57));

        return sb.toString();
    }

    public static void bindToTime(Labeled label) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new EventHandler<ActionEvent>() {
                            @Override public void handle(ActionEvent actionEvent) {
                                Calendar time = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                                label.setText(sdf.format(time.getTime()));
                            }
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private static String getCenteredString(String text, int midPoint){
        int len = text.length();
        return StringUtils.leftPad(text, midPoint + len/2);
    }

    private static String getJustifiedString(String leftText, String rightText, int totalLength){
        int leftLen = leftText.length();
        return leftText + StringUtils.leftPad(rightText, totalLength - leftLen) +"\n";
    }
}
