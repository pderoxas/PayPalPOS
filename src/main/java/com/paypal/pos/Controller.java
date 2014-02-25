package com.paypal.pos;


import com.paypal.pos.model.InStoreItem;
import com.paypal.pos.model.Item;
import com.paypal.pos.model.Transaction;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.apache.commons.lang.*;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;


public class Controller implements Initializable {

    private Transaction transaction = new Transaction();
    private static final double TAX_RATE = 7.0;

    @FXML private GridPane mainGridPane;

    @FXML private Pane loginPane;
    @FXML private TextField username;
    @FXML private PasswordField password;

    @FXML private TabPane tabPane;
    @FXML private Label timestampLabel;
    @FXML private Text cashierText;
    @FXML private Text salesAssociateText;
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
            displayNameList.add(getDisplayName(item,"add"));
        }

        ObservableList<String> itemsList = FXCollections.observableList(displayNameList);
        itemsListView.setItems(itemsList);

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
    }

    @FXML protected void cancelLogin(ActionEvent event) {
        username.setText("");
        password.setText("");
    }

    @FXML protected void cancelLoyalty(ActionEvent event) {
        username.setText("");
        password.setText("");
    }


    @FXML protected void logout(ActionEvent event) {
        tabPane.setVisible(false);
        loginPane.setVisible(true);
        password.setText("");
    }

    @FXML protected void addItem(ActionEvent event) {
        int selectedIndex = itemsListView.getSelectionModel().getSelectedIndex();
        Item selectedItem = items.get(selectedIndex);
        receiptField.appendText("\n" + getDisplayName(selectedItem,"add"));
        subtotal = subtotal + selectedItem.getUnitPrice().doubleValue();

        updateFields();
    }

    @FXML protected void removeItem(ActionEvent event) {
        int selectedIndex = itemsListView.getSelectionModel().getSelectedIndex();
        Item selectedItem = items.get(selectedIndex);
        receiptField.appendText("\n " + getDisplayName(selectedItem,"remove"));
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

    private String getDisplayName(Item item, String action){
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
