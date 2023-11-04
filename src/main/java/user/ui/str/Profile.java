package user.ui.str;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Profile implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public String profileName;
    public String profileUserName;
    public List<Folder> folders;

    public Profile(String profileName){
        this.profileName = profileName;
        this.folders = new ArrayList<>();
    }
    public void addFolder(Folder folder) {
        folders.add(folder);
    }
    public void rmFolder(Folder folder) {
        folders.remove(folder);
    }

    public String getName() {
        return profileName;
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        // Custom serialization code for Profile
        out.defaultWriteObject();
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // Custom deserialization code for Profile
        in.defaultReadObject();
    }
}
