package user.ui.str;

import java.io.Serializable;

public class ChatButton implements Serializable {
    public String name;

    public ChatButton(String name) {
        this.name = name;
    }
}