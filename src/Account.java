/**
 * A class which represents an account a user uses
 */
public class Account {

    private String username;
    private String password;
    private boolean isStudent;

    public Account(String username, String password, boolean isStudent){

        this.username = username;
        this.password = password;
        this.isStudent = isStudent;

    }

    public Account(){

        this.username = null;
        this.password = null;
        this.isStudent = false;

    }

    public void setUsername(String s){
        this.username = s;
    }

    public void setPassword(String s){
        this.password = s;
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
