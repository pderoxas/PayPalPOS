package com.paypal.pos;

import com.paypal.pos.controller.PaneManager;
import com.paypal.pos.model.InStoreItem;
import com.paypal.pos.model.Item;
import com.paypal.pos.model.Location;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.*;

public class PayPalPos extends Application {

    public static final String MENU = "menu";
    public static final String MENU_FXML = "/fxml/menu.fxml";

    public static final String LOGIN = "login";
    public static final String LOGIN_FXML = "/fxml/login.fxml";

    public static final String SALE = "sale";
    public static final String SALE_FXML = "/fxml/sale.fxml";

    public static boolean isStoreOpen = false;


    public static String currentCashier;
    public static Location location;


    //This is temporary - will store resources in database
    public static SortedMap<String, Item> inventoryMap;

    @Override
    public void start(Stage primaryStage) throws Exception{

        initializeDataSource();

        location = new Location("REG-123", "Acme Auto Store", "ACME-AUTO-001", "123 Main Street", "Providence, RI 02903", "401-555-5555");

        //will use transactionId as the key
        //TODO - can add logic to lookup

        PaneManager paneManager = new PaneManager();

        paneManager.loadPane(LOGIN, LOGIN_FXML);

        //Load after login??
        //paneManager.loadPane(MENU, MENU_FXML);
        //paneManager.loadPane(SALE, SALE_FXML);

        paneManager.setPane(LOGIN);
        Group root = new Group();
        root.getChildren().addAll(paneManager);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("PayPal Demo POS");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    private void initializeDataSource() {
        List<Item> items = new ArrayList<Item>();
        items.add(new InStoreItem("RC11553", BigDecimal.valueOf(129.99), "Loaded Single Calipe/Brake Caliper Loaded Single - Front", 10, 10));
        items.add(new InStoreItem("31327", BigDecimal.valueOf(52.99), "Duralast/Brake Rotor - Front", 10, 10));
        items.add(new InStoreItem("10298", BigDecimal.valueOf(64.99), "Duralast Reman/CV Axle", 10, 10));
        items.add(new InStoreItem("15543", BigDecimal.valueOf(155.99), "Duralast Import/Alternator", 10, 10));
        items.add(new InStoreItem("34R-AGM", BigDecimal.valueOf(145.99), "Duralast Platinum/Battery", 10, 10));
        items.add(new InStoreItem("711-328", BigDecimal.valueOf(12.99), "Dorman/Custom Wheel Locks", 10, 10));
        items.add(new InStoreItem("24188401", BigDecimal.valueOf(137.99), "Bilstein/Shock/Strut - Front", 10, 10));
        items.add(new InStoreItem("5635", BigDecimal.valueOf(52.99), "Duralast/Power Steering Pump", 10, 10));
        items.add(new InStoreItem("21A", BigDecimal.valueOf(21.99), "Bosch Icon/Wiper Blade (Windshield)", 10, 10));
        items.add(new InStoreItem("2096", BigDecimal.valueOf(1976.99), "Edelbrock/460 HP Power Package top end kit includes intake, heads, roller camshaft, lifters, gasket set and bolt kits - Not legal for pollution controlled motor vehicles", 10, 10));

       this.inventoryMap = new TreeMap<String, Item>();
        for (Item item : items){
            inventoryMap.put(item.getId().toString(), item);
        }
    }
}
