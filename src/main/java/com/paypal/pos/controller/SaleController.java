package com.paypal.pos.controller;


import com.paypal.pos.PayPalPos;
import com.paypal.pos.Utils;
import com.paypal.pos.dal.InStoreItemDAO;
import com.paypal.pos.exception.DalException;
import com.paypal.pos.model.*;
import com.paypal.pos.service.PayPalSdkAdapter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.action.AbstractAction;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;


public class SaleController implements Initializable, ManagedPane {
    private Logger logger = Logger.getLogger(this.getClass());
    PaneManager paneManager;
    InStoreItemDAO inStoreItemDAO = new InStoreItemDAO();
    PayPalWallet wallet = new PayPalWallet();
    ObjectMapper jsonMapper = new ObjectMapper();

    private PayPalSdkAdapter payPalSdkAdapter = new PayPalSdkAdapter();

    private static final double TAX_RATE = 7.0;
    public static Transaction currentTransaction;

    @FXML private Label timestampLabel;
    @FXML private Text cashierText;
    @FXML private Text registerText;

    //LOYALTY DIALOG
    @FXML private Text loyaltyNumberText;
    @FXML private Text customerNameText;
    @FXML private Text payCodeText;


    @FXML private Label taxLabel;
    @FXML private Label transactionIdLabel;
    @FXML private TextArea receiptField;
    @FXML private ListView<String> itemsListView;
    @FXML private TextField discountPercentField;
    @FXML private TextField discountAmountField;
    @FXML private TextField totalBeforeTaxField;
    @FXML private TextField taxField;
    @FXML private TextField subTotalField;
    @FXML private TextField totalField;

    //item profile
    @FXML private ImageView itemImage;
    @FXML private Text skuText;
    @FXML private Text unitPriceText;
    @FXML private Text descriptionText;

    private double subtotal  = 0.0;
    BigDecimal decimalSubtotal;
    BigDecimal decimalDiscountPercent;
    BigDecimal decimalDiscountAmount;
    BigDecimal decimalTotalBeforeTax;
    BigDecimal decimalSalesTaxPercent;
    BigDecimal decimalSalesTax;
    BigDecimal decimalTotal;

    private double total = 0.0;
    private double tax = 0.0;
    private double discount = 0.0;
    private double discountPercent = 0.0;

    final TextField payCodeTextField = new TextField();
    final Button cashButton = new Button("Pay");
    final Button debitCreditButton = new Button("Pay");
    final Button paypalButton = new Button("Pay");


    @FXML protected void logout(MouseEvent event) {
        //Just bring send to LOGIN
        //TODO - add actual logout logic
        paneManager.setPane(PayPalPos.LOGIN);
    }

    /**
     * HOOK to call PayPal SDK getWallet method
     */
    final Action getWallet = new AbstractAction("Lookup") {
        {
            ButtonBar.setType(this, ButtonBar.ButtonType.OK_DONE);
        }

        // This method is called when the Save button is clicked...
        public void execute(ActionEvent ae) {
            Dialog loyaltyDialog = (Dialog) ae.getSource();

            //TODO: Call to SDK to get information!!
            wallet = payPalSdkAdapter.getWallet(payCodeTextField.getText());

            payCodeText.setText(wallet.getPaycode());
            loyaltyNumberText.setText(wallet.getLoyaltyNumber());
            customerNameText.setText(wallet.getCustomerName());

            loyaltyDialog.hide();
        }
    };

    /**
     * HOOK to call PayPal SDK openStore method
     */
    final Action openStore = new AbstractAction("Open Location") {
        {
            ButtonBar.setType(this, ButtonBar.ButtonType.OK_DONE);
        }

        // This method is called when the Save button is clicked...
        public void execute(ActionEvent ae) {
            Dialog dialog = (Dialog) ae.getSource();
            dialog.hide();
            PayPalPos.isStoreOpen = true;
            paneManager.setPane(PayPalPos.LOGIN);
        }
    };

    /**
     * HOOK to call PayPal SDK closeStore method
     */
    final Action closeStore = new AbstractAction("Close Store") {
        {
            ButtonBar.setType(this, ButtonBar.ButtonType.OK_DONE);
        }

        // This method is called when the Save button is clicked...
        public void execute(ActionEvent ae) {
            Dialog dialog = (Dialog) ae.getSource();
            dialog.hide();
            PayPalPos.isStoreOpen = false;
            paneManager.setPane(PayPalPos.MENU);
        }
    };

    @FXML protected void openStore(MouseEvent event) {
        showCloseStoreDialog();
    }

    @FXML protected void closeStore(MouseEvent event) {
        showCloseStoreDialog();
    }

    protected void showOpenStoreDialog() {
        Dialog dialog = new Dialog(null, "Open/Close Store", true);

        final GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(10);
        content.add(new Label("The store is CLOSED. Would you like to OPEN the store?"), 0, 0);
        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(content);
        dialog.getActions().addAll(openStore, Dialog.Actions.CANCEL);
        Action response = dialog.show();
        if (response == Dialog.Actions.CANCEL || response == Dialog.Actions.CLOSE) {
            //TODO
        } else {
            //TODO
        }
    }


    protected void showCloseStoreDialog() {
        Dialog dialog = new Dialog(null, "Close Store", true);
        final GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(10);
        content.add(new Label("The store is OPEN. Would you like to CLOSE the store?"), 0, 0);
        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(content);
        dialog.getActions().addAll(closeStore, Dialog.Actions.CANCEL);
        Action response = dialog.show();
        if (response == Dialog.Actions.CANCEL || response == Dialog.Actions.CLOSE) {
            //TODO
        } else {
            //TODO
        }
    }



    /**
     * HOOK to call PayPal SDK makePayment method
     */
    protected void makePayment() {


    }

    @Override
    public void setParent(PaneManager paneManager) {
        this.paneManager = paneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        final ListView<String> itemListView = new ListView<String>();
        itemsListView.setItems(Utils.getItemSkuList());

        registerText.setText(PayPalPos.location.getRegisterNumber());
        cashierText.setText(PayPalPos.currentCashier);


        //running timestamp
        bindToTime();

        //set tax label
        taxLabel.setText(TAX_RATE + "% Tax:");

        //set the transaction id
        int transactionId =  Utils.generateTransactionId();
        transactionIdLabel.setText("Transaction ID: " + transactionId);

        Calendar time = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");

        //create header
        receiptField.appendText(Utils.getReceiptHeader(sdf.format(time.getTime()), transactionIdLabel.getText(), cashierText.getText()));

        //instantiate a new transaction
        currentTransaction = new Transaction(String.valueOf(transactionId), PayPalPos.currentCashier, TransactionType.SALE);

        currentTransaction.setLocation(PayPalPos.location);
        currentTransaction.setTransactionDate(time.getTime());

    }

    // This method is called when the user types into the text fields
    private void validateLoyaltyFields() {
        getWallet.disabledProperty().set(payCodeTextField.getText().trim().isEmpty());
    }


    @FXML protected void addLoyalty(ActionEvent event) {
        Dialog dialog = new Dialog(null, "Loyalty Lookup", true);

        // listen to user input on dialog (to enable / disable the button)
        ChangeListener<String> changeListener = new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                validateLoyaltyFields();
            }
        };
        payCodeTextField.textProperty().addListener(changeListener);
        //txPassword.textProperty().addListener(changeListener);

        // layout a custom GridPane containing the input fields and labels
        final GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(10);

        content.add(new Label("PayPal Pay Code"), 0, 0);
        content.add(payCodeTextField, 1, 0);
        GridPane.setHgrow(payCodeTextField, Priority.ALWAYS);

        //content.add(new Label("Password"), 0, 1);
        //content.add(txPassword, 1, 1);
        //GridPane.setHgrow(txPassword, Priority.ALWAYS);

        // create the dialog with a custom graphic and the gridpane above as the
        // main content region
        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(content);
        dialog.getActions().addAll(getWallet, Dialog.Actions.CANCEL);
        validateLoyaltyFields();

        // request focus on the username field by default (so the user can
        // type immediately without having to click first)
        Platform.runLater(new Runnable() {
            public void run() {
                payCodeTextField.requestFocus();
            }
        });

        dialog.show();
    }





    @FXML protected void checkout(ActionEvent event) {
        Dialog dialog = new Dialog(null, "Check Out", true);

        // listen to user input on dialog (to enable / disable the button)
        ChangeListener<String> changeListener = new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                validateLoyaltyFields();
            }
        };
        payCodeTextField.textProperty().addListener(changeListener);
        //txPassword.textProperty().addListener(changeListener);

        // layout a custom GridPane containing the input fields and labels
        final GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(10);

        //CASH
        TitledPane cashTitlePane = new TitledPane();
        GridPane cashGrid = new GridPane();
        cashGrid.setVgap(4);
        cashGrid.setPadding(new Insets(5, 5, 5, 5));
        cashGrid.add(new Label("Amount Tendered: "), 0, 0);
        cashGrid.add(new TextField(), 1, 0);
        cashGrid.add(new Label("Change Due: "), 0, 1);
        cashGrid.add(new TextField(), 1, 1);
        cashGrid.add(new Button("Make Payment"), 1, 2);
        cashTitlePane.setText("Cash");
        cashTitlePane.setContent(cashGrid);

        //CREDIT
        TitledPane debitCreditTitlePane = new TitledPane();
        GridPane debitCreditGrid = new GridPane();
        debitCreditGrid.setVgap(4);
        debitCreditGrid.setPadding(new Insets(5, 5, 5, 5));

        ListView<String> cardTypeList = new ListView<String>();
        ObservableList<String> cardTypes =FXCollections.observableArrayList (
                "American Express", "Diners Club", "MasterCard", "Visa");
        cardTypeList.setItems(cardTypes);

        debitCreditGrid.add(new Label("Credit Card Type: "), 0, 0);
        final ToggleGroup group = new ToggleGroup();
        RadioButton mastercard = new RadioButton("MasterCard");
        mastercard.setToggleGroup(group);
        RadioButton visa = new RadioButton("Visa");
        visa.setToggleGroup(group);
        debitCreditGrid.add(mastercard, 1, 0);
        debitCreditGrid.add(visa, 1, 1);

        debitCreditGrid.add(new Label("Credit Card Number: "), 0, 2);
        debitCreditGrid.add(new TextField(), 1, 2);

        debitCreditGrid.add(new Button("Make Payment"), 1, 3);
        debitCreditTitlePane.setText("Credit");
        debitCreditTitlePane.setContent(debitCreditGrid);

        //PAYPAL
        TitledPane paypalTitlePane = new TitledPane();
        GridPane paypalGrid = new GridPane();
        paypalGrid.setVgap(20);
        paypalGrid.setPadding(new Insets(5, 5, 5, 5));

        paypalGrid.add(new Label("PayPal Customer: "), 0, 0);
        paypalGrid.add(new Text(customerNameText.getText()), 1, 0);

        paypalGrid.add(new Label("PayPal Pay Code: "), 0, 1);
        paypalGrid.add(new TextField(payCodeText.getText()), 1, 1);


        Label authLabel = new Label("Authorization Code: ");
        authLabel.setVisible(false);
        Text authCode = new Text();
        authCode.setVisible(false);
        paypalGrid.add(new Label("Authorization Code: "), 0, 2);
        paypalGrid.add(authCode, 1, 2);



        PayPalPayment payment = new PayPalPayment(wallet);
        payment.setSubTotalAmount(decimalSubtotal);
        payment.setDiscountAmount(decimalDiscountAmount);
        payment.setDiscountPercentage(decimalDiscountPercent.doubleValue());
        payment.setTaxAmount(decimalSalesTax);
        payment.setTotalAmountDue(decimalTotal);
        payment.setTotalAmountPaid(decimalTotal);
        payment.setCurrency(Currency.getInstance(Locale.US));

        Button finishTransaction = new Button("Complete Transaction");
        finishTransaction.setVisible(false);
        finishTransaction.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                receiptField.appendText(Utils.getReceiptFooter(subTotalField.getText(), discountPercentField.getText(), discountAmountField.getText(), taxField.getText(), totalField.getText()));
                dialog.hide();
                try {
                    jsonMapper.writeValue(new File("data/" + currentTransaction.getId() + ".json"), currentTransaction);
                } catch (IOException ex) {
                    logger.error(ex.getMessage());

                }
            }
        });

        //THIS IS WHERE WE WOULD MAKE A CALL OUT TO SDK
        Button paypalMakePayment = new Button("Make Payment");
        paypalMakePayment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                paypalMakePayment.setVisible(false);
                currentTransaction.setPayment(payment);
                payment.setAuthorizationCode("AUTH-2342342342");
                authCode.setText(payment.getAuthorizationCode());

                finishTransaction.setVisible(true);
                authLabel.setVisible(true);
                authCode.setVisible(true);
                //receiptField.appendText(Utils.getReceiptFooter(subTotalField.getText(), discountPercentField.getText(), discountAmountField.getText(), taxField.getText(), totalField.getText()));

            }
        });


        paypalGrid.add(paypalMakePayment, 1, 2);

        paypalGrid.add(finishTransaction, 1, 3);

        paypalTitlePane.setText("PayPal Wallet");
        paypalTitlePane.setContent(paypalGrid);


        final TitledPane[] tps = new TitledPane[3];
        final Accordion accordion = new Accordion ();
        tps[0] = cashTitlePane;
        tps[1] = debitCreditTitlePane;
        tps[2] = paypalTitlePane;
        accordion.getPanes().addAll(tps);
        accordion.setExpandedPane(tps[2]);

        Text totalDue = new Text("TOTAL AMOUNT DUE: " + totalField.getText());
        totalDue.setFont(Font.font("Verdana", 20));
        totalDue.setFill(Color.RED);
        content.add(totalDue, 0, 0);
        content.add(accordion, 0, 1);

        dialog.setResizable(true);
        dialog.setIconifiable(false);
        dialog.setContent(content);
        dialog.getActions().addAll(Dialog.Actions.CANCEL);
        validateLoyaltyFields();

        // request focus on the username field by default (so the user can
        // type immediately without having to click first)
        Platform.runLater(new Runnable() {
            public void run() {
                payCodeTextField.requestFocus();
            }
        });

        dialog.show();
    }




    @FXML protected void setItemProfile(MouseEvent event) {
        try{
            String itemSku = itemsListView.getSelectionModel().getSelectedItem();
            Item selectedItem = inStoreItemDAO.getById(itemSku);
            itemImage.setImage(new Image("/images/" + itemSku + ".jpg"));

            skuText.setText(selectedItem.getId().toString());
            unitPriceText.setText(selectedItem.getUnitPrice().toString());
            descriptionText.setText(selectedItem.getDescription().toString());

        } catch (DalException e){
            logger.error(e.getMessage());
        }
    }


    @FXML protected void addItem(ActionEvent event) {
        //int selectedIndex = itemsListView.getSelectionModel().getSelectedIndex();
        //Item selectedItem = (Item) PayPalPos.inventoryMap.values().toArray()[selectedIndex];
        try {
            String itemSku = itemsListView.getSelectionModel().getSelectedItem();
            Item selectedItem = inStoreItemDAO.getById(itemSku);
            receiptField.appendText("\n" + Utils.getDisplayName(selectedItem,"add"));
            subtotal = subtotal + selectedItem.getUnitPrice().doubleValue();

            currentTransaction.getItems().add(selectedItem);

            updateFields();
        } catch (DalException e){
            logger.error(e.getMessage());
        }
    }

    @FXML protected void removeItem(ActionEvent event) {
        //int selectedIndex = itemsListView.getSelectionModel().getSelectedIndex();
        //Item selectedItem = (Item) PayPalPos.inventoryMap.values().toArray()[selectedIndex];

        try {
            String itemSku = itemsListView.getSelectionModel().getSelectedItem();
            Item selectedItem = inStoreItemDAO.getById(itemSku);
            receiptField.appendText("\n " + Utils.getDisplayName(selectedItem,"remove"));
            subtotal = subtotal - selectedItem.getUnitPrice().doubleValue();

            currentTransaction.getItems().remove(selectedItem);

            updateFields();
        } catch (DalException e){
            logger.error(e.getMessage());
        }


    }

    private void updateFields(){
        decimalSubtotal = new BigDecimal(Double.toString(subtotal));
        decimalSubtotal = decimalSubtotal.setScale(2, RoundingMode.HALF_UP);
        double discountPercent = 0.2;
        decimalDiscountPercent = new BigDecimal(Double.toString(discountPercent));

        decimalDiscountAmount = decimalSubtotal.multiply(decimalDiscountPercent);
        decimalDiscountAmount = decimalDiscountAmount.setScale(2, RoundingMode.HALF_UP);

        decimalTotalBeforeTax = decimalSubtotal.subtract(decimalDiscountAmount);
        decimalSalesTaxPercent = new BigDecimal(Double.toString(TAX_RATE / 100));
        decimalSalesTax = decimalSalesTaxPercent.multiply(decimalTotalBeforeTax);
        decimalSalesTax = decimalSalesTax.setScale(2, RoundingMode.HALF_UP);
        decimalTotal = decimalTotalBeforeTax.add(decimalSalesTax);

        NumberFormat currency = NumberFormat.getCurrencyInstance();
        NumberFormat percent = NumberFormat.getPercentInstance();

        subTotalField.setText(currency.format(subtotal));
        discountPercentField.setText(percent.format(decimalDiscountPercent));
        discountAmountField.setText(currency.format(decimalDiscountAmount));
        totalBeforeTaxField.setText(currency.format(decimalTotalBeforeTax));
        taxField.setText(currency.format(decimalSalesTax));
        totalField.setText(currency.format(decimalTotal));
    }



    private void bindToTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new EventHandler<ActionEvent>() {
                            @Override public void handle(ActionEvent actionEvent) {
                                Calendar time = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                                timestampLabel.setText(sdf.format(time.getTime()));
                            }
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

}
