import java.io.*;
import java.util.*;

public class Manager {
    public static void main(String[] args) {
        /*
        Account a = new Account("Manas", "password1", true);
        Account b = new Account("asda", "asd", false);
        addAccount(a);
        addAccount(b);
        Account c = new Account("Manas1", "password1", true);
        addAccount(c);
        deleteAccount(a);
        deleteAccount(c);
        editAccount(b, c);
        Account current = login("Manas","password1");
        addAccount(current);
        boolean bool = checkAvailability("Manas1");
        System.out.println(bool);
        bool = checkAvailability("anant");
        System.out.println(bool);
        Account acc = new Account("anant", ";0", true);
        addAccount(acc);
        bool = checkAvailability("anant");
        System.out.println(bool);
         */
        //System.out.println(listCourses());
    }
    public void addCourse(Course c) {
        try {
            writeChangesToFile(c.toString(), (c.getName() + ".txt"), false);
        } catch (Exception e) {
            System.out.println("There was a problem creating this course, try again!");
        }
    }

    public void editCourse(Course current, Course updated) {
        try {
            File tempFile = new File(current.getName() + ".txt");
            if (tempFile.exists()) {
                String update = updated.toString();
                writeChangesToFile(update, (updated.getName() + ".txt"), false);
            } else {
                System.out.println("The course you provided was not found!");
            }
        } catch (Exception e) {
            System.out.println("There was a problem editing your course, try again!");
        }

    }

    public String listCourses() {
        try {
            int courseCount = 1;
            ArrayList<String> courseInfo = readFile("sample_courses.txt");
            if (courseInfo.size() == 0) {
                return ("There are currently no courses!");
            }
            String courseList = "Courses:" + "\n";
            for (int i = 0; i < courseInfo.size(); i++) {
                String temp = courseInfo.get(i);
                if (temp.contains("CourseName: ")) {
                    courseList += courseCount + ". " + temp + "\n";
                    courseCount++;
                }
            }
            courseList = courseList.substring(0, courseList.length() - 1);
            courseList = courseList.replace("CourseName: ","");
            return courseList;
        } catch (Exception e) {
            return "There was a problem listing the courses, try again!";
        }
    }

    public boolean checkAvailability(String username) {
        try {
            ArrayList<String> accounts = readFile("accounts.txt");
            for (int i = 0; i < accounts.size(); i++) {
                String[] info = accounts.get(i).split(",");
                Account temp = new Account(info[1].trim(), info[2].trim(), Boolean.parseBoolean(info[3].trim()));
                if (temp.getUsername().equals(username)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("There was a problem creating your account, try again!");
            return false;
        }
    }

    public Account login(String username, String password) {
        try {
            ArrayList<String> accounts = readFile("accounts.txt");
            for (int i = 0; i < accounts.size(); i++) {
                String[] info = accounts.get(i).split(",");
                Account temp = new Account(info[1].trim(), info[2].trim(), Boolean.parseBoolean(info[3].trim()));
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
            boolean found = false;
            ArrayList<String> accounts = readFile("accounts.txt");
            for (String account : accounts) {
                String[] info = account.split(",");
                Account temp = new Account(info[1].trim(), info[2].trim(), Boolean.parseBoolean(info[3].trim()));
                temp.setAccountId(a.getAccountId());
                if (temp.equals(a)) {
                    accounts.remove(account);
                    found = true;
                    break;
                }
            }
            if (found) {
                String finalString = "";
                for (int i = 0; i < accounts.size(); i++) {
                    if (i == accounts.size() - 1) {
                        finalString += accounts.get(i);
                    } else {
                        finalString += accounts.get(i) + "\n";
                    }
                }
                writeChangesToFile(finalString, "accounts.txt", false);
            } else {
                System.out.println("The provided account was not found!");
            }
        } catch (Exception e) {
            System.out.println("There was a problem deleting this account, try again!");
        }

    }

    public void editAccount(Account current, Account updated) {
        try {
            boolean found = false;
            ArrayList<String> accounts = readFile("accounts.txt");
            for (int i = 0; i < accounts.size(); i++) {
                String[] info = accounts.get(i).split(",");
                Account temp = new Account(info[1].trim(), info[2].trim(), Boolean.parseBoolean(info[3].trim()));
                temp.setAccountId(current.getAccountId());
                if (temp.equals(current)) {
                    updated.setAccountId(current.getAccountId());
                    accounts.set(i, updated.toString());
                    found = true;
                    break;
                }
            }
            if (found) {
                String finalString = "";
                for (int i = 0; i < accounts.size(); i++) {
                    if (i == accounts.size() - 1) {
                        finalString += accounts.get(i);
                    } else {
                        finalString += accounts.get(i) + "\n";
                    }
                }
                writeChangesToFile(finalString, "accounts.txt", false);
            } else {
                System.out.println("The account was NOT found and edited!");
            }


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
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(f, append))) {
            pw.println(info);
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }
}
