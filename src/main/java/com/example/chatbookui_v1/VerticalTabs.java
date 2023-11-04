package com.example.chatbookui_v1;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class VerticalTabs extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Vertical Tabs Example");

        TabPane tabPane = createVerticalTabPane();

        // Create a button to add a new tab
        Button addTabButton = new Button("Add Tab");
        addTabButton.setOnAction(e -> addNewTab(tabPane));

        // Create a VBox to hold the button and the vertical TabPane
        VBox vBox = new VBox(addTabButton, tabPane);

        Scene scene = new Scene(vBox, 400, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private TabPane createVerticalTabPane() {
        TabPane verticalTabPane = new TabPane();
        verticalTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); // Disable closing tabs

        // Customize the appearance to display tabs vertically
        verticalTabPane.setSide(Side.LEFT);

        return verticalTabPane;
    }

    private void addNewTab(TabPane tabPane) {
        Tab newTab = new Tab("New Tab");

        // Create a ListView to display content within the tab
        ListView<String> contentListView = new ListView<>();
        contentListView.setPrefHeight(300); // Adjust the height as needed

        // Create a TextField to enter new content
        TextField newContentTextField = new TextField();
        newContentTextField.setPromptText("Enter Content");

        // Create a button to add content to the tab
        Button addContentButton = new Button("Add Content");
        addContentButton.setOnAction(e -> addContentToTab(contentListView, newContentTextField));

        VBox tabContent = new VBox(contentListView, newContentTextField, addContentButton);
        newTab.setContent(tabContent);
        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab);
    }

    private void addContentToTab(ListView<String> contentListView, TextField newContentTextField) {
        // Get the new content from the TextField
        String newContent = newContentTextField.getText();

        // Add the new content to the ListView
        if (!newContent.isEmpty()) {
            contentListView.getItems().add(newContent);
            newContentTextField.clear(); // Clear the input field after adding the content
        }
    }
}
