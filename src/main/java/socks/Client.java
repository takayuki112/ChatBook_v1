package socks;

//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//import javafx.scene.text.Text;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    private JavaFXClient javafxClient;

    public Client(Socket socket, String username, JavaFXClient javafxClient){
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username=username;
            this.javafxClient = javafxClient;
        } catch (IOException e) {
            e.printStackTrace();
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage()
    {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            Scanner scanner = new Scanner(System.in);
            while(socket.isConnected())
            {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(username+": "+messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }

    }
    public void sendMessageFromUI(String messageToSend) {
        try {
            bufferedWriter.write(username + ": " + messageToSend);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

//    public <T extends TextArea, S extends TextField> void sendMessagefromUI(TextArea chatMessagesTextArea, TextField messageTextField)
//    {
//        try {
////            bufferedWriter.write(username);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
////            Scanner scanner = new Scanner(System.in);
//            while(socket.isConnected())
//            {
//                String messageToSend = messageTextField.getText();
//                bufferedWriter.write(username+": "+messageToSend);
//                bufferedWriter.newLine();
//                bufferedWriter.flush();
//                if (!messageToSend.isEmpty()) {
//                    chatMessagesTextArea.appendText("You: " + messageToSend + "\n");
//                    // You can add code here to send the message to other users in the chat room
//
//                    messageTextField.clear();
//                }
//            }
//
//
//        } catch (IOException e) {
//            closeEverything(socket, bufferedReader, bufferedWriter);
//        }
//
//    }

//    public void listenForMessage()
//    {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String msgFromGroupChat;
//                while(socket.isConnected())
//                {
//                    try {
//                        msgFromGroupChat = bufferedReader.readLine();
//                        System.out.println(msgFromGroupChat);
//                    } catch (IOException e) {
//                        closeEverything(socket,bufferedReader,bufferedWriter);
//                    }
//
//                }
//
//            }
//        }).start();
//    }
    public void listenForMessage() {
        new Thread(() -> {
            String msgFromGroupChat;
            while (socket.isConnected()) {
                try {
                    msgFromGroupChat = bufferedReader.readLine();
                    if (msgFromGroupChat != null) {
                        javafxClient.displayMessage(msgFromGroupChat); // Display the received message in the UI
                    }
                } catch (IOException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                }
            }
        }).start();
    }


    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter)
    {

        try {
            if(bufferedReader != null)
            {
                bufferedReader.close();
            }
            if(bufferedWriter != null)
            {
                bufferedWriter.close();
            }
            if(socket != null)
            {
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username for gc: ");
        String username = scanner.nextLine();
        Socket socket1 = new Socket("localhost",1234);
        Client client = new Client(socket1, username, new JavaFXClient());
        client.listenForMessage();
        client.sendMessage();
    }

}

