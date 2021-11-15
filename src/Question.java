import java.util.ArrayList;

/**
 * Represents the questions that will be on the quiz
 *
 * @author Manas Srivastava, Ziyu Li, Leo Pan, Ram Laxminarayan, Miras Abdishev
 * @version November 15, 2021
 */
public class Question {
    //The prompt of the question on the quiz
    private String prompt;
    //The list of answer choices that are given
    private ArrayList<String> answerChoices;
    //The correct answer of the question
    private int correctAnswer;

    /**
     * Constructs a newly allocated Question object with the specified prompt, list of answer choices,
     * and correct answer
     *
     * @param prompt        The specified prompt of the question to be used in construction
     * @param answerChoices The specified list of answer choices of the question to be used in construction
     * @param correctAnswer The specified correct answer of the question to be used in construction
     */
    public Question(String prompt, ArrayList<String> answerChoices, int correctAnswer) {
        this.prompt = prompt;
        this.answerChoices = answerChoices;
        this.correctAnswer = correctAnswer;
    }

    /**
     * Returns the prompt of the question
     *
     * @return the prompt of the question
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Updates the prompt of the question using the specified prompt
     *
     * @param prompt The specified prompt that is going to be used in the update
     */
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    /**
     * Returns the list of answer choices for the question
     *
     * @return the list of answer choices for the question
     */
    public ArrayList<String> getAnswerChoices() {
        return answerChoices;
    }

    /**
     * Updates the answers choices of the question using the specified answer choices
     *
     * @param answerChoices The specified answer choices that is going to be used in the update
     */
    public void setAnswerChoices(ArrayList<String> answerChoices) {
        this.answerChoices = answerChoices;
    }

    /**
     * Returns the correct answer for the question
     *
     * @return the correct answer for the question
     */
    public int getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Updates the correct answer of the question using the specified correct answer
     *
     * @param The specified the correct answer of the question that is going to be used in the update
     */
    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * Returns the String represention of a question with the correct answer
     * Example:
     * Prompt of Question: What's your name
     * 1. Ram
     * 2. Manas
     * 3. Leo
     * 4. William
     * Correct Answer: 1
     *
     * @return the String representation of a question with the correct answer
     */
    public String toString() {
        String output = "Prompt of Question: " + getPrompt() + "\n";
        for (int i = 0; i < answerChoices.size(); i++) {
            output += answerChoices.get(i) + "\n";
        }
        output += "Correct Answer: " + correctAnswer;
        return output + "\n";
    }

    /**
     * Returns the String represention of a question without the correct answer
     * Example:
     * Prompt of Question: What's your name
     * 1. Ram
     * 2. Manas
     * 3. Leo
     * 4. William
     *
     * @return the String representation of a question without the correct answer
     */
    public String toStringWthoutAnswer() {
        String output = "Prompt of Question: " + getPrompt() + "\n";
        for (String str : answerChoices) {
            output += str + "\n";
        }
        return output + "\n";
    }

}
