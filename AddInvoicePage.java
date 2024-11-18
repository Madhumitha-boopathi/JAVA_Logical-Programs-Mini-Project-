package project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddInvoicePage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Labels and TextFields
        Label invoiceDateLabel = new Label("Invoice Date:");
        DatePicker invoiceDatePicker = new DatePicker(LocalDate.now());

        Label customerIdLabel = new Label("Customer ID:");
        TextField customerIdField = new TextField();

        Label totalAmountLabel = new Label("Total Amount:");
        TextField totalAmountField = new TextField();

        // Submit button
        Button submitButton = new Button("Create Invoice");

        submitButton.setOnAction(e -> {
            // Get form input values
            try {
                int customerId = Integer.parseInt(customerIdField.getText());
                float totalAmount = Float.parseFloat(totalAmountField.getText());
                Date invoiceDate = Date.valueOf(invoiceDatePicker.getValue());

                // Create dbconnect object and insert the invoice
                dbconnect db = new dbconnect();
                Connection conn = db.connect();

                // SQL query to insert the new invoice
                String sqlInsertInvoice = "INSERT INTO invoice (invoice_date, customer_id, bill_amount) VALUES (?, ?, ?)";
                PreparedStatement stmtInsertInvoice = conn.prepareStatement(sqlInsertInvoice);

                // Set parameters for the prepared statement
                stmtInsertInvoice.setDate(1, invoiceDate);  // Set invoice_date
                stmtInsertInvoice.setInt(2, customerId);    // Set customer_id
                stmtInsertInvoice.setFloat(3, totalAmount); // Set bill_amount

                // Execute the query to insert the invoice
                int rowsAffected = stmtInsertInvoice.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Invoice created successfully.");

                    // Update the last_purchase_date in the customer table
                    String sqlUpdateCustomer = "UPDATE customer SET last_purchase_date = ? WHERE customer_id = ?";
                    PreparedStatement stmtUpdateCustomer = conn.prepareStatement(sqlUpdateCustomer);

                    // Set parameters for the prepared statement
                    stmtUpdateCustomer.setDate(1, invoiceDate);  // Set the invoice date as the last purchase date
                    stmtUpdateCustomer.setInt(2, customerId);    // Set customer_id

                    // Execute the update query
                    int updateRowsAffected = stmtUpdateCustomer.executeUpdate();
                    if (updateRowsAffected > 0) {
                        System.out.println("Customer's last purchase date updated successfully.");
                        primaryStage.close();
                    } else {
                        System.out.println("Failed to update the customer's last purchase date.");
                    }

                    // Close the window after success
                    primaryStage.close();
                } else {
                    System.out.println("Failed to create invoice.");
                }

                // Close the connection
                conn.close();
            } catch (SQLException sqlEx) {
                System.out.println("Database error: " + sqlEx.getMessage());
                sqlEx.printStackTrace();
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // Layout setup
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Add components to grid
        grid.add(invoiceDateLabel, 0, 0);
        grid.add(invoiceDatePicker, 1, 0);

        grid.add(customerIdLabel, 0, 2);
        grid.add(customerIdField, 1, 2);

        grid.add(totalAmountLabel, 0, 5);
        grid.add(totalAmountField, 1, 5);
        grid.add(submitButton, 1, 6);

        // Scene setup
        Scene scene = new Scene(grid, 400, 350);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Create Invoice");
        primaryStage.show();
    }
}
