import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class which represents an account a user uses
 */
public class Account {

    private final String username;
    private final String password;
    private final boolean isStudent;

    public Account(String username, String password, boolean isStudent){

        this.username = username;
        this.password = password;
        this.isStudent = isStudent;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isStudent() {
        return isStudent;
    }
}
