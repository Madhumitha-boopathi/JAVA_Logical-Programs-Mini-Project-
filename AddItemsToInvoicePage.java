package project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddItemsToInvoicePage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // UI Components
        Label itemIdLabel = new Label("Item ID:");
        TextField itemIdField = new TextField();

        Label invoiceIdLabel = new Label("Invoice ID:");
        TextField invoiceIdField = new TextField();

        Label quantityLabel = new Label("Quantity:");
        TextField quantityField = new TextField();

        Label unitPriceLabel = new Label("Unit Price:");
        TextField unitPriceField = new TextField();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            // Retrieve input values
            int itemId = Integer.parseInt(itemIdField.getText());
            int invoiceId = Integer.parseInt(invoiceIdField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            double unitPrice = Double.parseDouble(unitPriceField.getText());
            double totalAmount = unitPrice * quantity;

            // Create DB connection and execute the necessary queries
            dbconnect db = new dbconnect();
            try (Connection con = db.connect()) {
                // 1. Check if enough quantity is available in the items table
                if (checkItemAvailability(con, itemId, quantity)) {
                    // 2. Add item to the invoice table
                    db.addItemsToInvoice(con, itemId, invoiceId, quantity, unitPrice, totalAmount);

                    // 3. Reduce the item quantity in the items table
                    reduceItemQuantity(con, itemId, quantity);

                    // Close the stage after successful operation
                    primaryStage.close();
                } else {
                    // Show an alert or message if not enough items are available
                    System.out.println("Not enough items available.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(itemIdLabel, 0, 0);
        grid.add(itemIdField, 1, 0);
        grid.add(invoiceIdLabel, 0, 1);
        grid.add(invoiceIdField, 1, 1);
        grid.add(quantityLabel, 0, 2);
        grid.add(quantityField, 1, 2);
        grid.add(unitPriceLabel, 0, 3);
        grid.add(unitPriceField, 1, 3);
        grid.add(submitButton, 1, 4);

        Scene scene = new Scene(grid, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add Items to Invoice");
        primaryStage.show();
    }

    /**
     * Checks if the requested quantity is available in the items table.
     * @param con The database connection.
     * @param itemId The ID of the item.
     * @param quantity The quantity to check.
     * @return true if the quantity is available, false otherwise.
     */
    private boolean checkItemAvailability(Connection con, int itemId, int quantity) throws SQLException {
        String query = "SELECT quantity FROM items WHERE item_id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, itemId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int availableQuantity = rs.getInt("quantity");
                    return availableQuantity >= quantity;
                }
            }
        }
        return false;
    }

    /**
     * Reduces the quantity of the item in the items table after it has been added to the invoice.
     * @param con The database connection.
     * @param itemId The ID of the item.
     * @param quantity The quantity to reduce.
     */
    private void reduceItemQuantity(Connection con, int itemId, int quantity) throws SQLException {
        String query = "UPDATE items SET quantity = quantity - ? WHERE item_id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, itemId);
            stmt.executeUpdate();
        }
    }
}
