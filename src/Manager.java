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
        getCourseName(1);
        Course a = new Course("CS193");
        addCourse(a);


         */
        // might not need
        //editCourse(l, c);

    }
    /*
    public void submitQuiz(String coursename, String quizname, Submission submission) {
        try {
            String filename = c.getName() + ".txt";
            writeChangesToFile(c.toString(), filename, false);
            ArrayList<String> courses = readFile("courses.txt");
            for (int i = 0; i < courses.size(); i++) {
                if (courses.get(i).isBlank()) {
                    courses.remove(i);
                }
            }
            courses.add(c.getName());
            String courseString = "";
            for (int i = 0; i < courses.size(); i++) {
                if (i == courses.size() - 1) {
                    courseString += courses.get(i);
                } else {
                    courseString += courses.get(i) + "\n";
                }

            }
            writeChangesToFile(courseString, coursename + "_" + quizname + ".txt", true);
        } catch (Exception e) {
            System.out.println("There was a problem creating this course, try again!");
        }
    }


     */
    public Course convertCourse(String coursename) {
        try {
            ArrayList<String> lines = readFile(coursename + ".txt");
            ArrayList<String> quizNames = new ArrayList<>();
            ArrayList<Quiz> quizzes = new ArrayList<>();
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith("Name of Quiz: ")) {
                    quizNames.add(lines.get(i).substring(14));
                }
            }
            for (int i = 0; i < quizNames.size(); i++) {
                quizzes.add(convertQuiz(coursename, quizNames.get(i)));
            }
            Course c = new Course(coursename, quizzes);
            return c;
        } catch (Exception e) {
            System.out.println("There was a problem converting this string into a course!");
            return null;
        }
    }
    public Quiz convertQuiz(String coursename, String quizname) {
        try {
            boolean quizFound = false;
            boolean questionFound = false;
            String prompt = "";
            int correctAnswer;
            ArrayList<String> choices = new ArrayList<>();
            ArrayList<String> lines = readFile(coursename + ".txt");
            ArrayList<Question> questions = new ArrayList<>();
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).equals("Name of Quiz: " + quizname)) {
                    quizFound = true;
                }
                if (quizFound) {
                    if (lines.get(i).startsWith("Correct Answer: ")) {
                        correctAnswer = Integer.parseInt(lines.get(i).substring(16));
                        Question temp = new Question(prompt, choices, correctAnswer);
                        questions.add(temp);
                        questionFound = false;
                        prompt = "";
                        choices = new ArrayList<>();
                    } else if (lines.get(i).startsWith("Prompt of Question: ")){
                        questionFound = true;
                        prompt = lines.get(i).substring(20);
                    } else {
                        choices.add(lines.get(i));
                    }
                }
            }
            Quiz q = new Quiz(quizname, questions);
            return q;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem converting this string to a quiz!");
            return null;
        }

    }

    public String getQuizName(int index, String quizzes) {
        String[] lines = quizzes.split("\n");
        return lines[index].substring(16 + String.valueOf(index).length());

    }
    public String getCourseName(int index) {
        try {
            ArrayList<String> courses = readFile("courses.txt");
            return courses.get(index);
        } catch (Exception e) {
            System.out.println("There was a problem finding your course!");
            return null;
        }
    }

    public void addCourse(Course c) {
        try {
            String filename = c.getName() + ".txt";
            writeChangesToFile(c.toString(), filename, false);
            ArrayList<String> courses = readFile("courses.txt");
            for (int i = 0; i < courses.size(); i++) {
                if (courses.get(i).isBlank()) {
                    courses.remove(i);
                }
            }
            courses.add(c.getName());
            String courseString = "";
            for (int i = 0; i < courses.size(); i++) {
                if (i == courses.size() - 1) {
                    courseString += courses.get(i);
                } else {
                    courseString += courses.get(i) + "\n";
                }

            }
            writeChangesToFile(courseString, "courses.txt", false);
        } catch (Exception e) {
            System.out.println("There was a problem creating this course, try again!");
        }
    }

    public boolean checkCourseAvailability(Course c) {
        try {
            ArrayList<String> courses = readFile("courses.txt");
            for (int i = 0; i < courses.size(); i++) {
                if (courses.get(i).equals(c.getName())) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("There was a problem checking the course availability, try again!");
            return false;
        }
    }

    public void editCourse(Course current, Course updated) {
        try {
            if (current.getName().equals(updated.getName())) {
                String update = updated.toString();
                writeChangesToFile(update, updated.getName() + ".txt", false);
            } else {
                System.out.println("The course you provided was not found!");
            }
            /*else {
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

             */
        } catch (Exception e) {
            System.out.println("There was a problem editing your course, try again!");
        }

    }

    public String listCourses() {
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

    public String listQuizzes(String coursename) {
        try {
            ArrayList<String> courseInfo = readFile(coursename + ".txt");
            int quizNumber = 0;
            //System.out.println(courseInfo.size());
            if (courseInfo.size() == 2) {
                return ("There are currently no quizzes!");
            }
            String quizList = "Quizzes:" + "\n";
            for (int i = 0; i < courseInfo.size(); i++) {
                if (courseInfo.get(i).startsWith("Name of Quiz: "))
                quizList += (quizNumber + ". ") + courseInfo.get(i) + "\n";
                quizNumber++;
            }
            quizList = quizList.substring(0, quizList.length() - 1);
            return quizList;
        } catch (Exception e) {
            return "There was a problem listing the quizzes, try again!";
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

    public void deleteAccount(int accountId) {
        try {
            boolean found = false;
            ArrayList<String> accounts = readFile("accounts.txt");
            for (String account : accounts) {
                String[] info = account.split(",");
                int id = Integer.parseInt(info[0]);
                //Account temp = new Account(info[1].trim(), info[2].trim(), Boolean.parseBoolean(info[3].trim()));
                //temp.setAccountId(a.getAccountId());
                if (id == accountId) {
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

    public void editAccount(Account updated, int accountId) {
        try {
            boolean found = false;
            ArrayList<String> accounts = readFile("accounts.txt");
            for (int i = 0; i < accounts.size(); i++) {
                String[] info = accounts.get(i).split(",");
                int id = Integer.valueOf(info[0].trim());
                if (id == accountId) {
                    updated.setAccountId(accountId);
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
            System.out.println("There was a problem editing this account, try again!");
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
