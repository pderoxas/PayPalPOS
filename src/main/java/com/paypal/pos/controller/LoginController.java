package com.paypal.pos.controller;

import com.paypal.pos.PayPalPos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.action.AbstractAction;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable, ManagedPane {
    PaneManager paneManager;
    @FXML private TextField username;
    @FXML private PasswordField password;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
    }

    @Override
    public void setParent(PaneManager paneManager) {
        this.paneManager = paneManager;
    }

    @FXML protected void login(ActionEvent event) {
        //Just bring send to HOME
        //TODO - add actual login logic
        paneManager.setPane(PayPalPos.MENU);

    }

    @FXML protected void cancelLogin(ActionEvent event) {
        //Just clear fields
        username.clear();
        password.clear();
    }

    @FXML protected void logout(ActionEvent event) {
        //Just bring send to LOGIN
        //TODO - add actual logout logic
        paneManager.setPane(PayPalPos.LOGIN);
    }




}
