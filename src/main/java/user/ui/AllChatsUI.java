package user.ui;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import user.User;
import user.ui.str.*;

import java.io.*;
import java.util.*;

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
        // Implement your instance saving logic here
        saveInstance();
    }

    public void saveInstance() {
        //String directoryPath = System.getProperty("user.dir") + "src/main/resources/SavedChatUI";
        String directoryPath = "C:/Users/HP/IdeaProjects/ChatBook-UI_v1/src/main/resources/SavedChatUI";
        //String directoryPath = "/SavedChatUI";
        String id = user.username + "999";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(directoryPath + "/" + id + ".ser"))) {
            out.writeObject(this);
            System.out.println("Instance saved with ID: " + id);
            user.file_id = id; // Make sure "user" is properly defined and accessible here.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AllChatsUI retrieveInstance(String username) {
        //String directoryPath = System.getProperty("user.dir") + "src/main/resources/SavedChatUI";
        String directoryPath = "C:/Users/HP/IdeaProjects/ChatBook-UI_v1/src/main/resources/SavedChatUI";
        //String directoryPath = "/SavedChatUI";
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
        Scene scene = new Scene(mainVBox, 800, 600); // Increase the size of the screen
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);
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
        // Create a button to add a new sub tab
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
        contentListView.setPrefHeight(300); // Increase the height

        //ADD PREV Buttons~
        for(ChatButton c:f.chats){
            addChatButtonToSubTab(contentListView, c.name);
        }

        // Create a TextField to enter new content
        TextField newContentTextField = new TextField();
        newContentTextField.setPromptText("Enter Chat Name");

        // Create a button to add content (chat) to the sub tab
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

        VBox subTabContent = new VBox(contentListView, newContentTextField, addContentButton);
        subTab.setContent(subTabContent);
        subTabPane.getTabs().add(subTab);
        subTabPane.getSelectionModel().select(subTab);
    }


    private void addChatButtonToSubTab(ListView<Button> contentListView, String chatName) {

        if (!chatName.isEmpty()) {
            Button chatButton = new Button(chatName);
            contentListView.getItems().add(chatButton);

            chatButton.setOnAction(e -> {
                ChoiceDialog<String> dialog = new ChoiceDialog<>("room1", "room2", "room3", "room4");
                dialog.setTitle("Choose a Chat Room");
                dialog.setHeaderText("Select a chat room:");
                dialog.setContentText("Chat Room:");

                // Show the dialog and handle the result
                dialog.showAndWait().ifPresent(selectedRoom -> {
                    // Open a new blank scene in the right section of the sub-tab
                    openChatRoom(selectedRoom);
                });
            });
        }
    }
    private void openChatRoom(String selectedRoom) {
        // Create a new Stage for the chat room
        Stage chatRoomStage = new Stage();
        chatRoomStage.setTitle(selectedRoom);

        // Create a VBox to organize the chat messages, text input field, and send button
        VBox chatRoomContent = new VBox();

        // Create a TextArea for displaying chat messages
        TextArea chatMessagesTextArea = new TextArea();
        chatMessagesTextArea.setEditable(false);
        chatMessagesTextArea.setWrapText(true);

        // Create a TextField for entering messages
        TextField messageTextField = new TextField();
        messageTextField.setPromptText("Type your message");

        // Create a Button for sending messages
        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> {
            // Get the message from the TextField and add it to the chatMessagesTextArea
            String message = messageTextField.getText();
            if (!message.isEmpty()) {
                chatMessagesTextArea.appendText("You: " + message + "\n");
                // You can add code here to send the message to other users in the chat room

                messageTextField.clear();
            }
        });

        chatRoomContent.getChildren().addAll(chatMessagesTextArea, messageTextField, sendButton);

        Scene chatRoomScene = new Scene(chatRoomContent, 400, 300); // Adjust the size as needed
        chatRoomStage.setScene(chatRoomScene);

        // Show the chat room window
        chatRoomStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

