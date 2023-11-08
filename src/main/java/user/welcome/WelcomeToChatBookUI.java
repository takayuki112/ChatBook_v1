package user.welcome;
import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WelcomeToChatBookUI extends Application {
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Welcome to ChatBook");
        primaryStage.setMinWidth(550);
        primaryStage.setMinHeight(350);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        // Add a header section
        Text headerText = new Text("~ Welcome to ChatBook ~");
        Font font = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 40); // Replace "Arial" with your desired font family
        headerText.setId("header");
        headerText.setFont(font);
        headerText.setFill(Color.WHITE);

        Text punchLine = new Text("An Organized Chatting Experience");
        punchLine.setId("punch");
        Font smallFont = new Font("Arial", 12);
        punchLine.setFont(smallFont);
        punchLine.setFill(Color.WHITE);

        Button loginButton = new Button("Existing User Login");
        loginButton.getStyleClass().add("my-buttons1");
        loginButton.setOnAction(e -> {
            // Handle existing user login action
            LoginPortal l = new LoginPortal();
            l.display();
//            primaryStage.close();
        });

        Button createAccountButton = new Button("Create New Account");
        createAccountButton.getStyleClass().add("my-buttons1");
        createAccountButton.setOnAction(e -> {
            // Handle create new account action
            NewUserUI n = new NewUserUI();
            n.start(new Stage());
//            primaryStage.close();
        });

        HBox buttons1 = new HBox();
        buttons1.setSpacing(20);
        buttons1.getChildren().addAll(loginButton, createAccountButton);
        buttons1.setAlignment(Pos.BOTTOM_CENTER);



        layout.getChildren().addAll(headerText, punchLine, buttons1);

        Scene scene = new Scene(layout, 700, 400); // Adjust the width and height as needed
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

