package com.example.chatbookui_v1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TabPaneExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TabPane Example");

        TabPane tabPane = new TabPane();

        // Create a button to add a new tab
        Button addButton = new Button("Add Tab");
        addButton.setOnAction(e -> addNewTab(tabPane));

        VBox vBox = new VBox(addButton, tabPane);
        Scene scene = new Scene(vBox, 400, 300);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void addNewTab(TabPane tabPane) {
        Tab newTab = new Tab("New Tab");
        // Customize the content of the new tab as needed
        newTab.setContent(new Button("This is the content of the new tab"));

        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab); // Select the newly added tab
    }
}

