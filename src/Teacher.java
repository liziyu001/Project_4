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

    //Randomizes question and option order
    public void randomizeQuiz(Course course, String filename) {
        try {
            Scanner s = new Scanner(new FileReader(filename));
            while (s.hasNext()) {
                synchronized (Object.class) {
                    course.AddQuizFromFile(filename);   
                }
            }
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
