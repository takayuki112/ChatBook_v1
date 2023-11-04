package com.example.chatbookui_v1;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ListView;

public class NestedTabPanes_v1 extends Application {
    private ListView<Button> contentListView; // Declare contentListView at class level

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ChatBook UI");

        TabPane mainTabPane = new TabPane();

        // Create a button to add a new main tab
        Button addProfile = new Button("Add Profile");
        addProfile.setOnAction(e -> addProfile(mainTabPane));

        VBox mainVBox = new VBox(addProfile, mainTabPane);
        Scene scene = new Scene(mainVBox, 800, 600); // Increase the size of the screen
        primaryStage.setScene(scene);

        primaryStage.show();
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

            // Create a ChoiceBox to select chat rooms
            ChoiceBox<String> chatRoomChoice = new ChoiceBox<>();
            chatRoomChoice.getItems().addAll("Chatroom 1", "Chatroom 2", "Chatroom 3", "Chatroom 4");
            chatRoomChoice.setValue("Chatroom 1"); // Default selection

            // Create a TextField to enter a new chat room name
            TextField newChatRoomTextField = new TextField();
            newChatRoomTextField.setPromptText("Enter Chat Room Name");

            // Create a button to create a new chat screen
            Button createChatButton = new Button("Create New Chat");
            createChatButton.setOnAction(e -> createNewChat(chatRoomChoice, newChatRoomTextField));

            VBox subTabContent = new VBox(chatRoomChoice, newChatRoomTextField, createChatButton);
            subTab.setContent(subTabContent);
            subTabPane.getTabs().add(subTab);
            subTabPane.getSelectionModel().select(subTab);
        });
    }

    private void createNewChat(ChoiceBox<String> chatRoomChoice, TextField newChatRoomTextField) {
        String chatRoom = chatRoomChoice.getValue();
        String chatRoomName = newChatRoomTextField.getText();

        // Add the new chat room name and a button to open a chat screen to the ListView
        if (!chatRoomName.isEmpty()) {
            Button openChatButton = new Button("Open Chat");
            openChatButton.setOnAction(e -> openChatScreen(chatRoom, chatRoomName));

            contentListView.getItems().add(openChatButton);
            newChatRoomTextField.clear(); // Clear the input field
        }
    }

    private void openChatScreen(String chatRoom, String chatRoomName) {
        // Implement your chat screen opening logic here
        // You can create a new window or navigate to a different view for the selected chat room.
        // This part depends on your specific requirements and UI design.
        System.out.println("Opening chat screen for " + chatRoom + ": " + chatRoomName);
    }
}
