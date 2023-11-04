package com.example.chatbookui_v1;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TabPaneWithCoolStyles extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TabPane with Cool Styles");

        TabPane tabPane = new TabPane();

        // Create a button to add a new tab
        Button addButton = new Button("Add Tab");
        addButton.setOnAction(e -> addNewTab(tabPane));

        VBox vBox = new VBox(addButton, tabPane);
        Scene scene = new Scene(vBox, 400, 300);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm()); // Load the CSS file

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addNewTab(TabPane tabPane) {
        Tab newTab = new Tab("New Tab");

        // Create a button to add a new folder within the tab
        Button addFolderButton = new Button("Add Folder");
        addFolderButton.setOnAction(e -> addNewFolder(newTab));

        VBox tabContent = new VBox(addFolderButton);
        newTab.setContent(tabContent);

        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab); // Select the newly added tab
    }

    private void addNewFolder(Tab tab) {
        // Create and add a new folder (you can customize the folder's content)
        VBox folderContent = new VBox(new Button("This is a new folder"));
        tab.setContent(folderContent);
    }
}
