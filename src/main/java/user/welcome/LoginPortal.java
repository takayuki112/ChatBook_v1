package user.welcome;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import static javafx.application.Application.launch;

public class LoginPortal extends Application {
    public void display() {
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");

        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setAlignment(Pos.CENTER);

        // Add a header section
        Text headerText = new Text("Login Portal");
        headerText.setFont(Font.font(20));

        HBox usernameBox = new HBox();
        usernameBox.setSpacing(10);
        Label usernameLabel = new Label("Username: ");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameBox.getChildren().addAll(usernameLabel, usernameField);
        usernameBox.setAlignment(Pos.CENTER);

        HBox passwordBox = new HBox();
        passwordBox.setSpacing(10);
        Label passwordLabel = new Label("Password: ");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordBox.getChildren().addAll(passwordLabel, passwordField);
        passwordBox.setAlignment(Pos.CENTER);

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Implement authentication logic here
            if (authenticate(username, password)) {
                // Successful login, close the login window or proceed to the main UI
                loginStage.close();
            } else {
                // Display an error message for unsuccessful login
                showAlert("Login Failed", "Invalid username or password.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            WelcomeToChatbookUI u = new WelcomeToChatbookUI();

            u.start(new Stage());
            loginStage.close();
        });

        layout.getChildren().addAll(headerText, usernameBox, passwordBox, loginButton, backButton);

        Scene scene = new Scene(layout, 400, 250); // Adjust the width and height as needed
        loginStage.setScene(scene);
        loginStage.show();
    }

    // Implement your authentication logic here
    private boolean authenticate(String username, String password) {
        // Check username and password against a database or user data
        // Return true for successful authentication, false otherwise
        return false;
    }

    // Display an alert dialog for errors
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void start(Stage primaryStage) {
        // Create and display the login portal
        LoginPortal loginPortal = new LoginPortal();
        loginPortal.display();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
