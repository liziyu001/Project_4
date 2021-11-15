import java.io.*;
import java.util.*;

/**
 * The Manager class has methods that deal with all the reading and writing to files.
 *
 * @author Manas Srivastava, Ziyu Li, Leo Pan, Ram Laxminarayan, Miras Abdishev
 * @version November 15, 2021
 */

public class Manager {
    //called when trying to validate a new user/a user changing their username
    public boolean checkDeletedAccounts(String username) {
        try {
            ArrayList<String> names = readFile("deleted_accounts.txt");
            for (int i = 0; i < names.size(); i++) {
                if (username.equals(names.get(i))) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("There was a problem checking if the account name already existed!");
            return false;
        }
    }

    //reads a file and creates an arraylist of Submission objects
    public ArrayList<Submission> convertSubmissions(String coursename, String quizname) {
        try {
            ArrayList<String> lines = readFile("accessible_quizzes.txt");
            String filename = "";
            boolean found = false;
            ArrayList<Submission> submissions = new ArrayList<>();
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(coursename + "_" + quizname)) {
                    filename = lines.get(i);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("The quiz submission file wasn't under accessible_quizzes!");
                return null;
            } else {
                lines = readFile(filename);
                for (int i = 0; i < lines.size(); i++) {
                    if (lines.get(i).startsWith("Submission:")) {
                        Submission s = new Submission();
                        String[] ans;
                        String ansString = "";
                        String[] points;
                        String pointString = "";
                        String timeStamp = "";
                        s.setUsername(lines.get(i + 1).substring(10));
                        s.setGraded(Boolean.parseBoolean(lines.get(i + 2).substring(8)));
                        ansString = lines.get(i + 3).substring(9).replace("[", "").replace("]", "");
                        if (ansString.contains(",")) {
                            ans = ansString.split(",");
                            int[] intAns = new int[ans.length];
                            for (int j = 0; j < ans.length; j++) {
                                intAns[j] = Integer.parseInt(ans[j].trim());
                            }
                            s.setAnswers(intAns);
                        } else {
                            ans = new String[1];
                            ans[0] = ansString;
                            int[] intAns = new int[1];
                            intAns[0] = Integer.parseInt(ans[0].trim());
                            s.setAnswers(intAns);
                        }
                        pointString = lines.get(i + 4).substring(8).replace("[", "").replace("]", "");
                        if (pointString.contains(",")) {
                            points = pointString.split(",");
                            int[] intPoints = new int[points.length];
                            for (int j = 0; j < points.length; j++) {
                                intPoints[j] = Integer.parseInt(points[j].trim());
                            }
                            s.setSubGrades(intPoints);
                        } else {
                            points = new String[1];
                            points[0] = pointString;
                            int[] intPoints = new int[1];
                            intPoints[0] = Integer.parseInt(points[0].trim());
                            s.setSubGrades(intPoints);
                        }
                        s.setTotalGrades(Integer.parseInt(lines.get(i + 5).substring(14)));
                        timeStamp = lines.get(i + 6).substring(16);
                        s.setTimestamp(timeStamp);
                        submissions.add(s);
                    }

                }
                return submissions;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem converting the submission!");
            return null;
        }
    }

    //updates the file containing a list of quizzes that have been graded(not used)
    public void updateGradedQuizzes(String filename, Submission submission) {
        try {
            ;
            ArrayList<String> lines = readFile(filename);
            String finalString = "";
            boolean done = false;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(("Username: ") + submission.getUsername())) {
                    if (lines.get(i + 5).startsWith(("Time Submitted: " + submission.getTimestamp()))) {
                        lines.set(i + 1, "Graded: true");
                        lines.set(i + 3, "Points: " + Arrays.toString(submission.getSubGrades()));
                        lines.set(i + 4, "Total Points: " + submission.getTotalGrades());
                        done = true;
                        break;
                    }
                }
            }
            if (!done) {
                System.out.println("The submission wasn't found!");
            } else {
                for (int i = 0; i < lines.size(); i++) {
                    finalString += lines.get(i) + "\n";
                }
                finalString = finalString.substring(0, finalString.length() - 1);
                writeChangesToFile(finalString, filename, false);
                System.out.println("The submission was updated in the quiz submission file!");
            }
        } catch (Exception e) {
            System.out.println("There was a problem updating the graded quizzes to a file!!");
        }

    }

    //given a coursename and quizname, searches the accessible_quizzes.txt file for the given quiz.
    public String searchAccessibleQuizzes(String coursename, String quizname) {
        try {
            boolean found = false;
            String filename = "";
            ArrayList<String> lines = readFile("accessible_quizzes.txt");
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(coursename + "_" + quizname + "_")) {
                    filename = lines.get(i);
                    return filename;
                }
            }
            if (!found) {
                System.out.println("The given quiz wasn't found in the accessible quizzes!");
                return null;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("There was a problem deleting the quiz from the accessible quizzes!");
            return null;
        }
    }

    //deletes a quiz from the accessible_quizzes.txt file
    public void deleteAccessibleQuiz(String coursename, String quizname) {
        try {
            boolean found = false;
            ArrayList<String> lines = readFile("accessible_quizzes.txt");
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(coursename + "_" + quizname + "_")) {
                    lines.remove(i);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("The given quiz wasn't found in the accessible quizzes!");
            }
            String finalString = "";
            for (int i = 0; i < lines.size(); i++) {
                finalString += lines.get(i) + "\n";
            }
            finalString = finalString.substring(0, finalString.length() - 1);
            writeChangesToFile(finalString, "accessible_quizzes.txt", false);
        } catch (Exception e) {
            System.out.println("There was a problem deleting the quiz from the accessible quizzes!");
        }
    }

    //updates the accessible_quizzes.txt file
    public void updateAccessibleQuizzes(String coursename, String quizname, String timestamp) {
        try {
            boolean found = false;
            String filename = coursename + "_" + quizname + "_" + timestamp + ".txt";
            ArrayList<String> lines = readFile("accessible_quizzes.txt");
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(coursename + "_" + quizname + "_")) {
                    found = true;
                    System.out.println("There was a problem updating the accessible quizzes");
                    return;
                }
            }
            lines.add(filename);
            String finalString = "";
            for (int i = 0; i < lines.size(); i++) {
                finalString += lines.get(i) + "\n";
            }
            finalString = finalString.substring(0, finalString.length() - 1);
            writeChangesToFile(finalString, "accessible_quizzes.txt", false);
        } catch (Exception e) {
            System.out.println("There was a problem updating the accessible quizzes!");
        }
    }

    //writes the student's submission to the correct file
    public void submit(String coursename, String quizname, Submission submission) {
        try {
            String filename;
            ArrayList<String> lines = readFile("accessible_quizzes.txt");
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(coursename + "_" + quizname)) {
                    filename = lines.get(i);
                    writeChangesToFile(submission.toString(), filename, true);
                    return;
                }
            }
            System.out.println("The quiz you tried to submit to doesn't exist!");
        } catch (Exception e) {
            System.out.println("There was a problem creating this course, try again!");
        }
    }

    //creates a quiz file given its information
    public void createQuizFile(String coursename, Quiz q, String timestamp) {
        try {
            writeChangesToFile(q.toString(), coursename + "_" + q.getName() + "_" + timestamp + ".txt", false);
        } catch (Exception e) {
            System.out.println("There was a problem creating this quiz submission file!");
        }

    }

    //returns a Quiz that was parsed from a file
    public Quiz addQuizFromFile(String filename) {
        try {
            String prompt = "";
            int correctAnswer;
            ArrayList<String> choices = new ArrayList<>();
            ArrayList<String> lines = readFile(filename + ".txt");
            ArrayList<Question> questions = new ArrayList<>();
            boolean rand = Boolean.parseBoolean(lines.get(0).replace("IsRandom: ", ""));
            String quizname = lines.get(1).substring(14);
            for (int i = 2; i < lines.size(); i++) {
                if (lines.get(i).startsWith("Correct Answer: ")) {
                    correctAnswer = Integer.parseInt(lines.get(i).substring(16));
                    Question temp = new Question(prompt, choices, correctAnswer);
                    questions.add(temp);
                    prompt = "";
                    choices = new ArrayList<>();
                } else if (lines.get(i).startsWith("Prompt of Question: ")) {
                    prompt = lines.get(i).substring(20);
                } else if (lines.get(i).startsWith("IsRandom: ")) {
                    System.out.println("ONLY 1 QUIZ PER FILE");
                    return null;
                } else {
                    choices.add(lines.get(i));
                }
            }
            Quiz q = new Quiz(quizname, questions, rand);
            return q;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem converting this file to a quiz!");
            return null;
        }

    }

    //returns a Course from a file with the given coursename
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

    //helper method for convertCourse, returns a Quiz from a file
    public Quiz convertQuiz(String coursename, String quizname) {
        try {
            boolean quizFound = false;
            String prompt = "";
            int correctAnswer;
            boolean rand = false;
            ArrayList<String> choices = new ArrayList<>();
            ArrayList<String> lines = readFile(coursename + ".txt");
            ArrayList<Question> questions = new ArrayList<>();
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).equals("Name of Quiz: " + quizname)) {
                    rand = Boolean.parseBoolean(lines.get(i - 1).replace("IsRandom: ", ""));
                    quizFound = true;
                    i++;
                }
                if (quizFound) {
                    if (lines.get(i).startsWith("IsRandom: ")) {
                        break;
                    } else if (lines.get(i).startsWith("Correct Answer: ")) {
                        correctAnswer = Integer.parseInt(lines.get(i).substring(16));
                        Question temp = new Question(prompt, choices, correctAnswer);
                        questions.add(temp);
                        prompt = "";
                        choices = new ArrayList<>();
                    } else if (lines.get(i).startsWith("Prompt of Question: ")) {
                        prompt = lines.get(i).substring(20);
                    } else {
                        choices.add(lines.get(i));
                    }
                }
            }
            Quiz q = new Quiz(quizname, questions, rand);
            return q;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem converting this string to a quiz!");
            return null;
        }

    }

    //helper method to list the quizzes
    public String getQuizName(int index, String quizzes) {
        String[] lines = quizzes.split("\n");
        return lines[index].substring(16 + String.valueOf(index).length());

    }

    //helper method to list the courses
    public String getCourseName(int index) {
        try {
            ArrayList<String> courses = readFile("courses.txt");
            return courses.get(index);
        } catch (Exception e) {
            System.out.println("There was a problem finding your course!");
            return null;
        }
    }

    //adds the coursename to courses.txt and creates a file with the course contents
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

    //checks if the coursename doesn't already exist in courses.txt
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

    //helper method for editing quizzes and other course contents
    public void editCourse(Course current, Course updated) {
        try {
            if (current.getName().equals(updated.getName())) {
                String update = updated.toString();
                writeChangesToFile(update, updated.getName() + ".txt", false);
            } else {
                System.out.println("The course you provided was not found!");
            }
        } catch (Exception e) {
            System.out.println("There was a problem editing your course, try again!");
        }

    }

    //reads the courses.txt file and lists the names of the courses
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

    //reads quizzes from a given course and lists them
    public String listQuizzes(String coursename) {
        try {
            ArrayList<String> courseInfo = readFile(coursename + ".txt");
            int quizNumber = 1;
            //System.out.println(courseInfo.size());
            if (courseInfo.size() == 2) {
                return ("There are currently no quizzes!");
            }
            String quizList = "Quizzes:" + "\n";
            for (int i = 0; i < courseInfo.size(); i++) {
                if (courseInfo.get(i).startsWith("Name of Quiz: ")) {
                    quizList += (quizNumber + ". ") + courseInfo.get(i) + "\n";
                    quizNumber++;
                }
            }
            quizList = quizList.substring(0, quizList.length() - 1);
            return quizList;
        } catch (Exception e) {
            return "There was a problem listing the quizzes, try again!";
        }
    }

    //checks if the given username doesn't exist in the accounts.txt file
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

    //checks if the given credentials are valid
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

    //writes the account to the accounts.txt file
    public void addAccount(Account a) {
        try {
            writeChangesToFile(a.toString(), "accounts.txt", true);
        } catch (Exception e) {
            System.out.println("There was a problem creating this account, try again!");
        }

    }

    //deletes the account from the accounts.txt file and adds it to deleted_accounts.txt
    public void deleteAccount(int accountId) {
        try {
            boolean found = false;
            ArrayList<String> accounts = readFile("accounts.txt");
            String username = null;
            for (String account : accounts) {
                String[] info = account.split(",");
                int id = Integer.parseInt(info[0]);
                //Account temp = new Account(info[1].trim(), info[2].trim(), Boolean.parseBoolean(info[3].trim()));
                //temp.setAccountId(a.getAccountId());
                if (id == accountId) {
                    accounts.remove(account);
                    username = info[1].trim();
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
                writeChangesToFile(username, "deleted_accounts.txt", true);
            } else {
                System.out.println("The provided account was not found!");
            }
        } catch (Exception e) {
            System.out.println("There was a problem deleting this account, try again!");
        }

    }

    //writes the changed account to the accounts.txt file
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

    //reads a file's contents
    public ArrayList<String> readFile(String fileName) {
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
            System.out.println("There was a problem reading from this file!");
            return null;
        }
    }

    //writes the contents to a file
    public void writeChangesToFile(String info, String filename, boolean append) {
        File f = new File(filename);
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(f, append))) {
            pw.println(info);
        } catch (IOException e) {
            System.out.println("There was an error writing changes to the file!");
        }
    }
}
