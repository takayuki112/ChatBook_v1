package socks;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

import socks.*;

public class JavaFXClient extends Application {
    private Socket socket;
    private Client client;
    private TextArea chatMessagesTextArea;
    private TextField messageTextField;
    private String username;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chat Client");
        primaryStage.setMinWidth(415);
        primaryStage.setMinHeight(600);
        primaryStage.setMaxWidth(415);

        Label prompt = new Label("Enter username to enter chat - ");
        prompt.setId("chatui-prompt");

        chatMessagesTextArea = new TextArea();
        chatMessagesTextArea.getStyleClass().add("client-chatbg");
        Image backgroundImage = new Image("/whatsup.jpg");


        chatMessagesTextArea.setMinHeight(500);
        chatMessagesTextArea.setEditable(false);

        messageTextField = new TextField();
        messageTextField.setMinWidth(355);
        messageTextField.setPromptText("Type your message...");

        Button sendButton = new Button("Send");
        sendButton.getStyleClass().add("send-button");
        sendButton.setOnAction(e -> {
            sendMessage();
            prompt.setVisible(false);
        });
        HBox bottombox = new HBox(messageTextField, sendButton);
        VBox all = new VBox(prompt, chatMessagesTextArea, bottombox);

//
//        BorderPane borderPane = new BorderPane();
//        borderPane.setCenter(new ScrollPane(chatMessagesTextArea));
//        borderPane.setBottom(messageTextField);
//        borderPane.setRight(sendButton);

//        Scene scene = new Scene(borderPane, 400, 300);
        Scene scene = new Scene(all, 400, 450);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(e -> {
            System.out.println("Alvida?");
            if (client != null) {
                client.closeEverything(socket, null, null);
            }
        });

        primaryStage.show();

        // Get the username from the user
        username = getUsernameFromUser();

        try {
            socket = new Socket("localhost", 1234);
            client = new Client(socket, username, this);
            client.listenForMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String messageToSend = messageTextField.getText();
        if (!messageToSend.isEmpty()) {
            client.sendMessageFromUI(messageToSend);
            messageTextField.clear();
            displayMyMessage(messageToSend);
        }
    }
    public void displayMyMessage(String message) { //what i sent
        String m1 = message + "\n";

        Platform.runLater(() -> chatMessagesTextArea.appendText(String.valueOf(m1)));
    }

    //    public void displayMyMessage(String message) {
//        String m1 = "You: " + message + "\n";
//        Platform.runLater(() -> {
//            Text sentText = new Text(m1);
//            sentText.setFill(Color.BLUE); // Set color for sent messages
//            chatMessagesTextArea.appendText(sentText);
//        });
//    }
    public void displayMessage(String message) { //what i received

        Platform.runLater(() -> chatMessagesTextArea.appendText(message + "\n"));
    }

    private String getUsernameFromUser() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Username");
        dialog.setHeaderText("Enter your username:");
        dialog.setContentText("Username:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            return result.get();
        } else {
            // Handle the case where the user cancels the dialog or doesn't enter a username.
            // You can return a default or show an error message.
            return "DefaultUsername"; // Provide a default username or handle it as needed.
        }
    }

}