package com.example.chatbookui_v1;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BeforeLunch extends Application {
    public static void main(String[] args) {
        launch(args);
    }

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
        verticalTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); // Disable closing tabs
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
        folderNameDialog.setHeaderText("Enter a Folder Name :");
        folderNameDialog.setContentText("Name:");

        folderNameDialog.showAndWait().ifPresent(folderName -> {
            Tab subTab = new Tab(folderName);

            // Create a ListView to display content within the sub tab
            ListView<String> contentListView = new ListView<>();
            contentListView.setPrefHeight(300); // Increase the height

            // Create a TextField to enter new content
            TextField newContentTextField = new TextField();
            newContentTextField.setPromptText("Enter Content");

            // Create a button to add content to the sub tab
            Button addContentButton = new Button("Create New Chat");
            addContentButton.setOnAction(e -> addContentToSubTab(contentListView, newContentTextField));

            VBox subTabContent = new VBox(contentListView, newContentTextField, addContentButton);
            subTab.setContent(subTabContent);
            subTabPane.getTabs().add(subTab);
            subTabPane.getSelectionModel().select(subTab);
        });
    }

    private void addContentToSubTab(ListView<String> contentListView, TextField newContentTextField) {
        // Get the new content from the TextField
        String newContent = newContentTextField.getText();

        // Add the new content to the ListView
        if (!newContent.isEmpty()) {
            contentListView.getItems().add(newContent);
            newContentTextField.clear(); // Clear the input field after adding the content
        }
    }
}
