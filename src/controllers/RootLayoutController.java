package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class RootLayoutController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem bookingsMenuItem;

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private Menu helpMenu;

    @FXML
    private MenuItem addUseMenuItem;

    @FXML
    private Menu fileMenu;

    @FXML
    void addUserClicked(ActionEvent event) {
    	
    	Main.showAddUserLayout();
    	
    }

    @FXML
    void bookingsClicked(ActionEvent event) {
    	Main.showBookingsLayout();
    }

    @FXML
    void closeClicked(ActionEvent event) {
    	
    	Main.primaryStage.close();

    }

    @FXML
    void aboutClicked(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert bookingsMenuItem != null : "fx:id=\"bookingsMenuItem\" was not injected: check your FXML file 'RootLayout.fxml'.";
        assert aboutMenuItem != null : "fx:id=\"aboutMenuItem\" was not injected: check your FXML file 'RootLayout.fxml'.";
        assert closeMenuItem != null : "fx:id=\"closeMenuItem\" was not injected: check your FXML file 'RootLayout.fxml'.";
        assert helpMenu != null : "fx:id=\"helpMenu\" was not injected: check your FXML file 'RootLayout.fxml'.";
        assert addUseMenuItem != null : "fx:id=\"addUseMenuItem\" was not injected: check your FXML file 'RootLayout.fxml'.";
        assert fileMenu != null : "fx:id=\"fileMenu\" was not injected: check your FXML file 'RootLayout.fxml'.";

    }
}
