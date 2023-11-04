package user.welcome;

import javafx.application.Application;
import javafx.stage.Stage;
import user.ui.AllChatsUI;
import user.ui.str.Profile;

public class Test extends Application {
    public static void main(String[] args) {
        // You can use the Application launch method to start your JavaFX application.
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Define the username for which you want to retrieve the AllChatsUI instance.
        String usernameToRetrieve = "0"; // Replace with the desired username.

        // Retrieve an instance of AllChatsUI for the specified username.
        AllChatsUI instance = AllChatsUI.retrieveInstance(usernameToRetrieve);

        // If ui is not null, start the UI
        if (instance != null) {
            instance.start(primaryStage);

            for (Profile p : instance.profiles){
                System.out.println(p.profileName);
            }
        }
        else {
            // Handle the case when no instance is retrieved, e.g., create a new instance.
            System.out.println("No instance found for username: " + usernameToRetrieve);

        }
    }
}
