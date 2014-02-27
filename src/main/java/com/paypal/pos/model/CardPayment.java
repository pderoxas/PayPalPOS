package com.paypal.pos.model;

import java.math.BigDecimal;

/**
 * Created by pderoxas on 2/26/14.
 */
public class CardPayment extends Payment{
    private String cardNumber;
    private String cardType;

    public CardPayment(String cardNumber, String cardType) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
