
import java.util.Scanner;

public class ManagementSystem {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to the System");
        String ans = "";
        //Manager m = new Manager();     Initialize accounts and courses by reading the files
        //Account currentAccount;

        login:
        while (true) {
            System.out.println("1. Create account");
            System.out.println("2. Login");
            switch (s.nextLine()) {
                case "1":
                    while (true) {
                        System.out.println("Enter your User ID:");
                        String id = s.nextLine();
//                        if (!checkAvailability(id)) {        return true if the new id is unique among the account file
//                            System.out.println("Invalid Username");
//                            continue;
//                        }

                        break;
                    }
                    System.out.println("Enter your Password:");
                    String pwd = s.nextLine();
                    roleChoice:
                    while (true) {
                        System.out.println("Your role: 1. Teacher   2. Student");
                        switch (s.nextLine()) {
                            case "1":
                                //m.addAccount(Teacher(id, pwd));      add a new teacher account in the account file
                                //currentAccount = Teacher(id, pwd)
                                break;
                            case "2":
                                //m.addAccount(Student(id, pwd));      add a new student account in the account file
                                //currentAccount = Student(id, pwd)
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
                    String id = s.nextLine();
                    System.out.println("Enter your Password:");
                    ans = s.nextLine();
                    //currentAccount = login(id, ans)    return the corresponding account object, return null if no matches.
//                    if (currentAccount != null){
//                        System.out.println("Successfully logged in as " + currentAccount.getUsername());
//                    } else {
//                        System.out.println("Invalid id or password");
//                        continue login;
//                    }
                    break;
                default:
                    System.out.println("Invalid choice");
                    continue login;
            }
            break;
        }
//        if (!currentAccount.isStudent()) {
//            Teacher:
//            while(true){
//                System.out.println("1. View Courses");
//                System.out.println("2. Create a Course");
//                System.out.println("3. Account Setting");
//            }
//        } else {
//            Student:
//            while(true){
//                System.out.println("1. View Courses");
//                System.out.println("2. Account Setting");
//            }
//        }

    }
}
