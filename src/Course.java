import java.util.ArrayList;

public class Course {
    private String name;
    private ArrayList<Quiz> courseQuiz; 
  
    public Course(String name, ArrayList<Quiz> courseQuiz) {
        this.name = name;
        this.courseQuiz = courseQuiz;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Quiz> getCourseQuiz() {
        return courseQuiz;
    }
    public void setCourseQuiz(ArrayList<Quiz> courseQuiz) {
        this.courseQuiz = courseQuiz;
    }
  
}
