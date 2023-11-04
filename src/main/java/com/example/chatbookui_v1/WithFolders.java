package com.example.chatbookui_v1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class WithFolders extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private ComboBox<String> folderComboBox; // Move the ComboBox to be a class member for easier access

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ChatBook UI");

        TabPane tabPane = new TabPane();

        // Create a button to add a new tab
        Button addTabButton = new Button("Add Tab");
        addTabButton.setOnAction(e -> addNewTab(tabPane));

        // Create a button to add a new folder to the ComboBox
        Button addFolderButton = new Button("Add Folder");
        addFolderButton.setOnAction(e -> addNewFolder());

        folderComboBox = new ComboBox<>();
        folderComboBox.setPromptText("Select Folder");

        VBox vBox = new VBox(addTabButton, addFolderButton, folderComboBox, tabPane);
        Scene scene = new Scene(vBox, 600, 400); // Increase the size of the screen
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void addNewTab(TabPane tabPane) {
        Tab newTab = new Tab("New Tab");

        // Create a ListView to display chats within the folder
        ListView<String> chatListView = new ListView<>();
        chatListView.setPrefHeight(300); // Increase the height

        // Create a TextField to enter a new chat message
        TextField newChatTextField = new TextField();
        newChatTextField.setPromptText("Enter Chat Message");

        // Create a button to add a chat to the current folder
        Button addChatButton = new Button("Add Chat");
        addChatButton.setOnAction(e -> addChatToFolder(chatListView, newChatTextField));

        VBox tabContent = new VBox(chatListView, newChatTextField, addChatButton);
        newTab.setContent(tabContent);

        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab); // Select the newly added tab
    }

    private void addNewFolder() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add New Folder");
        dialog.setHeaderText("Enter the name of the new folder:");
        dialog.setContentText("Folder Name:");

        // Show the dialog and get the result
        dialog.showAndWait().ifPresent(folderName -> {
            folderComboBox.getItems().add(folderName);
        });
    }

    private void addChatToFolder(ListView<String> chatListView, TextField newChatTextField) {
        // Get the new chat message from the TextField
        String newChat = newChatTextField.getText();

        // Add the new chat message to the ListView
        if (!newChat.isEmpty()) {
            chatListView.getItems().add(newChat);
            newChatTextField.clear(); // Clear the input field after adding the chat
        }
    }
}
