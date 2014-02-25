package com.paypal.pos;


import com.paypal.pos.model.*;
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
import javafx.scene.control.*;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.action.AbstractAction;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;


public class Controller implements Initializable {

    private Transaction transaction = new Transaction();
    private static final double TAX_RATE = 7.0;
    private static boolean isStoreOpen = false;

    @FXML private GridPane mainGridPane;

    @FXML private Pane loginPane;
    @FXML private TextField username;
    @FXML private PasswordField password;

    @FXML private TabPane tabPane;
    @FXML private Label timestampLabel;
    @FXML private Text cashierText;
    @FXML private Text salesAssociateText;

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


    private List<Item> items = new ArrayList<Item>();
    private List<String> displayNameList = new ArrayList<String>();
    private double subtotal  = 0.0;

    final TextField payCodeTextField = new TextField();


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
            payCodeText.setText(payCodeTextField.getText());
            loyaltyNumberText.setText("274658294824");
            customerNameText.setText("Joseph Smith");

            loyaltyDialog.hide();
        }
    };

    /**
     * HOOK to call PayPal SDK getWallet method
     */
    final Action openStore = new AbstractAction("Open Store") {
        {
            ButtonBar.setType(this, ButtonBar.ButtonType.OK_DONE);
        }

        // This method is called when the Save button is clicked...
        public void execute(ActionEvent ae) {
            Dialog dialog = (Dialog) ae.getSource();

            //TODO: Call to SDK to get information!!
            isStoreOpen = true;

            dialog.hide();
        }
    };

    /**
     * HOOK to call PayPal SDK getWallet method
     */
    final Action closeStore = new AbstractAction("Close Store") {
        {
            ButtonBar.setType(this, ButtonBar.ButtonType.OK_DONE);
        }

        // This method is called when the Save button is clicked...
        public void execute(ActionEvent ae) {
            Dialog dialog = (Dialog) ae.getSource();

            //TODO: Call to SDK to get information!!
            isStoreOpen = false;

            dialog.hide();
        }
    };



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        items.add(new InStoreItem("A001", BigDecimal.valueOf(10.99), "A Pillar", 2, 0));
        items.add(new InStoreItem("A002", BigDecimal.valueOf(275.99), "A/C Compressor", 5, 0));
        items.add(new InStoreItem("A003", BigDecimal.valueOf(99.99), "A/C Compressor Clutch Only", 5, 0));
        items.add(new InStoreItem("A004", BigDecimal.valueOf(65.99), "A/C Condenser", 7, 0));
        items.add(new InStoreItem("A005", BigDecimal.valueOf(75.99), "A/C Condenser Fan", 3, 0));
        items.add(new InStoreItem("A006", BigDecimal.valueOf(150.99), "A/C Control Computer", 3, 0));
        items.add(new InStoreItem("A007", BigDecimal.valueOf(99.99), "A/C Evaporator", 100, 0));
        items.add(new InStoreItem("A008", BigDecimal.valueOf(50.99), "A/C Evaporator Housing only", 8, 0));
        items.add(new InStoreItem("A009", BigDecimal.valueOf(65.99), "A/C Heater Control (see also Radio or TV Screen)", 3, 0));
        items.add(new InStoreItem("A010", BigDecimal.valueOf(9.99), "A/C Hose", 10, 0));
        items.add(new InStoreItem("A011", BigDecimal.valueOf(25.99), "Accelerator Parts", 5, 0));
        items.add(new InStoreItem("A012", BigDecimal.valueOf(78.99), "Adaptive Cruise Projector", 5, 0));
        items.add(new InStoreItem("A013", BigDecimal.valueOf(599.99), "Air Bag", 2, 0));
        items.add(new InStoreItem("A014", BigDecimal.valueOf(67.99), "Air Bag Clockspring", 4, 0));
        items.add(new InStoreItem("A015", BigDecimal.valueOf(78.99), "Air Bag Ctrl Module", 7, 0));
        items.add(new InStoreItem("A016", BigDecimal.valueOf(4.99), "Air Box/Air Cleaner", 2, 0));
        items.add(new InStoreItem("A017", BigDecimal.valueOf(9.99), "Air Cond./Heater Vents", 12, 0));
        items.add(new InStoreItem("A018", BigDecimal.valueOf(23.99), "Air Flow Meter", 4, 0));
        items.add(new InStoreItem("A019", BigDecimal.valueOf(54.99), "Air Pump", 3, 0));
        items.add(new InStoreItem("A020", BigDecimal.valueOf(150.99), "Air Ride Compressor", 1, 0));
        items.add(new InStoreItem("A021", BigDecimal.valueOf(6.99), "Air Tube/Resonator", 3, 0));
        items.add(new InStoreItem("A022", BigDecimal.valueOf(109.99), "Alternator", 2, 0));
        items.add(new InStoreItem("A023", BigDecimal.valueOf(89.99), "Amplifier/Radio", 1, 0));
        items.add(new InStoreItem("A024", BigDecimal.valueOf(15.99), "Antenna", 7, 0));
        items.add(new InStoreItem("A025", BigDecimal.valueOf(160.99), "Anti-Lock Brake Computer", 2, 0));
        items.add(new InStoreItem("A026", BigDecimal.valueOf(89.99), "Anti-Lock Brake Pump", 5, 0));
        items.add(new InStoreItem("A027", BigDecimal.valueOf(79.99), "Armrest", 3, 0));
        items.add(new InStoreItem("A028", BigDecimal.valueOf(7.99), "Ash Tray/Lighter", 5, 0));
        items.add(new InStoreItem("A029", BigDecimal.valueOf(399.99), "Audiovisual (A/V) (see also TV Screen)", 1, 0));
        items.add(new InStoreItem("A030", BigDecimal.valueOf(89.99), "Automatic Headlight Dimmer", 1, 0));
        items.add(new InStoreItem("A031", BigDecimal.valueOf(599.99), "Auto. Trans. Cooler", 6, 0));


        for (Item item : items){
            displayNameList.add(Utils.getDisplayName(item,"add"));
        }

        ObservableList<String> itemsList = FXCollections.observableList(displayNameList);
        itemsListView.setItems(itemsList);

        //Hide the main pane
        tabPane.setVisible(false);

        //running timestamp
        bindToTime();

        //set tax label
        taxLabel.setText(TAX_RATE + "% Tax:");

        //set the transaction id
        transactionIdLabel.setText("Transaction ID: " + Utils.generateTransactionId());

        //create modals



    }

    @FXML protected void login(ActionEvent event) {
        tabPane.setVisible(true);
        loginPane.setVisible(false);

        //TODO: use names instead of username??
        cashierText.setText(username.getText());
        salesAssociateText.setText(username.getText());

        if(!isStoreOpen) {
            showOpenStoreDialog();
        }
    }

    @FXML protected void cancelLogin(ActionEvent event) {
        username.setText("");
        password.setText("");
    }

    @FXML protected void logout(ActionEvent event) {
        tabPane.setVisible(false);
        loginPane.setVisible(true);
        password.setText("");

        if(isStoreOpen) {
            showCloseStoreDialog();
        }
    }

    // This method is called when the user types into the text fields
    private void validateLoyaltyFields() {
        getWallet.disabledProperty().set(payCodeTextField.getText().trim().isEmpty());
    }

    // Imagine that this method is called somewhere in your codebase
    @FXML protected void addLoyalty(ActionEvent event) {
        Dialog loyaltyDialog = new Dialog(null, "Loyalty Lookup");

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
        loyaltyDialog.setResizable(false);
        loyaltyDialog.setIconifiable(false);
        loyaltyDialog.setContent(content);
        loyaltyDialog.getActions().addAll(getWallet, Dialog.Actions.CANCEL);
        validateLoyaltyFields();

        // request focus on the username field by default (so the user can
        // type immediately without having to click first)
        Platform.runLater(new Runnable() {
            public void run() {
                payCodeTextField.requestFocus();
            }
        });

        loyaltyDialog.show();
    }


    private void showOpenStoreDialog() {
        tabPane.setOpacity(0.5);
        Dialog dialog = new Dialog(null, "Open/Close Store");
        final GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(10);
        content.add(new Label("The store is CLOSED. Would you like to open the store?"), 0, 0);
        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(content);
        dialog.getActions().addAll(openStore, Dialog.Actions.CANCEL);
        validateLoyaltyFields();
        Action response = dialog.show();

        if (response == Dialog.Actions.CANCEL || response == Dialog.Actions.CLOSE) {
            loginPane.setVisible(true);
            tabPane.setVisible(false);
        } else {
            tabPane.setVisible(true);
            tabPane.setOpacity(1);
        }
    }

    private void showCloseStoreDialog() {
        Dialog dialog = new Dialog(null, "Open/Close Store");
        final GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(10);
        content.add(new Label("The store is OPEN. Would you like to close the store?"), 0, 0);
        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(content);
        dialog.getActions().addAll(closeStore, Dialog.Actions.CANCEL);
        validateLoyaltyFields();
        dialog.show();
    }






    @FXML protected void addItem(ActionEvent event) {
        int selectedIndex = itemsListView.getSelectionModel().getSelectedIndex();
        Item selectedItem = items.get(selectedIndex);
        receiptField.appendText("\n" + Utils.getDisplayName(selectedItem,"add"));
        subtotal = subtotal + selectedItem.getUnitPrice().doubleValue();

        updateFields();
    }

    @FXML protected void removeItem(ActionEvent event) {
        int selectedIndex = itemsListView.getSelectionModel().getSelectedIndex();
        Item selectedItem = items.get(selectedIndex);
        receiptField.appendText("\n " + Utils.getDisplayName(selectedItem,"remove"));
        subtotal = subtotal - selectedItem.getUnitPrice().doubleValue();

        updateFields();
    }

    private void updateFields(){
        BigDecimal decimalSubtotal = new BigDecimal(Double.toString(subtotal));
        decimalSubtotal = decimalSubtotal.setScale(2, RoundingMode.HALF_UP);
        double discountPercent = 0.2;
        BigDecimal decimalDiscountPercent = new BigDecimal(Double.toString(discountPercent));

        BigDecimal discountAmount = decimalSubtotal.multiply(decimalDiscountPercent);
        discountAmount = discountAmount.setScale(2, RoundingMode.HALF_UP);

        BigDecimal totalBeforeTax = decimalSubtotal.subtract(discountAmount);
        BigDecimal salesTaxPercent = new BigDecimal(Double.toString(TAX_RATE / 100));
        BigDecimal salesTax = salesTaxPercent.multiply(totalBeforeTax);
        salesTax = salesTax.setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = totalBeforeTax.add(salesTax);

        NumberFormat currency = NumberFormat.getCurrencyInstance();
        NumberFormat percent = NumberFormat.getPercentInstance();


        subTotalField.setText(currency.format(subtotal));
        discountPercentField.setText(percent.format(decimalDiscountPercent));
        discountAmountField.setText(currency.format(discountAmount));
        totalBeforeTaxField.setText(currency.format(totalBeforeTax));
        taxField.setText(currency.format(salesTax));
        totalField.setText(currency.format(total));
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
