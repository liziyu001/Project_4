import java.util.ArrayList;

public class Question {
    String prompt;
    ArrayList<String> answerChoices;
    int correctAnswer;

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
}
