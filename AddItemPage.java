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

public class AddItemPage extends Application {

    @SuppressWarnings("unused")
    @Override
    public void start(Stage primaryStage) {
        // Labels and TextFields
        Label itemNameLabel = new Label("Item Name:");
        TextField itemNameField = new TextField();
        
        Label descriptionLabel = new Label("Description:");
        TextField descriptionField = new TextField();
        
        Label quantityLabel = new Label("Quantity:");
        TextField quantityField = new TextField();
        
        Label reorderLevelLabel = new Label("Reorder Level:");
        TextField reorderLevelField = new TextField();
        
        Label unitPriceLabel = new Label("Unit Price:");
        TextField unitPriceField = new TextField();
        
        Label purchaseDateLabel = new Label("Purchase Date:");
        DatePicker purchaseDatePicker = new DatePicker(LocalDate.now());

        // Submit button
        Button submitButton = new Button("Add Item");

        submitButton.setOnAction(e -> {
            String itemName = itemNameField.getText();
            String description = descriptionField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            int reorderLevel = Integer.parseInt(reorderLevelField.getText());
            float unitPrice = Float.parseFloat(unitPriceField.getText());
            Date purchaseDate = Date.valueOf(purchaseDatePicker.getValue());

            // Create dbconnect object and insert the item
            dbconnect db = new dbconnect();
            Connection conn = db.connect();
            db.addNewItem(conn, itemName, description, quantity, reorderLevel, unitPrice, purchaseDate);
            primaryStage.close();
        });


        // Layout setup
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        
        // Add components to grid
        grid.add(itemNameLabel, 0, 0);
        grid.add(itemNameField, 1, 0);
        grid.add(descriptionLabel, 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(quantityLabel, 0, 2);
        grid.add(quantityField, 1, 2);
        grid.add(reorderLevelLabel, 0, 3);
        grid.add(reorderLevelField, 1, 3);
        grid.add(unitPriceLabel, 0, 4);
        grid.add(unitPriceField, 1, 4);
        grid.add(purchaseDateLabel, 0, 5);
        grid.add(purchaseDatePicker, 1, 5);
        grid.add(submitButton, 1, 6);

        // Scene setup
        Scene scene = new Scene(grid, 400, 350);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add Item");
        primaryStage.show();
    }
}

