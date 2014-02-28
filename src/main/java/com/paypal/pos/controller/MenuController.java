package com.paypal.pos.controller;


import com.paypal.pos.PayPalPos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.apache.log4j.Logger;
import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.action.AbstractAction;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;

import java.net.URL;
import java.util.ResourceBundle;


public class MenuController implements Initializable, ManagedPane {
    private Logger logger = Logger.getLogger(this.getClass());
    PaneManager paneManager;
    @FXML private Label storeStatusLabel;
    @FXML private Label currentCashierLabel;
    @FXML private Label storeNumberLabel;
    @FXML private Label registerNumber;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        logger.debug("initializing...");

        //TODO store these in a properties file
        storeNumberLabel.setText("Store: " + PayPalPos.location.getStoreNumber());
        registerNumber.setText("Location: " + PayPalPos.location.getRegisterNumber());
        currentCashierLabel.setText("Cashier: " + PayPalPos.currentCashier);

        //TODO
        if(PayPalPos.isStoreOpen){
            storeStatusLabel.setText("Location is currently: OPEN");
        } else {
            storeStatusLabel.setText("Location is currently: CLOSED");
        }

    }

    @Override
    public void setParent(PaneManager paneManager) {
        this.paneManager = paneManager;
    }


    @FXML protected void handleItemSale(MouseEvent event) {
        //Just bring send to HOME
        //TODO - add actual login logic
        paneManager.loadPane(PayPalPos.SALE, PayPalPos.SALE_FXML);
        paneManager.setPane(PayPalPos.SALE);
    }

    @FXML protected void handleItemReturn(MouseEvent event) {
        //Just bring send to HOME
        //TODO - add actual login logic
        paneManager.setPane(PayPalPos.SALE);

    }


    @FXML protected void handleOpenCloseStore(MouseEvent event) {
        showOpenCloseStoreDialog();
    }

    protected void showOpenCloseStoreDialog() {
        Dialog dialog = new Dialog(null, "Open/Close Location", true);

        final GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(10);
        if(PayPalPos.isStoreOpen){
            content.add(new Label("The store is OPEN. Would you like to CLOSE the store?"), 0, 0);
        } else {
            content.add(new Label("The store is CLOSED. Would you like to OPEN the store?"), 0, 0);
        }

        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(content);
        dialog.getActions().addAll(PayPalPos.isStoreOpen ? closeStore : openStore, Dialog.Actions.CANCEL);
        Action response = dialog.show();
        if (response == Dialog.Actions.CANCEL || response == Dialog.Actions.CLOSE) {
            //TODO
        } else {
            //TODO
        }
    }

    final Action openStore = new AbstractAction("Open Store") {
        {
            ButtonBar.setType(this, ButtonBar.ButtonType.OK_DONE);
        }

        // This method is called when the Save button is clicked...
        public void execute(ActionEvent ae) {
            Dialog dialog = (Dialog) ae.getSource();
            dialog.hide();
            //store is currently closed, so open it
            PayPalPos.isStoreOpen = true;
            paneManager.setPane(PayPalPos.MENU);
            storeStatusLabel.setText("Location is currently: OPEN");
        }
    };

    final Action closeStore = new AbstractAction("Close Store") {
        {
            ButtonBar.setType(this, ButtonBar.ButtonType.OK_DONE);
        }

        // This method is called when the Save button is clicked...
        public void execute(ActionEvent ae) {
            Dialog dialog = (Dialog) ae.getSource();
            dialog.hide();
            //store is currently open, so close it
            PayPalPos.isStoreOpen = false;
            paneManager.setPane(PayPalPos.MENU);
            storeStatusLabel.setText("Location is currently: CLOSED");
        }
    };
}
