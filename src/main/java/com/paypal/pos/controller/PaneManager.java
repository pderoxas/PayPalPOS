package com.paypal.pos.controller;

import java.util.HashMap;
        import javafx.animation.KeyFrame;
        import javafx.animation.KeyValue;
        import javafx.animation.Timeline;
        import javafx.beans.property.DoubleProperty;
        import javafx.event.ActionEvent;
        import javafx.event.EventHandler;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.layout.StackPane;
        import javafx.util.Duration;

/**
 * This class will manage the panes in our application using a StackPane
 */
public class PaneManager extends StackPane {
    //Collection of the panes available
    private HashMap<String, Node> panes = new HashMap<String, Node>();

    public PaneManager() {
        super();
    }

    //Add pane to collection
    public void addPane(String name, Node screen) {
        panes.put(name, screen);
    }

    //Get pane by name
    public Node getPane(String name) {
        return panes.get(name);
    }

    //Load fxml file
    //add the pane to the collection
    //Injects the screenPane to the controller.
    public boolean loadPane(String name, String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) myLoader.load();
            ManagedPane myScreenControler = ((ManagedPane) myLoader.getController());
            myScreenControler.setParent(this);
            addPane(name, loadScreen);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //Set the current pane.  If one already exists, transition from old to new
    public boolean setPane(final String name) {
        if (panes.get(name) != null) {   //screen loaded
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) {    //if there is more than one screen
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(500), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent t) {
                                getChildren().remove(0);                    //remove the displayed screen
                                getChildren().add(0, panes.get(name));     //add the screen
                                Timeline fadeIn = new Timeline(
                                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                        new KeyFrame(new Duration(400), new KeyValue(opacity, 1.0)));
                                fadeIn.play();
                            }
                        }, new KeyValue(opacity, 0.0)));
                fade.play();

            } else {
                setOpacity(0.0);
                getChildren().add(panes.get(name));       //no one else been displayed, then just show
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(2500), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } else {
            System.out.println("screen hasn't been loaded!!! \n");
            return false;
        }
    }

    //Remove pane from the collection by name
    public boolean removePane(String name) {
        if (panes.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }
}
