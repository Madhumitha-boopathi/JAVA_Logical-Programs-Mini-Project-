package project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;

public class AddSupplierPage extends Application {

    @SuppressWarnings("unused")
    @Override
    public void start(Stage primaryStage) {
        // Labels and TextFields
        Label supplierNameLabel = new Label("Supplier Name:");
        TextField supplierNameField = new TextField();
        
        Label contactNoLabel = new Label("Contact No:");
        TextField contactNoField = new TextField();
        
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        
        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField();

        // Submit button
        Button submitButton = new Button("Add Supplier");

        submitButton.setOnAction(e -> {
            String supplierName = supplierNameField.getText();
            String contactNo = contactNoField.getText();
            String email = emailField.getText();
            String address = addressField.getText();

            // Create dbconnect object and insert the supplier
            dbconnect db = new dbconnect();
            Connection conn = db.connect();
            db.addSupplier(conn, supplierName, contactNo, email, address);
            primaryStage.close();
        });

        // Layout setup
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        
        // Add components to grid
        grid.add(supplierNameLabel, 0, 0);
        grid.add(supplierNameField, 1, 0);
        grid.add(contactNoLabel, 0, 1);
        grid.add(contactNoField, 1, 1);
        grid.add(emailLabel, 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(addressLabel, 0, 3);
        grid.add(addressField, 1, 3);
        grid.add(submitButton, 1, 4);

        // Scene setup
        Scene scene = new Scene(grid, 400, 350);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add Supplier");
        primaryStage.show();
    }
}

