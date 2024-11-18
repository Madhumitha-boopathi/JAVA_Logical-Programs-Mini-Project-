package project;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Creating buttons for the actions
        Button addItemButton = new Button("Add Item");
        Button addSupplierButton = new Button("Add Supplier");
        Button addCustomerButton = new Button("Add Customer");
        Button addInvoiceButton = new Button("Create Invoice");
        Button addSupplyInvoiceButton = new Button("Add Supply Invoice");
        Button addItemsToInvoiceButton = new Button("Add Items to Invoice");
        Button viewSuppliersButton = new Button("View Suppliers");
        Button viewItemsButton = new Button("View Items");
        Button viewCustomersButton = new Button("View Customers");
        Button viewInvoicesButton = new Button("View Invoices");

        // Style buttons to make the text fully visible
        String buttonStyle = "-fx-font-size: 14px; -fx-padding: 10px 20px; -fx-background-color: #555555; -fx-text-fill: white; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-pref-width: 200px;";
        addItemButton.setStyle(buttonStyle);
        addSupplierButton.setStyle(buttonStyle);
        addCustomerButton.setStyle(buttonStyle);
        addInvoiceButton.setStyle(buttonStyle);
        addSupplyInvoiceButton.setStyle(buttonStyle);
        addItemsToInvoiceButton.setStyle(buttonStyle);
        viewSuppliersButton.setStyle(buttonStyle);
        viewItemsButton.setStyle(buttonStyle);
        viewCustomersButton.setStyle(buttonStyle);
        viewInvoicesButton.setStyle(buttonStyle);

        // Set button actions
        addItemButton.setOnAction(e -> showAddItemPage());
        addSupplierButton.setOnAction(e -> showAddSupplierPage());
        addCustomerButton.setOnAction(e -> showAddCustomerPage());
        addInvoiceButton.setOnAction(e -> showAddInvoicePage());
        addSupplyInvoiceButton.setOnAction(e -> showAddSupplyInvoicePage());
        addItemsToInvoiceButton.setOnAction(e -> showAddItemsToInvoicePage());
        viewSuppliersButton.setOnAction(e -> displaySuppliers(primaryStage));
        viewItemsButton.setOnAction(e -> displayItems(primaryStage));
        viewCustomersButton.setOnAction(e -> displayCustomers(primaryStage));
        viewInvoicesButton.setOnAction(e -> displayInvoices(primaryStage));

        // Create VBox layout for buttons
        VBox leftColumn = new VBox(20);  // 20px spacing between buttons in the left column
        leftColumn.setAlignment(Pos.CENTER_LEFT);

        VBox rightColumn = new VBox(20);  // 20px spacing between buttons in the right column
        rightColumn.setAlignment(Pos.CENTER_LEFT);

        VBox centerColumn = new VBox(20);  // 20px spacing between buttons in the center
        centerColumn.setAlignment(Pos.CENTER);

        // Add buttons to the left, right, and center columns
        leftColumn.getChildren().addAll(
            addItemButton, addSupplierButton, addCustomerButton, addInvoiceButton,
            addSupplyInvoiceButton
        );

        rightColumn.getChildren().addAll(
            addItemsToInvoiceButton, viewSuppliersButton, viewItemsButton,
            viewCustomersButton, viewInvoicesButton
        );

        centerColumn.getChildren().addAll(
            // If you want to center specific buttons, you can add them here
        );

        // Create HBox layout to hold left, right, and center columns
        HBox hBox = new HBox(40); // 40px spacing between the columns
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(leftColumn, centerColumn, rightColumn);

        // Create main layout with background image
        VBox mainLayout = new VBox();
        mainLayout.getChildren().add(hBox);

        // Set the background image
        BackgroundImage background = new BackgroundImage(
            new Image(getClass().getResource("/project/Background/login.png").toExternalForm(), 800, 600, false, true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER, BackgroundSize.DEFAULT
        );
        mainLayout.setBackground(new Background(background));

        // Set up the scene and stage
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Inventory Management System");
        primaryStage.show();
    }

    // Navigation methods for different pages
    private void showAddItemPage() {
        AddItemPage itemPage = new AddItemPage();
        Stage itemStage = new Stage();
        itemPage.start(itemStage);
    }

    private void showAddSupplierPage() {
        AddSupplierPage supplierPage = new AddSupplierPage();
        Stage supplierStage = new Stage();
        supplierPage.start(supplierStage);
    }

    private void showAddCustomerPage() {
        AddCustomerPage customerPage = new AddCustomerPage();
        Stage customerStage = new Stage();
        customerPage.start(customerStage);
    }

    private void showAddInvoicePage() {
        AddInvoicePage invoicePage = new AddInvoicePage();
        Stage invoiceStage = new Stage();
        invoicePage.start(invoiceStage);
    }

    private void showAddSupplyInvoicePage() {
        AddSupplyInvoicePage supplyInvoicePage = new AddSupplyInvoicePage();
        Stage supplyInvoiceStage = new Stage();
        supplyInvoicePage.start(supplyInvoiceStage);
    }

    private void showAddItemsToInvoicePage() {
        AddItemsToInvoicePage itemsToInvoicePage = new AddItemsToInvoicePage();
        Stage itemsToInvoiceStage = new Stage();
        itemsToInvoicePage.start(itemsToInvoiceStage);
    }

    // Go back to the home page
    private void goBackToHomePage(Stage primaryStage) {
        start(primaryStage); // This will navigate back to the home page by calling the start method
    }

    // Methods to display items, suppliers, customers, and invoices
    @SuppressWarnings("unchecked")
    public void displayItems(Stage primaryStage) {
        String query = "SELECT * FROM items";
        ObservableList<Item> items = FXCollections.observableArrayList();
        dbconnect d = new dbconnect();

        try (Connection conn = d.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                items.add(new Item(
                        rs.getInt("item_id"),
                        rs.getString("item_name"),
                        rs.getString("description"),
                        rs.getInt("quantity"),
                        rs.getInt("reorder_level"),
                        rs.getBigDecimal("unit_price"),
                        rs.getBigDecimal("total_amount"),
                        rs.getDate("purchase_date")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create a TableView
        TableView<Item> tableView = new TableView<>(items);
        TableColumn<Item, Integer> idColumn = new TableColumn<>("Item ID");
        TableColumn<Item, String> nameColumn = new TableColumn<>("Item Name");
        TableColumn<Item, String> descColumn = new TableColumn<>("Description");
        TableColumn<Item, Integer> quantityColumn = new TableColumn<>("Quantity");
        TableColumn<Item, Integer> reorderColumn = new TableColumn<>("Reorder Level");
        TableColumn<Item, String> priceColumn = new TableColumn<>("Unit Price");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        reorderColumn.setCellValueFactory(new PropertyValueFactory<>("reorderLevel"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        tableView.getColumns().addAll(idColumn, nameColumn, descColumn, quantityColumn, reorderColumn, priceColumn);

        // Create "Back" button to return to the home page
        Button backButton = new Button("Back to Home Page");
        backButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px; -fx-background-color: #555555; -fx-text-fill: white;");
        backButton.setOnAction(e -> goBackToHomePage(primaryStage));

        // Create a VBox to hold the TableView and Back button
        VBox layout = new VBox(20, tableView, backButton);
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Items List");
        primaryStage.show();
    }

    public void displaySuppliers(Stage primaryStage) {
        String query = "SELECT * FROM supplier";
        ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
        dbconnect d = new dbconnect();

        try (Connection conn = d.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                suppliers.add(new Supplier(
                        rs.getInt("supplier_id"),
                        rs.getString("supplier_name"),
                        rs.getString("contact_no"),
                        rs.getString("email"),
                        rs.getString("address")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create TableView for Suppliers
        TableView<Supplier> tableView = new TableView<>(suppliers);
        TableColumn<Supplier, Integer> idColumn = new TableColumn<>("Supplier ID");
        TableColumn<Supplier, String> nameColumn = new TableColumn<>("Supplier Name");
        TableColumn<Supplier, String> contactColumn = new TableColumn<>("Contact No");
        TableColumn<Supplier, String> emailColumn = new TableColumn<>("Email");
        TableColumn<Supplier, String> addressColumn = new TableColumn<>("Address");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        tableView.getColumns().addAll(idColumn, nameColumn, contactColumn, emailColumn, addressColumn);

        // Create the "Back" button
        Button backButton = new Button("Back to Home Page");
        backButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px; -fx-background-color: #555555; -fx-text-fill: white;");
        backButton.setOnAction(e -> goBackToHomePage(primaryStage));

        // Create the layout and set the scene
        VBox layout = new VBox(20, tableView, backButton);
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Suppliers List");
        primaryStage.show();
    }

    public void displayCustomers(Stage primaryStage) {
        String query = "SELECT * FROM customer";  // Modify according to your database schema
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        dbconnect d = new dbconnect();
    
        try (Connection conn = d.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
    
            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("contact_no"),
                        rs.getString("email"),
                        rs.getString("address"), 0, 
                        rs.getDate("last_purchase_date")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        // Create TableView for Customers
        TableView<Customer> tableView = new TableView<>(customers);
        TableColumn<Customer, Integer> idColumn = new TableColumn<>("Customer ID");
        TableColumn<Customer, String> nameColumn = new TableColumn<>("Customer Name");
        TableColumn<Customer, String> contactColumn = new TableColumn<>("Contact No");
        TableColumn<Customer, String> emailColumn = new TableColumn<>("Email");
        TableColumn<Customer, String> addressColumn = new TableColumn<>("Address");
        TableColumn<Customer, Date> dateColumn = new TableColumn<>("Last Purchase Date");
    
        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("lastPurchaseDate"));
    
        tableView.getColumns().addAll(idColumn, nameColumn, contactColumn, emailColumn, addressColumn,dateColumn);
    
        // Create the "Back" button
        Button backButton = new Button("Back to Home Page");
        backButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px; -fx-background-color: #555555; -fx-text-fill: white;");
        backButton.setOnAction(e -> goBackToHomePage(primaryStage));
    
        // Create the layout and set the scene
        VBox layout = new VBox(20, tableView, backButton);
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Customers List");
        primaryStage.show();
    }
    
    public void displayInvoices(Stage primaryStage) {
        String query = "SELECT * FROM invoice";  // Modify according to your database schema
        ObservableList<Invoice> invoices = FXCollections.observableArrayList();
        dbconnect d = new dbconnect();
    
        try (Connection conn = d.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
    
            while (rs.next()) {
                invoices.add(new Invoice(
                        rs.getInt("invoice_id"),
                        rs.getInt("customer_id"),
                        rs.getBigDecimal("bill_amount"),
                        rs.getDate("invoice_date")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        // Create TableView for Invoices
        TableView<Invoice> tableView = new TableView<>(invoices);
        TableColumn<Invoice, Integer> idColumn = new TableColumn<>("Invoice ID");
        TableColumn<Invoice, Integer> customerIdColumn = new TableColumn<>("Customer ID");
        TableColumn<Invoice, String> dateColumn = new TableColumn<>("Invoice Date");
        TableColumn<Invoice, String> totalAmountColumn = new TableColumn<>("Bill Amount");
    
        idColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceDate"));
        totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
    
        tableView.getColumns().addAll(idColumn, customerIdColumn, dateColumn, totalAmountColumn);
    
        // Create the "Back" button
        Button backButton = new Button("Back to Home Page");
        backButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px; -fx-background-color: #555555; -fx-text-fill: white;");
        backButton.setOnAction(e -> goBackToHomePage(primaryStage));
    
        // Create the layout and set the scene
        VBox layout = new VBox(20, tableView, backButton);
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Invoices List");
        primaryStage.show();
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}