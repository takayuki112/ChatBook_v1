package user.welcome;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import user.User;

import java.io.*;


import static javafx.application.Application.launch;

public class NewUserUI extends Application {
    public void display() {
        Stage signUpStage = new Stage();
        signUpStage.setTitle("New User");

        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setAlignment(Pos.CENTER);

        // Add a header section
        Text headerText = new Text("Create a new Account - ");
        headerText.setFont(Font.font(23));
        headerText.setFill(Color.WHITE);

        HBox usernameBox = new HBox();
        usernameBox.setSpacing(10);
        Label usernameLabel = new Label("Username: ");
        usernameLabel.getStyleClass().add("labels-newUserUI");
        TextField usernameField = new TextField();

        usernameField.setPromptText("Enter a username");
        usernameBox.getChildren().addAll(usernameLabel, usernameField);
        usernameBox.setAlignment(Pos.CENTER);

        HBox passwordBox = new HBox();
        passwordBox.setSpacing(10);
        Label passwordLabel = new Label("Password: ");
        passwordLabel.getStyleClass().add("labels-newUserUI");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordBox.getChildren().addAll(passwordLabel, passwordField);
        passwordBox.setAlignment(Pos.CENTER);

        HBox passwordBox2 = new HBox();
        passwordBox2.setSpacing(10);
        Label passwordLabel2 = new Label("Re-enter : ");
        passwordLabel2.getStyleClass().add("labels-newUserUI");
        PasswordField passwordField2 = new PasswordField();
        passwordField2.setPromptText("Password Again ");
        passwordBox2.getChildren().addAll(passwordLabel2, passwordField2);
        passwordBox2.setAlignment(Pos.CENTER);

        //ACTION BUTTONS

        Button signUp = new Button("Sign Up");
        signUp.getStyleClass().add("my-buttons");
        signUp.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String password2 = passwordField2.getText();
            if(isUsernameExists(username))
                showAlert("Already Registered", "Username Exists. Please go back & LogIn");
            else if(password2.equals(password)){
                createNewUser(username, password);
                signUpStage.close();

            }
            else {
                showAlert("Password Mismatch", "Passwords do not match.");
                passwordField.clear();
                passwordField2.clear();
            }
        });

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("my-buttons");
        backButton.setOnAction(e -> {
            WelcomeToChatBookUI u = new WelcomeToChatBookUI();

            u.start(new Stage());
            signUpStage.close();
        });

        HBox buttons = new HBox();
        buttons.setSpacing(20);
        buttons.getChildren().addAll(signUp, backButton);
        buttons.setAlignment(Pos.BOTTOM_CENTER);


        layout.getChildren().addAll(headerText, usernameBox, passwordBox, passwordBox2, buttons);

        Scene scene = new Scene(layout, 400, 250); // Adjust the width and height as needed
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        signUpStage.setScene(scene);
        signUpStage.show();
    }

    // Implement your authentication logic here
    public boolean isUsernameExists(String usernameToCheck) {
        // Specify the full path to the CSV file
        //String filePath = "C:\\Users\\HP\\IdeaProjects\\ChatBook-UI_v1\\src\\main\\resources\\UserDataBase\\users.csv";

        String baseDirectory = System.getProperty("user.dir");
        String relativePath = "/src/main/resources/UserDataBase/users.csv";
        String filePath = baseDirectory + relativePath;

        try (BufferedReader csvReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = csvReader.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[0]; // Assuming the username is in the first column

                if (username.equals(usernameToCheck)) {
                    return true; // Username already exists
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; // Username does not exist
    }

    public void createNewUser(String usr, String pwd) {
        // Specify the full path to the CSV file
        String baseDirectory = System.getProperty("user.dir");
        String relativePath = "/src/main/resources/UserDataBase/users.csv";
        String filePath = baseDirectory + relativePath;
        try (FileWriter csvWriter = new FileWriter(filePath, true)) {
            csvWriter.append(usr);
            csvWriter.append(",");
            csvWriter.append(pwd);
            csvWriter.append("\n");
            showSuccessAlert("Success", "User registered successfully.");
            User u = new User(usr, pwd);
            LaunchNew l = new LaunchNew(u);
            l.start(new Stage());

        }
        catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to register user.");
        }
    }


    // Display an alert dialog for errors
    public static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void showSuccessAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Use INFORMATION for success message
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public void start(Stage primaryStage) {
        // Create and display the login portal
        NewUserUI loginPortal = new NewUserUI();
        loginPortal.display();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
