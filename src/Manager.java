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
        System.out.println(listCourses());
        Course c = new Course("CS180");
        addCourse(c);
        System.out.println(listCourses());
        Course m = new Course("Manas");
        addCourse(m);
        System.out.println(listCourses());
        Course l = new Course("lol");
        addCourse(l);
        System.out.println(listCourses());
        getCourse(1);
        editCourse(l, c);
    }

    public static Course getCourse(int index) {
        try {
            Course temp = new Course("temp");
            ArrayList<String> courses = readFile("courses.txt");
            String filename = courses.get(index) + ".txt";
            System.out.println(Arrays.deepToString(readFile(filename).toArray()));
            return temp;
        } catch (Exception e) {
            System.out.println("There was a problem finding your course!");
            return null;
        }
    }
    public static void addCourse(Course c) {
        try {
            String filename = c.getName() + ".txt";
            writeChangesToFile(c.toString(), filename, false);
            writeChangesToFile(c.getName(), "courses.txt", true);
        } catch (Exception e) {
            System.out.println("There was a problem creating this course, try again!");
        }
    }

    public static void editCourse(Course current, Course updated) {
        try {
            if (current.getName().equals(updated.getName())) {
                String update = updated.toString();
                writeChangesToFile(update, updated.getName() + ".txt", false);
            } else {
                File tempFile = new File(current.getName() + ".txt");
                //checks if the current course has a file
                if (tempFile.exists()) {
                    tempFile.delete();
                    String update = updated.toString();
                    //creates a file with the updated course
                    writeChangesToFile(update, (updated.getName() + ".txt"), false);
                    //updates the courses.txt file since the course name was changed
                    ArrayList<String> courses = readFile("courses.txt");
                    courses.set(courses.indexOf(current.getName()), updated.getName());
                    String updatedCourses = "";
                    for (int i = 0; i < courses.size(); i++) {
                        updatedCourses += courses.get(i) + "\n";
                    }
                    writeChangesToFile(updatedCourses, "courses.txt", false);
                } else {
                    System.out.println("The course you provided was not found!");
                }
            }
        } catch (Exception e) {
            System.out.println("There was a problem editing your course, try again!");
        }

    }

    public static String listCourses() {
        try {
            ArrayList<String> courseInfo = readFile("courses.txt");
            if (courseInfo.size() == 0) {
                return ("There are currently no courses!");
            }
            String courseList = "Courses:" + "\n";
            for (int i = 0; i < courseInfo.size(); i++) {
                courseList += (i + 1) + ". " + courseInfo.get(i) + "\n";
            }
            courseList = courseList.substring(0, courseList.length() - 1);
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

    public static ArrayList<String> readFile(String fileName) throws FileNotFoundException {
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

    public static void writeChangesToFile(String info, String filename, boolean append) throws FileNotFoundException {
        File f = new File(filename);
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(f, append))) {
            pw.println(info);
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }
}
