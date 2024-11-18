package project;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginPage {

    // Method to create and return the login page layout
    @SuppressWarnings("unused")
    public VBox createLoginPage() {
        // Create the UI components

        // Title label
        Label titleLabel = new Label("Inventory Management System");
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        // Username field
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");

        // Password field
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");

        // Login button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 15px;");

        // Clear button
        Button clearButton = new Button("Clear");
        clearButton.setStyle("-fx-background-color: #FF5733; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 15px;");

        // Message label (to show error/success messages)
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");

        // Action for login button
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Validate username and password (hardcoded validation for simplicity)
            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Please enter both username and password.");
            } else if (username.equals("admin") && password.equals("admin123")) {
                messageLabel.setText("Login successful!");
                messageLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: green;");

                // Here you would typically transition to the main inventory dashboard
                // For now, we just close the login window as an example
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close(); // Close the login window upon successful login

                // You can now open the main inventory page or other functionality
                // e.g., openDashboard();
            } else {
                messageLabel.setText("Invalid username or password.");
            }
        });

        // Action for clear button
        clearButton.setOnAction(event -> {
            usernameField.clear();
            passwordField.clear();
            messageLabel.setText("");
        });

        // Create the layout
        VBox layout = new VBox(20); // VBox layout with 20px spacing
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 40px; -fx-background-radius: 10px;");

        // Add UI elements to the layout
        layout.getChildren().addAll(
            titleLabel,
            usernameLabel,
            usernameField,
            passwordLabel,
            passwordField,
            loginButton,
            clearButton,
            messageLabel
        );

        Image image = new Image(getClass().getResource("/images/login.jpg").toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(
            image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER, BackgroundSize.DEFAULT
        );
        layout.setBackground(new Background(backgroundImage));

        return layout;  // Return the layout
    }
}
