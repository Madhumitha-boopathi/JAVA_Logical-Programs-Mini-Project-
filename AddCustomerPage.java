package project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;

public class AddCustomerPage extends Application {

    @SuppressWarnings("unused")
    @Override
    public void start(Stage primaryStage) {
        // Labels and TextFields
        Label customerNameLabel = new Label("Customer Name:");
        TextField customerNameField = new TextField();
        
        Label contactNoLabel = new Label("Contact No:");
        TextField contactNoField = new TextField();
        
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        
        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField();
        
        Label loyaltyPointsLabel = new Label("Loyalty Points:");
        TextField loyaltyPointsField = new TextField();
        
        Label lastPurchaseDateLabel = new Label("Last Purchase Date:");
        DatePicker lastPurchaseDatePicker = new DatePicker(LocalDate.now());

        // Submit button
        Button submitButton = new Button("Add Customer");

        submitButton.setOnAction(e -> {
            String customerName = customerNameField.getText();
            String contactNo = contactNoField.getText();
            String email = emailField.getText();
            String address = addressField.getText();
            int loyaltyPoints = Integer.parseInt(loyaltyPointsField.getText());
            Date lastPurchaseDate = Date.valueOf(lastPurchaseDatePicker.getValue());

            // Create dbconnect object and insert the customer
            dbconnect db = new dbconnect();
            Connection conn = db.connect();
            db.addCustomer(conn, customerName, contactNo, email, address, loyaltyPoints, lastPurchaseDate);
            primaryStage.close();
        });

        // Layout setup
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        
        // Add components to grid
        grid.add(customerNameLabel, 0, 0);
        grid.add(customerNameField, 1, 0);
        grid.add(contactNoLabel, 0, 1);
        grid.add(contactNoField, 1, 1);
        grid.add(emailLabel, 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(addressLabel, 0, 3);
        grid.add(addressField, 1, 3);
        grid.add(loyaltyPointsLabel, 0, 4);
        grid.add(loyaltyPointsField, 1, 4);
        grid.add(lastPurchaseDateLabel, 0, 5);
        grid.add(lastPurchaseDatePicker, 1, 5);
        grid.add(submitButton, 1, 6);

        // Scene setup
        Scene scene = new Scene(grid, 400, 350);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add Customer");
        primaryStage.show();
    }
}
