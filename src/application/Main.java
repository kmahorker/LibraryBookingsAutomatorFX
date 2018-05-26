package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import main.Runner;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	
	static BorderPane rootPane;
    public static Stage primaryStage;
    static AnchorPane bookingsPane;
    static GridPane addUserPane;

	@Override
	public void start(Stage primaryStage) {
		Runner.init();
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Library Bookings Automator");
		initRootLayout();
		
		showBookingsLayout();
	}
	
	public static void showBookingsLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("BookingsUIFXML.fxml"));
			bookingsPane = (AnchorPane) loader.load();

			rootPane.setCenter(bookingsPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 public static void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("RootLayout.fxml"));
			rootPane = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootPane);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	 
	 public static void showAddUserLayout() {
		 try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getClassLoader().getResource("AddUserLayout.fxml"));
				addUserPane = (GridPane) loader.load();

				rootPane.setCenter(addUserPane);
			} catch (IOException e) {
				e.printStackTrace();
			}
	 }
	 
	
	public static void main(String[] args) {
		launch(args);
	}
}
