package socks;

import java.io.*;
import java.net.*;

public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket)
    {
        this.serverSocket=serverSocket;

    }
    public void  startServer()
    {
        try {
            while(!serverSocket.isClosed())
            {
                Socket socket = serverSocket.accept();
                System.out.println("a new client connected");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }
        catch (IOException e)
        {
            closeServerSocket(); //might have to print stack trace
        }
    }
    public void closeServerSocket()
    {
        try {
            if(serverSocket != null)
            {
                serverSocket.close();
            }
        }
        catch (IOException e)
        {
            System.out.println("Someone Left?");
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}

