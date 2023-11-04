package com.example.chatbookui_v1;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AllChatsUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ChatBook UI");

        TabPane mainTabPane = createVerticalTabPane();

        // Create a button to add a new main tab
        Button addProfile = new Button(" + ");
        addProfile.setOnAction(e -> addProfile(mainTabPane));
        addProfile.setMinWidth(40);

        VBox mainVBox = new VBox(addProfile, mainTabPane);
        Scene scene = new Scene(mainVBox, 800, 600); // Increase the size of the screen
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private TabPane createVerticalTabPane() {
        TabPane verticalTabPane = new TabPane();
        verticalTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS); // Disable closing tabs
        verticalTabPane.setSide(Side.LEFT);
        verticalTabPane.setTabMinHeight(30);
        verticalTabPane.setTabMinWidth(30);
        verticalTabPane.setMinHeight(500);
        verticalTabPane.setTabMinWidth(40);

        return verticalTabPane;
    }

    private void addProfile(TabPane mainTabPane) {
        TextInputDialog profileNameDialog = new TextInputDialog();
        profileNameDialog.setTitle("Profile Name");
        profileNameDialog.setHeaderText("Enter a Profile Name:");
        profileNameDialog.setContentText("Name:");

        profileNameDialog.showAndWait().ifPresent(profileName -> {
            Tab mainTab = new Tab(profileName);

            TabPane subTabPane = new TabPane(); // Create a sub TabPane for each main tab

            // Create a button to add a new sub tab
            Button addSubTabButton = new Button("Add Folder");
            addSubTabButton.setOnAction(e -> addNewSubTab(subTabPane));

            mainTab.setContent(new VBox(addSubTabButton, subTabPane));

            mainTabPane.getTabs().add(mainTab);
            mainTabPane.getSelectionModel().select(mainTab);
        });
    }

    private void addNewSubTab(TabPane subTabPane) {
        TextInputDialog folderNameDialog = new TextInputDialog();
        folderNameDialog.setTitle("Folder Name");
        folderNameDialog.setHeaderText("Enter a Folder Name:");
        folderNameDialog.setContentText("Name:");

        folderNameDialog.showAndWait().ifPresent(folderName -> {
            Tab subTab = new Tab(folderName);

            // Create a ListView to display content within the sub tab
            ListView<Button> contentListView = new ListView<>();
            contentListView.setPrefHeight(300); // Increase the height

            // Create a TextField to enter new content
            TextField newContentTextField = new TextField();
            newContentTextField.setPromptText("Enter Chat Name");

            // Create a button to add content (chat) to the sub tab
            Button addContentButton = new Button("Create New Chat");
            addContentButton.setOnAction(e -> addChatButtonToSubTab(contentListView, newContentTextField));

            VBox subTabContent = new VBox(contentListView, newContentTextField, addContentButton);
            subTab.setContent(subTabContent);
            subTabPane.getTabs().add(subTab);
            subTabPane.getSelectionModel().select(subTab);
        });
    }

    private void addChatButtonToSubTab(ListView<Button> contentListView, TextField newContentTextField) {
        String chatName = newContentTextField.getText();

        if (!chatName.isEmpty()) {
            Button chatButton = new Button(chatName);
            contentListView.getItems().add(chatButton);
            newContentTextField.clear();

            chatButton.setOnAction(e -> {
                ChoiceDialog<String> dialog = new ChoiceDialog<>("room1", "room2", "room3", "room4");
                dialog.setTitle("Choose a Chat Room");
                dialog.setHeaderText("Select a chat room:");
                dialog.setContentText("Chat Room:");

                // Show the dialog and handle the result
                dialog.showAndWait().ifPresent(selectedRoom -> {
                    // Open a new blank scene in the right section of the sub-tab
                    openChatRoom(selectedRoom);
                });
            });
        }
    }
    private void openChatRoom(String selectedRoom) {
        // Create a new Stage for the chat room
        Stage chatRoomStage = new Stage();
        chatRoomStage.setTitle(selectedRoom);

        // Create a VBox to organize the chat messages, text input field, and send button
        VBox chatRoomContent = new VBox();

        // Create a TextArea for displaying chat messages
        TextArea chatMessagesTextArea = new TextArea();
        chatMessagesTextArea.setEditable(false);
        chatMessagesTextArea.setWrapText(true);

        // Create a TextField for entering messages
        TextField messageTextField = new TextField();
        messageTextField.setPromptText("Type your message");

        // Create a Button for sending messages
        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> {
            // Get the message from the TextField and add it to the chatMessagesTextArea
            String message = messageTextField.getText();
            if (!message.isEmpty()) {
                chatMessagesTextArea.appendText("You: " + message + "\n");
                // You can add code here to send the message to other users in the chat room

                messageTextField.clear();
            }
        });

        chatRoomContent.getChildren().addAll(chatMessagesTextArea, messageTextField, sendButton);

        Scene chatRoomScene = new Scene(chatRoomContent, 400, 300); // Adjust the size as needed
        chatRoomStage.setScene(chatRoomScene);

        // Show the chat room window
        chatRoomStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}

