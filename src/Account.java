public class Account {
    private String username;
    private String password;
    private boolean isStudent;


    public Account(String username, String password, boolean isStudent) {
        this.username = username;
        this.password = password;
        this.isStudent = isStudent;
    }

    public Account(Account a) {
        this.username = a.getUsername();
        this.password = a.getPassword();
        this.isStudent = a.isStudent();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }
    public void editId(String username){
        this.username = username;
    }
    public void editPassword(String password){
        this.password = password;
    }

    @Override
    public String toString() {
        return  username + ", " + password + ", " + isStudent;
    }
}
