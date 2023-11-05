package socks;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Server chatServer;
    private PrintWriter out;

    public ClientHandler(Server chatServer, Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.chatServer = chatServer;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String message;
            while ((message = in.readLine()) != null) {
                chatServer.broadcastMessage(message, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            chatServer.removeClient(this);
        }
    }
}
