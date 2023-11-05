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
import user.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

            if(! isUsernameExists(username))
                showInfo("Username Not Found", "Please Go back and create a new account");

            else if (authenticate(username, password)) {
                System.out.println("Succ");
                User u = new User(username, password);
                BookRetriever b = new BookRetriever(u);
                b.start(new Stage());
                loginStage.close();
            }
            else {
                // Display an error message for unsuccessful login
                showAlert("Login Failed", "Invalid username or password.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            WelcomeToChatBookUI u = new WelcomeToChatBookUI();

            u.start(new Stage());
            loginStage.close();
        });

        layout.getChildren().addAll(headerText, usernameBox, passwordBox, loginButton, backButton);

        Scene scene = new Scene(layout, 400, 250); // Adjust the width and height as needed
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        loginStage.setScene(scene);
        loginStage.show();
    }

    public boolean isUsernameExists(String usernameToCheck) {
        // Specify the full path to the CSV file
        String filePath = "C:\\Users\\HP\\IdeaProjects\\ChatBook-UI_v1\\src\\main\\resources\\UserDataBase\\users.csv";

        try (BufferedReader csvReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = csvReader.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[0]; // Assuming the username is in the first column

                if (username.equals(usernameToCheck)) {
                    return true; // Username already exists
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return false; // Username does not exist
    }

    // Implement your authentication logic here
    private boolean authenticate(String username, String password) {
        String filePath = "C:\\Users\\HP\\IdeaProjects\\ChatBook-UI_v1\\src\\main\\resources\\UserDataBase\\users.csv";

        try (BufferedReader csvReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = csvReader.readLine()) != null) {
                String[] data = line.split(",");
                String u = data[0];
                String p = data[1];

                if (username.equals(u) && password.equals(p)) {
                    return true;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Use INFORMATION for success message
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
