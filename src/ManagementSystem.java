
import javax.sound.midi.Soundbank;
import java.io.File;
import java.security.cert.PolicyQualifierInfo;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;
// need to check ALL scanners to ensure no commas are inputted, will mess with file reading/writing otherwise
public class ManagementSystem {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to the System");
        String ans = "";
        Manager m = new Manager();
        boolean quit = false;
        Account currentAccount;
        try {
            File a = new File("accounts.txt");
            File c = new File("courses.txt");
            c.createNewFile();
            a.createNewFile();
        } catch (Exception e) {
            System.out.println("There was a problem on startup");
        }

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
                                currentAccount = new Account(id, pwd, false);
                                m.addAccount(currentAccount);
                                break;
                            case "2":
                                currentAccount = new Account(id, pwd, true);
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
                            continue login;
                        }
                        currentAccount = m.login(id, ans);
                        if (currentAccount != null) {
                            System.out.println("Successfully logged in as " + currentAccount.getUsername());
                        } else {
                            System.out.println("Invalid ID or password");
                            continue;
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
            while (!quit) {
                System.out.println("1. View Courses");
                System.out.println("2. Create a Course");
                System.out.println("3. Account Setting");
                switch (s.nextLine()){
                    case "2":
                        try {
                            Course c = ( currentAccount).createCourse(s);
                            if (m.checkCourseAvailability(c)) {
                                m.addCourse(c);
                                System.out.println("Course " + c.getName() + " successfully created");
                                continue Teacher;
                            } else {
                                System.out.println("Failed! Course with the same name already exists!");
                                continue Teacher;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Error occurs in creating the course");
                        }
                        break;
                    case "3":
                        AccountSetting:
                        while (true) {
                            System.out.println("1. Edit your username");
                            System.out.println("2. Edit your password");
                            System.out.println("3. Delete your account");
                            System.out.println("0. Back");
                            switch (s.nextLine()) {
                                case "1":
                                    while (true) {
                                        System.out.println("Enter your new Username:");
                                        String tempUser = s.nextLine();
                                        if (!m.checkAvailability(tempUser)) {
                                            System.out.println("Username already exists!");
                                            continue AccountSetting;
                                        }
                                        if (tempUser.contains(",")) {
                                            System.out.println("Invalid Username, no commas allowed!");
                                        } else {
                                            currentAccount.setUsername(tempUser);
                                            m.editAccount(currentAccount, currentAccount.getAccountId());
                                            continue AccountSetting;
                                        }
                                    }
                                case "2":
                                    while (true) {
                                        System.out.println("Enter your new Password:");
                                        String tempPass = s.nextLine();
                                        if (tempPass.contains(",")) {
                                            System.out.println("Invalid Password, no commas allowed!");
                                        } else {
                                            currentAccount.setPassword(tempPass);
                                            m.editAccount(currentAccount, currentAccount.getAccountId());
                                            continue AccountSetting;
                                        }
                                    }
                                case "3":
                                    // need to deal with files associated with the account? gonna be hard
                                    m.deleteAccount(currentAccount.getAccountId());
                                    System.out.println("Your account has been deleted!");
                                    quit = true;
                                    break;
                                case "0":
                                    continue Teacher;
                                default :
                                    System.out.println("Invalid Choice");
                                    continue AccountSetting;
                            }
                            break;
                        }
                        if (quit) {
                            break;
                        }
                    case "1":
                        if (!m.listCourses().equals("There are currently no courses!")) {
                            Course currentCourse;
                            String choice;
                            while (true) {
                                System.out.println("Select the course you want to view");
                                System.out.println(m.listCourses());
                                System.out.println("0. Back");
                                try {
                                    choice = s.nextLine();
                                    if (choice.equals("0")) {
                                        continue Teacher;
                                    }
                                    currentCourse = m.convertCourse(m.getCourseName((Integer.parseInt(choice) - 1)));
                                    if (currentCourse != null) {
                                        break;
                                    } else {
                                        System.out.println("Please enter a valid index!");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Please enter a valid index!");
                                }
                            }
                            if (!m.listQuizzes(currentCourse.getName()).equals("There are currently no quizzes!")) {
                                Quiz currentQuiz;
                                while (true) {
                                    try {
                                        System.out.println("Select the Quiz you want to proceed.");
                                        System.out.println("-1. Create a new Quiz");
                                        System.out.println("-2. Create a new Quiz from file");
                                        System.out.println(m.listQuizzes(currentCourse.getName()));
                                        System.out.println("0. Back");
                                        String quizzes = m.listQuizzes(currentCourse.getName());
                                        choice = s.nextLine();
                                        if (choice.equals("0")) {
                                            continue Teacher;
                                        } else if (choice.equals("-1")) {
                                            System.out.println("Enter the name of the Quiz");
                                            Course temp = currentCourse;
                                            boolean create = currentCourse.addQuiz(s.nextLine(), s);
                                            if (create) {
                                                m.editCourse(temp, currentCourse);
                                                System.out.println("Quiz created");
                                            } else {
                                                System.out.println("Quiz not created!");
                                            }
                                            continue Teacher;
                                        } else if (choice.equals("-2")) {
                                            Course temp = currentCourse;
                                            System.out.println("Enter the filepath(just the filename)");
                                            String filename = s.nextLine();
                                            boolean create = currentCourse.addQuizFromFile(m.addQuizFromFile(filename));
                                            if (create) {
                                                m.editCourse(temp, currentCourse);
                                                System.out.println("Quiz created from file!");
                                            } else {
                                                System.out.println("Quiz not created from file!");
                                            }
                                        } else {
                                            currentQuiz = m.convertQuiz(currentCourse.getName(),
                                                    m.getQuizName(Integer.parseInt(choice), quizzes));
                                            System.out.println("1. Edit the Quiz");
                                            System.out.println("2. Grade Submissions");
                                            System.out.println("3. Delete this Quiz");
                                            System.out.println("4. Upload Quiz from file");
                                            System.out.println("5. View Quiz");
                                            System.out.println("0. Back");
                                            break;
                                        }

                                    } catch (Exception e) {
                                        //e.printStackTrace();
                                        System.out.println("Please enter a valid index!");
                                    }
                                }

                                switch (s.nextLine()) {
                                    case "1":
                                        Course temp = currentCourse;
                                        System.out.println("Current course: " +"\n" + currentCourse);
                                        System.out.println("Current Quiz: " + "\n" + currentQuiz);
                                        System.out.println("Quiz name: " + currentQuiz.getName());
                                        boolean create = currentCourse.editQuiz(currentQuiz.getName(), s);
                                        if (create) {
                                            m.editCourse(temp, currentCourse);
                                        }
                                        break;
                                    case "2" :
                                /*
                                ArrayList<Submission> graded = (currentAccount).gradeSubmission(s, currentQuiz.getSubmissions());
                                currentQuiz.setSubmissions(graded);

                                 */
                                        System.out.println("Choose submission you want to grade(number): ");
                                        currentQuiz.showAllSubmission();
                                        int sub = Integer.parseInt(s.nextLine());
                                        Submission tempSub = currentQuiz.getSubmissionById(sub-1);
                                        ArrayList<Integer>answers = currentAccount.gradeSubmission(s, tempSub);
                                        currentQuiz.EditSubmission(answers, tempSub.getAccount());
                                        System.out.println("Not implemented yet");
                                    case "3":
                                        temp = currentCourse;
                                        currentCourse.deleteQuiz(currentQuiz.getName());
                                        m.editCourse(temp, currentCourse);
                                        break;
                                    case "4":
                                        System.out.println("Enter the filename: ");
                                        String filename = s.nextLine();
                                        System.out.println("Randomize Quiz? (Y/N)");
                                        String randomize = s.nextLine();
                                        if (randomize.equalsIgnoreCase("Y")) {
                                            (currentAccount).randomizeQuiz(filename);
                                        }
                                        else if (randomize.equalsIgnoreCase("N")) {
                                            //not implemented yet
                                            //currentCourse.addQuizFromFile(filename);
                                        }
                                        break;
                                    case "0":
                                        continue Teacher;
                                    case "5":
                                        System.out.println(currentCourse);
                                }
                            } else {
                                System.out.println("There are currently no quizzes!");
                                System.out.println("0. Create a new Quiz");
                                if (!s.nextLine().equals("0")) {
                                    System.out.println("Invalid Input!");
                                    continue Teacher;
                                }
                                System.out.println("Enter the name of the Quiz");
                                Course temp = currentCourse;
                                boolean create = currentCourse.addQuiz(s.nextLine(), s);
                                if (create) {
                                    m.editCourse(temp, currentCourse);
                                    System.out.println("Quiz created");
                                } else {
                                    System.out.println("Quiz not created!");
                                }
                                continue Teacher;
                            }
                        } else {
                            System.out.println("There are currently no courses!");
                            continue Teacher;
                        }
                    default:
                        System.out.println("Invalid Choice!");
                }
            }
        } else {
            Student:
            while (!quit) {
                System.out.println("1. View Courses");
                System.out.println("2. Account Setting");
                switch (s.nextLine()) {
                    case "2":
                        studentAccountChoice:
                        while (true) {
                            System.out.println("1. Edit your username");
                            System.out.println("2. Edit your password");
                            System.out.println("3. Delete your account");
                            System.out.println("0. Back");
                            switch (s.nextLine()) {
                                case "1":
                                    while (true) {
                                        System.out.println("Enter your new Username:");
                                        String tempUser = s.nextLine();
                                        if (!m.checkAvailability(tempUser)) {
                                            System.out.println("Username already exists!");
                                            continue studentAccountChoice;
                                        }
                                        if (tempUser.contains(",")) {
                                            System.out.println("Invalid Username, no commas allowed!");
                                        } else {
                                            currentAccount.setUsername(tempUser);
                                            m.editAccount(currentAccount, currentAccount.getAccountId());
                                            continue studentAccountChoice;
                                        }
                                    }
                                case "2":
                                    while (true) {
                                        System.out.println("Enter your new Password:");
                                        String tempPass = s.nextLine();
                                        if (tempPass.contains(",")) {
                                            System.out.println("Invalid Password, no commas allowed!");
                                        } else {
                                            currentAccount.setPassword(tempPass);
                                            m.editAccount(currentAccount, currentAccount.getAccountId());
                                            continue studentAccountChoice;
                                        }
                                    }
                                case "3":
                                    // need to deal with files associated with the account? gonna be hard
                                    m.deleteAccount(currentAccount.getAccountId());
                                    System.out.println("Your account has been deleted!");
                                    quit = true;
                                    break;
                                case "0":
                                    continue Student;
                                default :
                                    System.out.println("Invalid Choice");
                                    continue studentAccountChoice;
                            }
                            break;
                        }
                        if (quit) {
                            break;
                        }
                        break;

                    case "1":
                        if (!m.listCourses().equals("There are currently no courses!")) {
                            Course currentCourse;
                            String choice;
                            while (true) {
                                System.out.println("Select the course you want to view");
                                System.out.println(m.listCourses());
                                System.out.println("0. Back");
                                try {
                                    choice = s.nextLine();
                                    if (choice.equals("0")) {
                                        continue Student;
                                    }
                                    currentCourse = m.convertCourse(m.getCourseName((Integer.parseInt(choice) - 1)));
                                    if (currentCourse != null) {
                                        break;
                                    } else {
                                        System.out.println("Please enter a valid index!");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Please enter a valid index!");
                                }
                            }
                            if (!m.listQuizzes(currentCourse.getName()).equals("There are currently no quizzes!")) {
                                Quiz currentQuiz = new Quiz("XDDDDTEMPXD!@#$");
                                while (true) {
                                    try {
                                        System.out.println("Select the Quiz you want to take.");
                                        System.out.println(m.listQuizzes(currentCourse.getName()));
                                        String quizzes = m.listQuizzes(currentCourse.getName());
                                        System.out.println("-1. Take a quiz using a File:");
                                        System.out.println("0. Back");
                                        choice = s.nextLine();
                                        if (choice.equals("0")) {
                                            continue Student;
                                        } else if (choice.equals("-1")) {
                                            System.out.println("Enter the filepath(just the filename): ");
                                            String filename = s.nextLine();
                                            //currentQuiz.addSubmissionViaFile(currentAccount, filename);
                                        } else {
                                            currentQuiz = m.convertQuiz(currentCourse.getName(),
                                                    m.getQuizName(Integer.parseInt(choice) - 1, quizzes));
                                            System.out.println("1. View Gradings");
                                            System.out.println("2. Take the quiz");
                                            System.out.println("0. Back");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Please enter a valid index!");
                                    }
                                    switch (s.nextLine()) {
                                        case "1":
                                            currentQuiz.showResultsOfQuiz(currentAccount);
                                            break;
                                        case "2":
                                            //ArrayList<String> answers = (currentAccount.takeQuiz(s, currentQuiz));
                                            //currentQuiz.addSubmission(currentAccount, answers);
                                            break;
                                        case "0":
                                            continue Student;
                                        default:
                                            System.out.println("Invalid input");
                                            break;
                                    }
                                }
                            } else {
                                System.out.println("0. Back");
                                if (!s.nextLine().equals("0")) {
                                    System.out.println("Invalid Input!");
                                    continue Student;
                                } else if (s.nextLine().equals("0")) {
                                    continue Student;
                                }
                            }
                        } else {
                            System.out.println("There are currently no courses!");
                            continue Student;
                        }
                    default:
                        System.out.println("Invalid Choice");
                }

            }
        }

    }


}
