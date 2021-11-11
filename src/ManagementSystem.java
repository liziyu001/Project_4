
import java.util.Scanner;

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
                        if (!Methods.checkAvailability(id)) {
                            System.out.println("Invalid Username");
                            continue;
                        }

                        break;
                    }
                    System.out.println("Enter your Password:");
                    String pwd = s.nextLine();
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
                    System.out.println("Enter your User ID:");
                    id = s.nextLine();
                    System.out.println("Enter your Password:");
                    ans = s.nextLine();
                    currentAccount = Methods.login(id, ans);
                    if (currentAccount != null) {
                        System.out.println("Successfully logged in as " + currentAccount.getUsername());
                    } else {
                        System.out.println("Invalid id or password");
                        continue login;
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
                        System.out.println("Enter the name of the course you want to create:");
                        m.addCourse(new Course(s.nextLine()));
                        m.record();
                        break;
                    case "3":
                        System.out.println("1. Edit your username");
                        System.out.println("2. Edit your password");
                        System.out.println("3. Delete your account");
                        switch (s.nextLine()){
                            case "1":
                                System.out.println("Enter your new Username:");
                                currentAccount.editId(s.nextLine());
                            case "2":
                                System.out.println("Enter your new Password:");
                                currentAccount.editPassword(s.nextLine());
                            case "3":
                                m.deleteAccount(currentAccount);
                        }
                    case "1":
                        System.out.println("Select the course you want to view, ");
                        System.out.println(m.listCourses());
                        Course currentCourse = ((Course)m.getCourses().get(Integer.parseInt(s.nextLine()) - 1));
                        System.out.println(currentCourse.toString());

                        System.out.println("Select the Quiz you want to proceed.");
                        Quiz currentQuiz = ((Quiz)currentCourse.getCourseQuiz().get(Integer.parseInt(s.nextLine()) - 1));
                        System.out.println("1. Edit the Quiz");
                        System.out.println("2. Grade Submissions");
                        System.out.println("3. Delete this Quiz");
                        System.out.println("4. Upload Quiz from file");
                        System.out.println("0. Create a new Quiz");
                        switch (s.nextLine()){
                            case "1":
                                currentCourse.editQuiz(currentQuiz.getName(), s);
                            case "2" :

                            case "3":
                                currentCourse.deleteQuiz(currentQuiz.getName());
                            case "4":
                                System.out.println("Enter the filename: ");
                                String filename = s.nextLine();
                                currentCourse.AddQuizFromFile(filename);
                            case "0":
                                System.out.println("Enter the name of the Quiz");
                                currentCourse.addQuiz(s.nextLine(), s);
                        }
                }
            }
        } else {
            Student:
            while (true) {
                System.out.println("1. View Courses");
                System.out.println("2. Account Setting");
                switch (s.nextLine()){
                    case "2":
                        System.out.println("1. Edit your username");
                        System.out.println("2. Edit your password");
                        System.out.println("3. Delete your account");
                        switch (s.nextLine()){
                            case "1":
                                System.out.println("Enter your new Username:");
                                currentAccount.editId(s.nextLine());
                            case "2":
                                System.out.println("Enter your new Password:");
                                currentAccount.editPassword(s.nextLine());
                            case "3":
                                m.deleteAccount(currentAccount);
                        }
                    case "1":
                }
            }
        }

    }
}
