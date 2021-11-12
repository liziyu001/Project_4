import java.io.*;
import java.util.*;

public class Manager {

    public void editCourse(Course current, Course updated) {
        try {
            ArrayList<String> courses = readFile("courses.txt");
            int startIndex = -1;
            int endIndex = -1;
            boolean found = false;
            for (int i = 0; i < courses.size(); i++) {
                String courseName = "CourseName " + current.getName();
                if (courses.get(i).equals(courseName)) {
                    startIndex = i;
                    found = true;
                }
                if (courses.get(i).contains("CourseName: ") && found) {
                    endIndex = i - 1;
                    break;
                }
            }
            String update = updated.toString();
            String temp = Arrays.deepToString(courses.toArray());
            temp = temp.substring(startIndex, endIndex);
            update += temp;
            writeChangesToFile(update, "courses.txt", false);
        } catch (Exception e) {
            System.out.println("There was a problem editing your course, try again!");
        }

    }

    public boolean checkAvailability(String username) {
        try {
            ArrayList<String> accounts = readFile("accounts.txt");
            for (int i = 0; i < accounts.size(); i++) {
                String[] info = accounts.get(i).split(",");
                Account temp = new Account(info[0], info[1], Boolean.parseBoolean(info[2]));
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
    public String listCourses() {
        try {
            ArrayList<String> courseInfo = readFile("courses.txt");
            if (courseInfo.size() == 0) {
                return ("There are currently no courses!");
            }
            String courseList = "Courses: ";
            for (int i = 0; i < courseInfo.size(); i++) {
                String temp = courseInfo.get(i);
                if (temp.contains("CourseName: ")) {
                    temp.replace("CourseName: ", "");
                    courseList += temp + ", ";
                }
            }
            courseList = courseList.substring(0, courseList.length() - 1);
            return courseList;
        } catch (Exception e) {
            return "There was a problem listing the courses, try again!";
        }
    }

    public Account login(String username, String password) {
        try {
            ArrayList<String> accounts = readFile("accounts.txt");
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
            ArrayList<String> accounts = readFile("accounts.txt");
            for (String account : accounts) {
                String[] info = account.split(",");
                Account temp = new Account(info[0], info[1], Boolean.parseBoolean(info[2]));
                if (temp.equals(a)) {
                    accounts.remove(account);
                    break;
                }
            }
            writeChangesToFile(Arrays.deepToString(accounts.toArray()), "accounts.txt", false);

        } catch (Exception e) {
            System.out.println("There was a problem deleting this account, try again!");
        }

    }

    public void editAccount(Account current, Account updated) {
        try {
            ArrayList<String> accounts = readFile("accounts.txt");
            for (int i = 0; i < accounts.size(); i++) {
                String[] info = accounts.get(i).split(",");
                Account temp = new Account(info[0], info[1], Boolean.parseBoolean(info[2]));
                if (temp.equals(current)) {
                    accounts.set(i, updated.toString());
                }
            }
            writeChangesToFile(Arrays.deepToString(accounts.toArray()), "accounts.txt", false);

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
