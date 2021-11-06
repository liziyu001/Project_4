
import java.util.Scanner;

public class ManagementSystem {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to the System");
        String ans = "";

        login:
        while (true) {
            System.out.print("1. Create account");
            System.out.println("2. Login");
            switch (s.nextLine()) {
                case "1":
                    while (true) {
                        System.out.println("Enter your User ID:");
                        String id = s.nextLine();
//                        if (!checkAvailability(id)) {
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
                                //AccountManager.addAccount(Teacher(id, pwd));
                                break;
                            case "2":
                                //AccountManager.addAccount(Student(id, pwd));
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
//                    if (checkAccount(id, ans)){
//                        System.out.println("Successfully logged in as " + id);
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
    }
}
