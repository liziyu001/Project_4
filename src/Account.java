import java.util.Objects;

public class Account {
    private String username;
    private String password;
    private boolean isStudent;
    private static int id = 1;
    private int accountId;


    public Account(String username, String password, boolean isStudent) {
        this.username = username;
        this.password = password;
        this.isStudent = isStudent;
        this.accountId = id;
        id++;
    }

    public Account(Account a) {
        this.username = a.getUsername();
        this.password = a.getPassword();
        this.isStudent = a.isStudent();
        this.accountId = id;
        id++;
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

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) { this.accountId = accountId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return isStudent == account.isStudent && accountId == account.accountId
                && Objects.equals(username, account.username) && Objects.equals(password, account.password);
    }

    @Override
    public String toString() {
        return  accountId + ", " + username + ", " + password + ", " + isStudent;
    }
}
