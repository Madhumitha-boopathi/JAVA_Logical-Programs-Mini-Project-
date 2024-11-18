package project;


//import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;

public class dbconnect {
    
    public Connection connect(){
        final String username = "postgres";
        final String password = "priya";
        final String URL = "jdbc:postgresql://localhost:5432/Inventory System";
        Connection conn = null;
        try{

            conn = DriverManager.getConnection(URL, username, password);
            System.out.println("Connected to the PostgreSQL database successfully!");
        
        }
        catch(Exception e){
            System.out.println(e);
        }
        return conn;
    }
    public void addNewItem(Connection conn, String item, String desc, int quantity, int reorderLevel, float unitPrice, Date date) {
        try {
            
            String query = "INSERT INTO items (item_name, description, quantity, reorder_level, unit_price, total_amount, purchase_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(query);
    
            stmt.setString(1, item);                    // item_name
            stmt.setString(2, desc);                    // description
            stmt.setInt(3, quantity);                   // quantity_in_stock
            stmt.setInt(4, reorderLevel);               // reorder_level
            stmt.setFloat(5, unitPrice);                // unit_price
            stmt.setFloat(6, unitPrice * quantity);     // total_amount (calculated as unitPrice * quantity)
            stmt.setDate(7, date);                      // purchase_date
    
            
            stmt.executeUpdate();
            System.out.println("Row inserted successfully.");
    
           
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void addStockQuantity(Connection conn, int itemId, int additionalQuantity) {
        try {
            
            String query = "UPDATE items SET quantity = quantity + ? WHERE item_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, additionalQuantity); // additional quantity to add
            stmt.setInt(2, itemId);             // item_id of the item to update
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Stock quantity updated successfully.");
            } else {
                System.out.println("No item found with the given item_id.");
            }

            // Close the statement
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void addSupplier(Connection conn, String supplierName, String contactNo, String email, String address) {
        String sql = "INSERT INTO supplier (supplier_name, contact_no, email, address) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, supplierName);
            pstmt.setString(2, contactNo);  // Contact number stored as a string (e.g., to allow special characters)
            pstmt.setString(3, email);
            pstmt.setString(4, address);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Supplier added successfully!");
            } else {
                System.out.println("Failed to add supplier.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding supplier: " + e.getMessage());
        }
    }
    public void addCustomer(Connection conn, String customerName, String contactNo, String email, String address, int loyaltyPoints,Date lastPurchaseDate) {
        String sql = "INSERT INTO customer (customer_name, contact_no, email, address, loyalty_points, last_purchase_date) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customerName);
            pstmt.setString(2, contactNo); 
            pstmt.setString(3, email);
            pstmt.setString(4, address);
            pstmt.setInt(5, loyaltyPoints); 
            pstmt.setDate(6, lastPurchaseDate); 
            
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Customer added successfully.");
            } else {
                System.out.println("Failed to add customer.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding customer: " + e.getMessage());
        }
    }
    public void addSupplyInvoice(Connection con, int itemId, int supplierId, double supplyPrice, int quantity, Date billDate) {
        String sql = "INSERT INTO supplyInvoice (item_id, supplier_id, supply_price, quantity, bill_amount, bill_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            // Set the values for the prepared statement
            stmt.setInt(1, itemId);
            stmt.setInt(2, supplierId);
            stmt.setDouble(3, supplyPrice);
            stmt.setInt(4, quantity);
            stmt.setDouble(5,quantity*supplyPrice);
            stmt.setDate(6, billDate); // Use the automatically set bill date

            // Execute the insert query
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Supply invoice added successfully!");
            } else {
                System.out.println("Failed to add supply invoice.");
            }
        } catch (SQLException e) {
            System.out.println("Error while adding supply invoice: " + e.getMessage());
        }
    }
    public void addItemsToInvoice(Connection con, int itemId, int invoiceId, int quantity, double unitPrice, double totalAmount) {
        String sql = "INSERT INTO items_invoice (item_id, invoice_id, quantity, unit_price, total_amount) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            // Set the values for the prepared statement
            stmt.setInt(1, itemId);
            stmt.setInt(2, invoiceId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, unitPrice);
            stmt.setDouble(5, totalAmount);

            // Execute the insert query
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Item added to invoice successfully!");
            } else {
                System.out.println("Failed to add item to invoice.");
            }
        } catch (SQLException e) {
            System.out.println("Error while adding item to invoice: " + e.getMessage());
        }
    }
}
    
