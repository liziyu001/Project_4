import java.io.*;
import java.util.*;

public class Manager {

    public Account login(String username, String password) {
        try {
            ArrayList<String> accounts = readFile("account.txt");
            for (int i = 0; i < accounts.size(); i++) {
                String[] info = accounts.get(i).split(",");
                Account temp = new Account(info[0], info[1], Boolean.parseBoolean(info[2]));
                if (temp.getUsername().equals(username) && temp.getPassword().equals(password)) {
                    return temp;
                }
            }
        } catch (Exception e) {
            System.out.println("There was a problem logging in, try again!");
        }
        return null;
    }
    public void addAccount(Account a) {
        try {
            writeChangesToFile(a.toString(), "accounts.txt", true);
        } catch (Exception e) {
            System.out.println("There was a problem creating this account, try again!");
        }

    }

    public void deleteAccount(Account a) {
        try {
            ArrayList<String> accounts = readFile("account.txt");
            for (String account : accounts) {
                String[] info = account.split(",");
                Account temp = new Account(info[0], info[1], Boolean.parseBoolean(info[2]));
                if (temp.equals(a)) {
                    accounts.remove(account);
                    break;
                }
            }
            writeChangesToFile(Arrays.deepToString(accounts.toArray()), "account.txt", false);

        } catch (Exception e) {
            System.out.println("There was a problem deleting this account, try again!");
        }

    }

    public void editAccount(Account current, Account updated) {
        try {
            ArrayList<String> accounts = readFile("account.txt");
            for (int i = 0; i < accounts.size(); i++) {
                String[] info = accounts.get(i).split(",");
                Account temp = new Account(info[0], info[1], Boolean.parseBoolean(info[2]));
                if (temp.equals(current)) {
                    accounts.set(i, updated.toString());
                }
            }
            writeChangesToFile(Arrays.deepToString(accounts.toArray()), "account.txt", false);

        } catch (Exception e) {
            System.out.println("There was a problem deleting this account, try again!");
        }

    }

    public ArrayList<String> readFile(String fileName) throws FileNotFoundException {
        ArrayList<String> tempString = new ArrayList<>();
        File f = new File(fileName);
        try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
            while (true) {
                String line = bfr.readLine();
                if (line == null)
                    break;
                tempString.add(line);
            }
            return tempString;
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }
    public void writeChangesToFile(String info, String filename, boolean append) throws FileNotFoundException {
        File f = new File(filename);
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(f), append)) {
            pw.println(info);
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }
}
