
import java.util.ArrayList;
import java.util.Scanner;
// need to check ALL scanners to ensure no commas are inputted, will mess with file reading/writing otherwise
public class ManagementSystem {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to the System");
        String ans = "";
        Manager m = new Manager();
        Account currentAccount;

        login:
        while (true) {
            System.out.println("1. Create account");
            System.out.println("2. Login");
            String id;
            switch (s.nextLine()) {
                case "1":
                    while (true) {
                        System.out.println("Enter your User ID:");
                        id = s.nextLine();
                        if (id.contains(",")){
                            System.out.println("Invalid Username, no commas allowed!");
                            continue;
                        }
                        if (!m.checkAvailability(id)) {
                            System.out.println("Username already exists!");
                            continue;
                        }

                        break;
                    }
                    String pwd;
                    while (true) {
                        System.out.println("Enter your Password:");
                        pwd = s.nextLine();
                        if (pwd.contains(",")) {
                            System.out.println("Invalid Password, no commas allowed!");
                            continue;
                        }
                        break;
                    }
                    roleChoice:
                    while (true) {
                        System.out.println("Your role: 1. Teacher   2. Student");
                        switch (s.nextLine()) {
                            case "1":
                                currentAccount = new Teacher(id, pwd);
                                m.addAccount(currentAccount);
                                break;
                            case "2":
                                currentAccount = new Student(id, pwd);
                                m.addAccount(currentAccount);
                                break;
                            default:
                                System.out.println("Invalid choice");
                                continue roleChoice;
                        }
                        break;
                    }
                    break;
                case "2":
                    logininput:
                    while (true) {
                        System.out.println("Enter your User ID:");
                        id = s.nextLine();
                        System.out.println("Enter your Password:");
                        ans = s.nextLine();
                        if (id.contains(",") || ans.contains(",")){
                            System.out.println("Invalid input, no commas allowed!");
                            continue logininput;
                        }
                        currentAccount = m.login(id, ans);
                        if (currentAccount != null) {
                            System.out.println("Successfully logged in as " + currentAccount.getUsername());
                        } else {
                            System.out.println("Invalid ID or password");
                            continue login;
                        }
                        break;
                    }
                    break;
                default:
                    System.out.println("Invalid choice");
                    continue login;
            }
            break;
        }
        if (!currentAccount.isStudent()) {
            Teacher:
            while (true) {
                System.out.println("1. View Courses");
                System.out.println("2. Create a Course");
                System.out.println("3. Account Setting");
                switch (s.nextLine()){
                    case "2":
                        try {
                            // need to set s.nextLine() to a variable and input into these 2 functions
                            Course c = ((Teacher) currentAccount).createCourse(s);
                            if (m.checkCourseAvailability(c)) {
                                m.addCourse(c);
                            } else {
                                System.out.println("Course with the same name already exists!");
                                continue Teacher;
                            }
                        } catch (Exception e) {
                            System.out.println("Error occurs in creating the course");
                        }
                        break;
                    case "3":
                        AccountSetting:
                        while (true) {
                            System.out.println("1. Edit your username");
                            System.out.println("2. Edit your password");
                            System.out.println("3. Delete your account");
                            switch (s.nextLine()) {
                                case "1":
                                    Account temp = currentAccount;
                                    while (true) {
                                        System.out.println("Enter your new Username:");
                                        String tempUser = s.nextLine();
                                        if (tempUser.contains(",")) {
                                            System.out.println("Invalid Username, no commas allowed!");
                                            continue;
                                        } else {
                                            currentAccount.setUsername(tempUser);
                                            m.editAccount(temp, currentAccount);
                                            break;
                                        }
                                    }
                                case "2":
                                    temp = currentAccount;
                                    while (true) {
                                        System.out.println("Enter your new Password:");
                                        String tempPass = s.nextLine();
                                        if (tempPass.contains(",")) {
                                            System.out.println("Invalid Password, no commas allowed!");
                                            continue;
                                        } else {
                                            currentAccount.setPassword(tempPass);
                                            m.editAccount(temp, currentAccount);
                                            break;
                                        }
                                    }
                                case "3":
                                    // need to deal with files associated with the account? gonna be hard
                                    m.deleteAccount(currentAccount);
                                    System.out.println("Your account has been deleted!");
                                    break;
                                default :
                                    System.out.println("Invalid Choice");
                                    continue AccountSetting;
                            }
                            break;
                        }
                    case "1":
                        System.out.println("Select the course you want to view, ");
                        System.out.println(m.listCourses());
                        Course currentCourse = m.convertCourse(m.getCourseName((Integer.parseInt(s.nextLine()) - 1)));
                        System.out.println("Select the Quiz you want to proceed.");
                        System.out.println(m.listQuizzes(currentCourse.getName()));
                        //System.out.println(currentCourse.toString());
                        String quizzes = m.listQuizzes(currentCourse.getName());
                        Quiz currentQuiz = m.convertQuiz(currentCourse.getName(),
                                m.getQuizName(Integer.parseInt(s.nextLine()) - 1, quizzes));
                        System.out.println("1. Edit the Quiz");
                        System.out.println("2. Grade Submissions");
                        System.out.println("3. Delete this Quiz");
                        System.out.println("4. Upload Quiz from file");
                        System.out.println("0. Create a new Quiz");

                        switch (s.nextLine()) {
                            case "1":
                                Course temp = currentCourse;
                                currentCourse.editQuiz(currentQuiz.getName(), s);
                                m.editCourse(temp, currentCourse);
                            case "2" :
                                ArrayList<Submission> graded = ((Teacher)currentAccount).gradeSubmission(s, currentQuiz.getSubmissions());
                                currentQuiz.setSubmissions(graded);
                            case "3":
                                temp = currentCourse;
                                currentCourse.deleteQuiz(currentQuiz.getName());
                                m.editCourse(temp, currentCourse);
                            case "4":
                                System.out.println("Enter the filename: ");
                                String filename = s.nextLine();
                                System.out.println("Randomize Quiz? (Y/N)");
                                String randomize = s.nextLine();
                                if (randomize.equalsIgnoreCase("Y")) {
                                    ((Teacher)currentAccount).randomizeQuiz(filename);
                                }
                                else if (randomize.equalsIgnoreCase("N")) {    
                                    currentCourse.AddQuizFromFile(filename);
                                }
                            case "0":
                                System.out.println("Enter the name of the Quiz");
                                temp = currentCourse;
                                currentCourse.addQuiz(s.nextLine(), s);
                                m.editCourse(temp, currentCourse);
                        }
                }
            }
        } else {
            Student:
            while (true) {
                System.out.println("1. View Courses");
                System.out.println("2. Account Setting");
                switch (s.nextLine()) {
                    case "2":
                        studentAccountChoice:
                        while (true) {
                            System.out.println("1. Edit your username");
                            System.out.println("2. Edit your password");
                            System.out.println("3. Delete your account");
                            switch (s.nextLine()) {
                                case "1":
                                    Account temp = currentAccount;
                                    while (true) {
                                        System.out.println("Enter your new Username:");
                                        String tempUser = s.nextLine();
                                        if (tempUser.contains(",")) {
                                            System.out.println("Invalid Username, no commas allowed!");
                                            continue;
                                        } else {
                                            currentAccount.setUsername(tempUser);
                                            m.editAccount(temp, currentAccount);
                                            break;
                                        }
                                    }
                                case "2":
                                    temp = currentAccount;
                                    while (true) {
                                        System.out.println("Enter your new Password:");
                                        String tempPass = s.nextLine();
                                        if (tempPass.contains(",")) {
                                            System.out.println("Invalid Password, no commas allowed!");
                                            continue;
                                        } else {
                                            currentAccount.setPassword(tempPass);
                                            m.editAccount(temp, currentAccount);
                                            break;
                                        }
                                    }
                                case "3":
                                    m.deleteAccount(currentAccount);
                                    break;
                                default :
                                    System.out.println("Invalid Choice");
                                    continue studentAccountChoice;
                            }
                            break;
                        }
                        break;

                    case "1":
                        System.out.println("Select the course you want to view, ");
                        System.out.println(m.listCourses());
                        Course currentCourse = m.convertCourse(m.getCourseName((Integer.parseInt(s.nextLine()) - 1)));
                        System.out.println("Select the Quiz you want to take.");
                        //System.out.println(currentCourse.toString());
                        String quizzes = m.listQuizzes(currentCourse.getName());
                        Quiz currentQuiz = m.convertQuiz(currentCourse.getName(),
                                m.getQuizName(Integer.parseInt(s.nextLine()) - 1, quizzes));
                        System.out.println("0. View Gradings");
                        if (s.nextLine().equals("0")){
                            Submission[] results = ((Student)currentAccount).viewQuizResults((Student) currentAccount, currentCourse);
                            for (int i = 0; i < results.length; i++) {
                                System.out.println(results[i].toString());
                            }
                        } else {
                            Quiz currentQuiz = ((Quiz)currentCourse.getCourseQuiz().get(Integer.parseInt(s.nextLine()) - 1));
                            currentQuiz.addSubmission(((Student)currentAccount).takeQuiz(currentQuiz,s));
                        }
                        break;
                    default :
                        System.out.println("Invalid Choice");
                        continue Student;
                }
            }
        }

    }


}
