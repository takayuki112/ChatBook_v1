package com.example.chatbookui_v1;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NestedTabsExample_testing extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Nested Tabs Example");

        TabPane tabPane = createVerticalTabPane();

        // Create a button to add a new tab
        Button addTabButton = new Button("Add Tab");
        addTabButton.setOnAction(e -> addNewTab(tabPane));

        // Create a VBox to hold the button and the vertical TabPane
        VBox vBox = new VBox(addTabButton, tabPane);

        Scene scene = new Scene(vBox, 600, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private TabPane createVerticalTabPane() {
        TabPane verticalTabPane = new TabPane();
        verticalTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); // Disable closing tabs
        verticalTabPane.setSide(Side.LEFT);

        return verticalTabPane;
    }

    private void addNewTab(TabPane tabPane) {
        Tab newTab = new Tab("Vertical Tab");

        // Create a horizontal TabPane to nest inside the vertical tab
        TabPane horizontalTabPane = new TabPane();
        horizontalTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Add horizontal tabs
        Tab tab1 = new Tab("Tab 1");
        Tab tab2 = new Tab("Tab 2");
        horizontalTabPane.getTabs().addAll(tab1, tab2);

        // Add the horizontal TabPane to the vertical tab's content
        newTab.setContent(horizontalTabPane);

        // Add the vertical tab to the vertical TabPane
        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab);
    }

}

