import java.util.ArrayList;

public class Question {
    private String prompt;
    private ArrayList<String> answerChoices;
    private int correctAnswer;

    public Question(String prompt, ArrayList<String> answerChoices, int correctAnswer) {
        this.prompt = prompt;
        this.answerChoices = answerChoices;
        this.correctAnswer = correctAnswer;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public ArrayList<String> getAnswerChoices() {
        return answerChoices;
    }

    public void setAnswerChoices(ArrayList<String> answerChoices) {
        this.answerChoices = answerChoices;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    // dont look at it
    public  void something(){
      //ded
    }
}
