package com.paypal.pos;

import com.paypal.pos.controller.PaneManager;
import com.paypal.pos.model.Transaction;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class PayPalPos extends Application {

    public static final String MENU = "menu";
    public static final String MENU_FXML = "/fxml/menu.fxml";

    public static final String LOGIN = "login";
    public static final String LOGIN_FXML = "/fxml/login.fxml";

    public static final String SALE = "sale";
    public static final String SALE_FXML = "/fxml/sale.fxml";

    public static boolean isStoreOpen = false;

    public static Map<String, Transaction> transactionMap;

    @Override
    public void start(Stage primaryStage) throws Exception{

        //will use transactionId as the key
        //TODO - can add logic to lookup
        transactionMap = new HashMap<String, Transaction>();

        PaneManager paneManager = new PaneManager();
        paneManager.loadPane(MENU, MENU_FXML);
        paneManager.loadPane(LOGIN, LOGIN_FXML);
        paneManager.loadPane(SALE, SALE_FXML);

        paneManager.setPane(LOGIN);
        Group root = new Group();
        root.getChildren().addAll(paneManager);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
