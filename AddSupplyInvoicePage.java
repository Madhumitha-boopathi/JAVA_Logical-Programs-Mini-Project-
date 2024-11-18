package project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Date;

public class AddSupplyInvoicePage extends Application {

    @SuppressWarnings("unused")
    @Override
    public void start(Stage primaryStage) {
        // UI Components
        Label itemIdLabel = new Label("Item ID:");
        TextField itemIdField = new TextField();
        
        Label supplierIdLabel = new Label("Supplier ID:");
        TextField supplierIdField = new TextField();

        Label supplyPriceLabel = new Label("Supply Price:");
        TextField supplyPriceField = new TextField();

        Label quantityLabel = new Label("Quantity:");
        TextField quantityField = new TextField();

        Label billDateLabel = new Label("Bill Date:");
        DatePicker billDatePicker = new DatePicker();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            int itemId = Integer.parseInt(itemIdField.getText());
            int supplierId = Integer.parseInt(supplierIdField.getText());
            double supplyPrice = Double.parseDouble(supplyPriceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            Date billDate = Date.valueOf(billDatePicker.getValue());

            dbconnect db = new dbconnect();
            Connection con = db.connect();
            db.addSupplyInvoice(con, itemId, supplierId, supplyPrice, quantity, billDate);
            primaryStage.close();
        });

        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(itemIdLabel, 0, 0);
        grid.add(itemIdField, 1, 0);
        grid.add(supplierIdLabel, 0, 1);
        grid.add(supplierIdField, 1, 1);
        grid.add(supplyPriceLabel, 0, 2);
        grid.add(supplyPriceField, 1, 2);
        grid.add(quantityLabel, 0, 3);
        grid.add(quantityField, 1, 3);
        grid.add(billDateLabel, 0, 4);
        grid.add(billDatePicker, 1, 4);
        grid.add(submitButton, 1, 5);

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add Supply Invoice");
        primaryStage.show();
    }
}

