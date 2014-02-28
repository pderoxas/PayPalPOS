package com.paypal.pos.service;

import com.paypal.pos.model.PayPalPayment;
import com.paypal.pos.model.PayPalWallet;
import com.paypal.pos.model.Transaction;
import org.apache.log4j.Logger;

import java.math.BigDecimal;

/**
 * Created by pderoxas on 2/26/14.
 * This class will server as an adapter to the PayPal SDK
 * May get rid of this once we have the sdk as part of the project
 */
public class PayPalSdkAdapter {
    private Logger logger = Logger.getLogger(this.getClass());
    public PayPalSdkAdapter() {
    }

    public PayPalWallet getWallet(String payCode){
        logger.info("Calling out to the SDK to get wallet information.");
        PayPalWallet wallet = new PayPalWallet();
        wallet.setCustomerName("Paolo de Roxas");
        wallet.setLoyaltyNumber("5746388294");
        wallet.setPaycode(payCode);
        return wallet;
    }

    public void openStore(String storeId){
        logger.info("Calling out to the SDK to OPEN store:" + storeId);
    }

    public void closeStore(String storeId){
        logger.info("Calling out to the SDK to CLOSE store:" + storeId);
    }

    public boolean makePayment(PayPalPayment payment){
        logger.info("Calling out to the SDK to make a payment");
        payment.setAuthorizationCode("AUTH-474747474");
        return true;
    }
}
