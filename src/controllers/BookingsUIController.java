package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import classes.Cell;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import main.Runner;

public class BookingsUIController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuButton monthMenuButton;

    @FXML
    private Button updateTableButton;

    @FXML
    private MenuButton dateMenuButton;

    @FXML
    private Button submitBookingsButton;
    
    @FXML
    private TableView<Cell> tableView;
    
    private int currentMonth;
    private int currentDate;

    @FXML
    void updateTableButtonClicked(ActionEvent event) {
    	updateTableButton.setDisable(true);
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.MONTH, currentMonth);
    	calendar.set(Calendar.DATE, currentDate);
    	
    	boolean dateChanged = Runner.changeDate(calendar);
    	
    	if(dateChanged) {
    		//UPDATECELLS
    		
    		try {
        		//SHOW ALERT LOADING DIALOG FOR 2 SECs
    			Thread.sleep(2000);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	Runner.updateCellsWithAvailability();
        	
    		populateTable();
    	}
    	else {
    		//SHOW FAIL DIALOG
    	}
    	
    	updateTableButton.setDisable(false);
    }

    @FXML
    void submitBookingsClicked(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert monthMenuButton != null : "fx:id=\"monthMenuButton\" was not injected: check your FXML file 'BookingsUIFXML.fxml'.";
        assert updateTableButton != null : "fx:id=\"updateTableButton\" was not injected: check your FXML file 'BookingsUIFXML.fxml'.";
        assert dateMenuButton != null : "fx:id=\"dateMenuButton\" was not injected: check your FXML file 'BookingsUIFXML.fxml'.";
        assert submitBookingsButton != null : "fx:id=\"submitBookingsButton\" was not injected: check your FXML file 'BookingsUIFXML.fxml'.";
        
        Calendar cal = Calendar.getInstance();
        currentMonth = cal.get(Calendar.MONTH);
        currentDate = cal.get(Calendar.DATE);
        populateMenus();
        populateTable();
    }
    
    void populateMenus() {
    	String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    	for(int k = 0; k < months.length; k++) {
    		MenuItem item = new MenuItem(months[k]);
    		item.setId(k+"");
    		item.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					monthMenuButton.setText(item.getText());
					currentMonth = Integer.parseInt(item.getId());
					
				}
			});
    		monthMenuButton.getItems().add(item);
    	}
    	
    	for(int i = 1; i <= 31; i++) {
    		MenuItem item = new MenuItem(i + "");
    		item.setId(i + "");
    		item.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					dateMenuButton.setText(item.getText());
					currentDate = Integer.parseInt(item.getId());
				}
			});
    		dateMenuButton.getItems().add(item);
    	}
    	
    	
    }
    
    void initTable() {
    	TableColumn<Cell, String> col = new TableColumn<Cell, String>("hi");
    	col.setCellFactory(new Callback<TableColumn<Cell,String>, TableCell<Cell,String>>() {
			
			@Override
			public TableCell<Cell, String> call(TableColumn<Cell, String> param) {
				// TODO Auto-generated method stub
				TableCell<Cell, String> cell = new TableCell<Cell,String>();
				return cell;
			}
		});
    	tableView.getColumns().add(new TableColumn<Cell, String>(""));
    	for(String time: Runner.times) {
    		tableView.getColumns().add(new TableColumn<Cell, String>(time))
    	}
    	
    }
    
    void populateTable() {
    	
    	Runner.cells
    	Runner.displayCells();
    	
    }
}
