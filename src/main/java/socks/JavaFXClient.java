package socks;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
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

        chatMessagesTextArea = new TextArea();
        chatMessagesTextArea.setEditable(false);

        messageTextField = new TextField();
        messageTextField.setPromptText("Type your message...");

        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> sendMessage());

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(new ScrollPane(chatMessagesTextArea));
        borderPane.setBottom(messageTextField);
        borderPane.setRight(sendButton);

        Scene scene = new Scene(borderPane, 400, 300);

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
            displayMessage(messageToSend);
        }
    }

    public void displayMessage(String message) {
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
