package user.welcome;

import javafx.application.Application;
import javafx.stage.Stage;
import user.User;
import user.ui.AllChatsUI;

public class LaunchNew extends Application {
    String usr;
    String pwd;
    public LaunchNew(){
        usr = "rahul";
        pwd = "ra";
    }
    public LaunchNew(String u, String p){
        usr = u;
        pwd = p;
    }
    public LaunchNew(User u){
        usr = u.username;
        pwd = u.password;
    }
    public static void main(String[] args) {
        launch(args); // This launches the JavaFX application.
    }
    @Override
    public void start(Stage primaryStage) {
        AllChatsUI a = new AllChatsUI(new User(usr, pwd));
        a.start(new Stage());
    }
}
