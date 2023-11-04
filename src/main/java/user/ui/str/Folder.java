package user.ui.str;

import java.io.*;
import java.util.*;

public class Folder implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public String name;
    public List<ChatButton> chats;

    public Folder(String name) {
        this.name = name;
        this.chats = new ArrayList<>();
    }

    public void addChat(ChatButton chat) {
        chats.add(chat);
    }
    public void rmChat(ChatButton chat) {
        chats.remove(chat);
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }
}