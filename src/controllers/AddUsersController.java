package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import classes.AES;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.Runner;

public class AddUsersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button addButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    void addButtonClicked(ActionEvent event) {
    	addButton.setDisable(true);
    	boolean success = false;
    	try {
			success = Runner.addUserToFirebase(usernameTextField.getText(), AES.encrypt(passwordTextField.getText()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			success = false;
		}
    	if(success) {
    		//SHOW SUCCESS ALERT
    		
    	}else {
    		//SHOW FAIL ALERT
    	}
    	
    	usernameTextField.setText("");
    	passwordTextField.setText("");
    	addButton.setDisable(false);
    }

    @FXML
    void initialize() {
        assert passwordTextField != null : "fx:id=\"passwordTextField\" was not injected: check your FXML file 'AddUserLayout.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'AddUserLayout.fxml'.";
        assert usernameTextField != null : "fx:id=\"usernameTextField\" was not injected: check your FXML file 'AddUserLayout.fxml'.";

    }
}
