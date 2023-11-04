package user.welcome;
import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WelcomeToChatbookUI extends Application {
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Welcome to Chatbook");

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        // Add a header section
        Text headerText = new Text("~ Welcome to ChatBook ~");
        headerText.setFont(Font.font(20));
        Text punchLine = new Text("An Organized Chatting Experience");
        Font smallFont = new Font("Arial", 12);
        punchLine.setFont(smallFont);

        Button loginButton = new Button("Existing User Login");
        loginButton.setOnAction(e -> {
            // Handle existing user login action
            LoginPortal l = new LoginPortal();
            l.display();
            primaryStage.close();
        });

        Button createAccountButton = new Button("Create New Account");
        createAccountButton.setOnAction(e -> {
            // Handle create new account action
            NewUserUI n = new NewUserUI();
            n.start(new Stage());
            primaryStage.close();
        });

        layout.getChildren().addAll(headerText, punchLine, loginButton, createAccountButton);

        Scene scene = new Scene(layout, 400, 200); // Adjust the width and height as needed
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}

