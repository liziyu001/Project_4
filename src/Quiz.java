import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Quiz {
    private String name;
    private ArrayList<Question> questions;

    public Quiz(String name, ArrayList<Question> questions) {
        this.name = name;
        this.questions = questions;
    }
    public Quiz(String name){
        this.name = name;
        questions = new ArrayList<>();
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

    //Directly asks teacher to write list of question via terminal


    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
    //possiblyuu for grading
    public void Feedback(){

    }
    public String toString() {
        String output = "Name of Quiz: " + name + "\n";
        for (Question question : questions){
            output += question.toString();
        }
        return output + "\n";
    }
}


