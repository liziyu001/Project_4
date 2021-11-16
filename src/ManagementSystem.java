import javax.sound.midi.Soundbank;
import java.io.File;
import java.security.cert.PolicyQualifierInfo;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
//Implements all of the other classes created and runs the entire main method needed for testing

/**
 * The ManagementSystem contains the only main method and need to be run to start the system
 *
 * @author Manas Srivastava, Ziyu Li, Leo Pan, Ram Laxminarayan, Miras Abdishev
 * @version November 15, 2021
 */
public class ManagementSystem {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to the System");
        String ans = "";
        Manager m = new Manager();
        boolean quit = false;
        Account currentAccount;
        //Creates files
        try {
            File a = new File("accounts.txt");
            File c = new File("courses.txt");
            File u = new File("accessible_quizzes.txt");
            File d = new File("deleted_accounts.txt");
            c.createNewFile();
            a.createNewFile();
            u.createNewFile();
            d.createNewFile();
        } catch (Exception e) {
            System.out.println("There was a problem on startup");
        }
        //Login process where a person cannot create an account or log in into their existing account
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
                        if (id.contains(",")) {
                            System.out.println("Invalid Username, no commas allowed!");
                            continue;
                        }
                        if (!m.checkAvailability(id) || (!m.checkDeletedAccounts(id))) {
                            System.out.println("Username already exists!");
                            continue login;
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
                    //Can determine if you are a teacher or a student
                    roleChoice:
                    while (true) {
                        System.out.println("Your role: 1. Teacher(manage quizzes/courses, grade student's submissions"
                                + "\n" +
                                "2. Student(view courses/quizzes and submit their responses to quizzes)");
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
                    //Input your existing user ID and password
                    logininput:
                    while (true) {
                        System.out.println("Enter your User ID:");
                        id = s.nextLine();
                        System.out.println("Enter your Password:");
                        ans = s.nextLine();
                        if (id.contains(",") || ans.contains(",")) {
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
        //If the person has a teacher account, then they have the option to view their courses, create a course, and
        // see their account settings
        if (!currentAccount.isStudent()) {
            Teacher:
            while (!quit) {
                System.out.println("1. View Courses");
                System.out.println("2. Create a Course");
                System.out.println("3. Account Setting");
                System.out.println("0. Exit");
                switch (s.nextLine()) {
                    case "0":
                        System.out.println("Thanks for using our program!");
                        break Teacher;
                    case "2":
                        try {
                            Course c = (currentAccount).createCourse(s);
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
                        //The account setting allows you to change your username and password, delete your account,
                        // or exit
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
                                        if (!m.checkAvailability(tempUser) || (!m.checkDeletedAccounts(tempUser))) {
                                            System.out.println("Username already exists!");
                                            continue AccountSetting;
                                        }
                                        if (tempUser.contains(",")) {
                                            System.out.println("Invalid Username, no commas allowed!");
                                        } else {
                                            Account temp = new Account(currentAccount);
                                            currentAccount.setUsername(tempUser);
                                            m.editAccount(currentAccount, currentAccount.getAccountId(), temp.getUsername(), temp.getPassword());
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
                                            Account temp = new Account(currentAccount);
                                            currentAccount.setPassword(tempPass);
                                            m.editAccount(currentAccount, currentAccount.getAccountId(), temp.getUsername(), temp.getPassword());
                                            continue AccountSetting;
                                        }
                                    }
                                case "3":
                                    m.deleteAccount(currentAccount.getAccountId(), currentAccount.getUsername(), currentAccount.getPassword());
                                    quit = true;
                                    break;
                                case "0":
                                    continue Teacher;
                                default:
                                    System.out.println("Invalid Choice");
                                    continue AccountSetting;
                            }
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
                                    //Allows teacher to select a certain quiz, and create a new quiz either
                                    // from a file
                                    // or not from a file
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
                                            Course temp = new Course(currentCourse);
                                            boolean create = currentCourse.addQuiz(s.nextLine(), s);
                                            if (create) {
                                                m.editCourse(temp, currentCourse);
                                                String time = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
                                                        .format(new java.util.Date());
                                                currentQuiz = currentCourse.getCourseQuiz().
                                                        get(currentCourse.getCourseQuiz().size() - 1);
                                                m.createQuizFile(currentCourse.getName(), currentQuiz, time);
                                                m.updateAccessibleQuizzes(currentCourse.getName(),
                                                        currentQuiz.getName(), time);
                                                System.out.println("Quiz created");
                                            } else {
                                                System.out.println("Quiz not created!");
                                            }
                                            continue Teacher;
                                        } else if (choice.equals("-2")) {
                                            Course temp = new Course(currentCourse);
                                            System.out.println("Enter the filepath(just the filename no .txt)");
                                            String filename = s.nextLine();
                                            boolean create = currentCourse.addQuizFromFile(m.
                                                    addQuizFromFile(filename));
                                            if (create) {
                                                String time = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").
                                                        format(new java.util.Date());
                                                m.editCourse(temp, currentCourse);
                                                currentQuiz = currentCourse.getCourseQuiz().
                                                        get(currentCourse.getCourseQuiz().size() - 1);
                                                m.createQuizFile(currentCourse.getName(), currentQuiz, time);
                                                m.updateAccessibleQuizzes(currentCourse.getName(),
                                                        currentQuiz.getName(), time);
                                                System.out.println("Quiz created from file!");
                                            } else {
                                                System.out.println("Quiz not created from file!");
                                            }
                                        } else {
                                            //Allows the teacher to edit or view a quiz, grade a student's submission,
                                            // delete a quiz, or view a quiz
                                            //There is always the option to exit
                                            currentQuiz = m.convertQuiz(currentCourse.getName(),
                                                    m.getQuizName(Integer.parseInt(choice), quizzes));
                                            System.out.println("1. Edit the Quiz");
                                            System.out.println("2. Grade Submissions");
                                            System.out.println("3. Delete this Quiz");
                                            System.out.println("4. View Quiz");
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
                                        Course temp = new Course(currentCourse);
                                        System.out.println("Current course: " + "\n" + currentCourse);
                                        System.out.println("Current Quiz: " + "\n" + currentQuiz);
                                        System.out.println("Quiz name: " + currentQuiz.getName());
                                        boolean create = currentCourse.editQuiz(currentQuiz.getName(), s);
                                        if (create) {
                                            m.editCourse(temp, currentCourse);
                                            String time = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").
                                                    format(new java.util.Date());
                                            currentQuiz = currentCourse.getCourseQuiz().
                                                    get(currentCourse.getCourseQuiz().size() - 1);
                                            m.createQuizFile(currentCourse.getName(), currentQuiz, time);
                                            m.updateAccessibleQuizzes(currentCourse.getName(),
                                                    currentQuiz.getName(), time);
                                            System.out.println("Quiz edited!");
                                        }
                                        break;
                                    case "2":
                                        ArrayList<Submission> submissions = m.convertSubmissions(currentCourse.
                                                        getName()
                                                , currentQuiz.getName());
                                        if (submissions == null || submissions.size() == 0) {
                                            System.out.println("There are no submissions for this quiz!");
                                            break;
                                        }
                                        currentQuiz.setSubmissions(submissions);
                                        int sub;
                                        while (true) {
                                            try {
                                                System.out.println("Choose submission you want to grade(number): ");
                                                currentQuiz.showAllSubmission(currentCourse.getName());
                                                sub = Integer.parseInt(s.nextLine());
                                                if (sub <= 0 || sub > submissions.size()) {
                                                    System.out.println("Enter a valid index!");
                                                } else {
                                                    break;
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Invalid input!");
                                            }
                                        }
                                        Submission tempSub = currentQuiz.getSubmissionById(sub - 1);
                                        ArrayList<Integer> answers = currentAccount.gradeSubmission(s, tempSub);
                                        tempSub = currentQuiz.EditSubmission(answers, tempSub.getUsername(),
                                                tempSub.getTimestamp());
                                        String file = m.searchAccessibleQuizzes(currentCourse.getName(),
                                                currentQuiz.getName());
                                        m.updateGradedQuizzes(file, tempSub);
                                        break;

                                    case "3":
                                        temp = currentCourse;
                                        m.deleteAccessibleQuiz(currentCourse.getName(), currentQuiz.getName());
                                        currentCourse.deleteQuiz(currentQuiz.getName());
                                        m.editCourse(temp, currentCourse);
                                        System.out.println("Quiz deleted!");
                                        break;
                                    case "0":
                                        continue Teacher;
                                    case "4":
                                        System.out.println(currentQuiz);
                                        break;
                                }
                            } else {
                                System.out.println("There are currently no quizzes!");
                                System.out.println("0. Create a new Quiz");
                                if (!s.nextLine().equals("0")) {
                                    System.out.println("Invalid Input!");
                                    continue Teacher;
                                }
                                System.out.println("Enter the name of the Quiz");
                                Course temp = new Course(currentCourse);
                                boolean create = currentCourse.addQuiz(s.nextLine(), s);
                                if (create) {
                                    m.editCourse(temp, currentCourse);
                                    String time = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").
                                            format(new java.util.Date());
                                    m.createQuizFile(currentCourse.getName(), currentCourse.
                                            getCourseQuiz().get(0), time);
                                    m.updateAccessibleQuizzes(currentCourse.getName(), currentCourse.
                                            getCourseQuiz().get(0).getName(), time);
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
                        break;
                    default:
                        System.out.println("Invalid Choice!");
                }
            }
        } else {
            Student:
            //A person with a student account only has the options to view their courses and their account setting,
            // as these options are similar to teacher
            while (!quit) {
                System.out.println("1. View Courses");
                System.out.println("2. Account Setting");
                System.out.println("0. Exit");
                switch (s.nextLine()) {
                    case "0":
                        System.out.println("Thanks for using our program!");
                        break Student;
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
                                        if (!m.checkAvailability(tempUser) || (!m.checkDeletedAccounts(tempUser))) {
                                            System.out.println("Username already exists!");
                                            continue studentAccountChoice;
                                        }
                                        if (tempUser.contains(",")) {
                                            System.out.println("Invalid Username, no commas allowed!");
                                        } else {
                                            Account temp = new Account(currentAccount);
                                            currentAccount.setUsername(tempUser);
                                            m.editAccount(currentAccount, currentAccount.getAccountId(), temp.getUsername(), temp.getPassword());
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
                                            Account temp = new Account(currentAccount);
                                            currentAccount.setPassword(tempPass);
                                            m.editAccount(currentAccount, currentAccount.getAccountId(), temp.getUsername(), temp.getPassword());
                                            continue studentAccountChoice;
                                        }
                                    }
                                case "3":
                                    m.deleteAccount(currentAccount.getAccountId(), currentAccount.getUsername(), currentAccount.getPassword());
                                    System.out.println(currentAccount.getAccountId());

                                    quit = true;
                                    break;
                                case "0":
                                    continue Student;
                                default:
                                    System.out.println("Invalid Choice");
                                    continue studentAccountChoice;
                            }
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
                                        System.out.println("Select the Quiz you want to view.");
                                        System.out.println(m.listQuizzes(currentCourse.getName()));
                                        String quizzes = m.listQuizzes(currentCourse.getName());
                                        System.out.println("0. Back");
                                        choice = s.nextLine();
                                        if (choice.equals("0")) {
                                            continue Student;
                                        } else {
                                            currentQuiz = m.convertQuiz(currentCourse.getName(),
                                                    m.getQuizName(Integer.parseInt(choice), quizzes));
                                            System.out.println("1. View Gradings");
                                            System.out.println("2. Take the quiz");
                                            System.out.println("0. Back");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Please enter a valid index!");
                                    }
                                    switch (s.nextLine()) {
                                        case "1":
                                            ArrayList<Submission> submissions = m.convertSubmissions(
                                                    currentCourse.getName(), currentQuiz.getName());
                                            currentQuiz.showResultsOfQuiz(currentAccount.getUsername(),
                                                    submissions);
                                            break;

                                        case "2":
                                            ArrayList<String> answers = (currentAccount.takeQuiz(s, currentQuiz));
                                            Submission sub = currentQuiz.addSubmission(currentAccount, answers);
                                            m.submit(currentCourse.getName(), currentQuiz.getName(), sub);
                                            break;
                                        case "0":
                                            continue Student;
                                        default:
                                            System.out.println("Invalid input");
                                            break;
                                    }
                                }
                            } else {
                                System.out.println("There are currently no quizzes!");
                                System.out.println("0. Back");
                                if (!s.nextLine().equals("0")) {
                                    System.out.println("Invalid Input!");
                                    continue Student;
                                } else {

                                }
                            }
                        } else {
                            System.out.println("There are currently no courses!");
                            continue Student;
                        }
                        break;
                    default:
                        System.out.println("Invalid Choice");
                }

            }
        }

    }


}
