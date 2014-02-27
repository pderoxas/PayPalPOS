package com.paypal.pos.service;

import com.paypal.pos.model.PayPalPayment;
import com.paypal.pos.model.PayPalWallet;
import com.paypal.pos.model.Transaction;

import java.math.BigDecimal;

/**
 * Created by pderoxas on 2/26/14.
 */
public class PayPalSdkAdapter {
    public PayPalSdkAdapter() {
    }

    public PayPalWallet getWallet(String payCode){
        return new PayPalWallet();
    }

    public void openStore(String storeId){

    }

    public void closeStore(String storeId){

    }

    public PayPalPayment makePayment(Transaction transaction, PayPalWallet payPalWallet){
        return new PayPalPayment(payPalWallet);
    }
}
