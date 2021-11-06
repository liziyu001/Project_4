import java.util.ArrayList;

public class Quiz {
    String name;
    ArrayList<Question> questions;

    public Quiz(String name, ArrayList<Question> questions) {
        this.name = name;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
//possiblyuu for grading
    public void Feedback(){

    }
}


