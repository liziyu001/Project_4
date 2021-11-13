import java.util.Scanner;

public class Student extends Account {
    public Student(String username, String password) {
        super(username, password, true);
    }
    public Submission takeQuiz(Quiz q, Scanner s){
        System.out.println("1. take on terminal");
        System.out.println("2. take by uploading a file");
        switch (s.nextLine()) {
            case "1":
            case "2" :
        }
    }

    public Submission[] viewQuizResults(Student s, Course c){ //return graded submissions belongs to course c and did by student s.

    }
}
