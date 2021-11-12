import java.util.Scanner;

public class Teacher extends Account {
    public Teacher(String username, String password) {
        super(username, password, false);
    }

    public Course createCourse(Scanner scanner) {
        System.out.println("Enter the name of the course: ");
        String nameCourse = scanner.nextLine();
        return new Course(nameCourse);
    }
    public Submission gradeSubmission (Scanner s, Submission sub) {
        
    }


    public void randomizeQuiz() {
        
    }
}
