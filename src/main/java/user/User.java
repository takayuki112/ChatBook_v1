package user;

import java.io.Serializable;

public class User implements Serializable {
    public String username;
    public String password;
    public String file_id;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
}
