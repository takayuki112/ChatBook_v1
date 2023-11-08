package user.ui;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import socks.JavaFXClient;
import socks.Server;
import user.User;
import user.ui.str.*;

import java.io.*;
import java.net.ServerSocket;
import java.util.*;

import static user.welcome.NewUserUI.showAlert;

public class AllChatsUI extends Application implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public List<Profile> profiles;
    User user;
    public AllChatsUI(){
        this.user = new User("0", "0");
        profiles = new ArrayList<>();
    }
    public AllChatsUI(User u){
        this.user = u;
        profiles = new ArrayList<>();
    }

    //SAVE INSTANCE ~~
    public void saveInstanceOnExit() {
        saveInstance();
    }

    public void saveInstance() {
        //String directoryPath = System.getProperty("user.dir") + "src/main/resources/SavedChatUI";
        //String directoryPath = "C:/Users/HP/IdeaProjects/ChatBook-UI_v1/src/main/resources/SavedChatUI";
        //String directoryPath = "/SavedChatUI";

        String baseDirectory = System.getProperty("user.dir");
        String relativePath = "/src/main/resources/SavedChatUI";
        String directoryPath = baseDirectory + relativePath;

        String id = user.username + "999";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(directoryPath + "/" + id + ".ser"))) {
            out.writeObject(this);
            System.out.println("Instance saved with ID: " + id);
            user.file_id = id;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AllChatsUI retrieveInstance(String username) {

        //String directoryPath = "C:/Users/HP/IdeaProjects/ChatBook-UI_v1/src/main/resources/SavedChatUI";
        //String directoryPath = "/SavedChatUI";

        String baseDirectory = System.getProperty("user.dir");
        String relativePath = "/src/main/resources/SavedChatUI";
        String directoryPath = baseDirectory + relativePath;

        String id = username + "999";
        AllChatsUI instance = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(directoryPath + File.separator + id + ".ser"))) {
            instance = (AllChatsUI) in.readObject();
            System.out.println("Instance retrieved with ID: " + id);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return instance;
    }

    //START
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ChatBook UI");

        TabPane ProfileTabPane = createVerticalTabPane();  //used once to make the pane.

        //ADD Profiles in the list previously...
        for(Profile p : profiles){
            createProfileUI(p, ProfileTabPane);
        }


        // ADD PROFILE + Button ~~
        Button addProfile = new Button(" + ");
        addProfile.setOnAction(e -> addProfile(ProfileTabPane));
        addProfile.setMinWidth(40);

        VBox mainVBox = new VBox(addProfile, ProfileTabPane);
        Scene scene = new Scene(mainVBox, 800, 550); // Increase the size of the screen
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);

        //Initialize Server
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            Server server = new Server(serverSocket);

            Thread serverThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    server.startServer();
                }
            });

            Thread shutdownHook = new Thread(new Runnable() {
                @Override
                public void run() {
                    server.closeServerSocket();
                    System.out.println("closeServer called - ");
                }
            });

            serverThread.start();

            Runtime.getRuntime().addShutdownHook(shutdownHook);
        }
        catch (IOException e) {
            // Handle exceptions if necessary
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(this::saveInstanceOnExit));
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

    private void addProfile(TabPane t) {

        TextInputDialog profileNameDialog = new TextInputDialog();
        profileNameDialog.setTitle("Profile Name");
        profileNameDialog.setHeaderText("Enter a Profile Name:");
        profileNameDialog.setContentText("Name:");

        profileNameDialog.showAndWait().ifPresent(profileName -> {
            //PROFILE OBJECT ~~
            Profile p = new Profile(profileName);
            profiles.add(p);
            createProfileUI(p, t);

        });
    }
    public void createProfileUI(Profile p, TabPane ProfileTabPane){

        String profileName = p.profileName;

        //THE UI STUFF ~~
        Tab profileTab = new Tab(profileName);
        TabPane folderPane = new TabPane(); // Create a Folders Pane for each Profile
        Button addSubTabButton = new Button("Add Folder");

        //ADD FOLDERS previous session
        for(Folder f: p.folders){
            createFolderUI(f, folderPane);

        }
        //ANY NEW Folders
        addSubTabButton.setOnAction(e -> addFolder(p, folderPane));
        profileTab.setContent(new VBox(addSubTabButton, folderPane));

        ProfileTabPane.getTabs().add(profileTab);
        ProfileTabPane.getSelectionModel().select(profileTab);

    }

    private void addFolder(Profile p, TabPane subTabPane) {
        TextInputDialog folderNameDialog = new TextInputDialog();
        folderNameDialog.setTitle("Folder Name");
        folderNameDialog.setHeaderText("Enter a Folder Name:");
        folderNameDialog.setContentText("Name:");

        folderNameDialog.showAndWait().ifPresent(folderName -> {

            //FOLDER OBJECT -
            Folder f = new Folder(folderName);
            p.folders.add(f);
            createFolderUI(f, subTabPane);

        });
    }
    private void createFolderUI(Folder f, TabPane subTabPane){

        String folderName = f.name;

        //FOLDER UI ~~
        Tab subTab = new Tab(folderName);

        // Create a ListView to display content within the sub tab
        ListView<Button> contentListView = new ListView<>();
        contentListView.setPrefHeight(900); // Increase the height

        //ADD PREV Buttons~
        for(ChatButton c:f.chats){
            addChatButtonToSubTab(contentListView, c.name);
        }

        //CREATE NEW CHATS ~
        TextField newContentTextField = new TextField();
        newContentTextField.setMinSize(800, 23);
        newContentTextField.setPromptText("Enter Name Of Chat To Be Added...");

        Button addContentButton = new Button("Create New Chat");

        addContentButton.setOnAction(e -> {
            String chatName = newContentTextField.getText();
            if (!chatName.isEmpty()) {
                addChatButtonToSubTab(contentListView, chatName);
                newContentTextField.clear();

                //Add to f.chats
                f.chats.add(new ChatButton(chatName));
            }
        });
        newContentTextField.clear();
        HBox newChat = new HBox(addContentButton, newContentTextField);
        VBox subTabContent = new VBox(contentListView, newChat );
        subTab.setContent(subTabContent);
        subTabPane.getTabs().add(subTab);
        subTabPane.getSelectionModel().select(subTab);
    }


    private void addChatButtonToSubTab(ListView<Button> contentListView, String chatName) {

        if (!chatName.isEmpty()) {
            Button chatButton = new Button(chatName);
            contentListView.getItems().add(chatButton);

            chatButton.setOnAction(e -> {
                //DIALOG BOX - To open a New Chat -
                ChoiceDialog<String> dialog = new ChoiceDialog<>("room1", "room2", "room3", "room4");
                dialog.setTitle("Choose a Chat Room");
                dialog.setHeaderText("Select a chat room:");
                dialog.setContentText("Chat Room:");

                dialog.showAndWait().ifPresent(selectedRoom -> {
                    // Open a new blank scene in the right section of the sub-tab
                    openChatRoom1(selectedRoom);
                });
            });
        }
    }
    private void openChatRoom1(String selectedRoom) {


        //Launch ChatUI
        JavaFXClient chatWindow = new JavaFXClient();
        chatWindow.start(new Stage());

    }


    public static void main(String[] args) {
        launch(args);
    }
}

