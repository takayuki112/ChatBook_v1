package user.welcome;

import javafx.application.Application;
import javafx.stage.Stage;
import user.User;
import user.ui.AllChatsUI;
import user.ui.str.Profile;

public class BookRetriever extends Application {
    User usr;
    boolean blank;

    public BookRetriever(){
        this.usr = new User("r", "0");
        blank = false;
    }
    public BookRetriever(User usr){
        this.usr = usr;
        blank = false;
    }
    public BookRetriever(User usr, boolean b){
        this.usr = usr;
        blank = b;
    }

    public static void main(String[] args) {
        // You can use the Application launch method to start your JavaFX application.
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        // Define the username for which you want to retrieve the AllChatsUI instance.

        // Retrieve an instance of AllChatsUI for the specified username.
        AllChatsUI instance = AllChatsUI.retrieveInstance(usr.username);

        // If ui is not null, start the UI
        if (instance != null) {
            instance.start(primaryStage);

            for (Profile p : instance.profiles){
                System.out.println(p.profileName);
            }
        }
        else {
            // Handle the case when no instance is retrieved, e.g., create a new instance.
            System.out.println("No instance found for username: " + usr.username);
            instance = new AllChatsUI();

        }
    }
}
