import java.io.*;

public class Manager {
    public void addAccount(Account a) {
        try {
            writeChangesToFile(a.toString(), "accounts.txt");
        } catch (Exception e) {
            System.out.println("There was a problem creating this account, try again!");
        }

    }

    public void deleteAccount(Account a) {

    }

    public void readFile() {

    }
    public static void writeChangesToFile(String info, String filename) throws FileNotFoundException {
        File f = new File(filename);
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(f), true)) {
            pw.println(info);
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }
}
