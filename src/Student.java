import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Student extends Account {
    public Student(String username, String password) {
        super(username, password, true);
    }

    /**
     * Helper method which prompts the user to enter responses to a given set of questions.
     *
     * @param scan Scanner object which is passed to allow for only one instance of a Scanner object throughout program workflow
     * @param quiz The quiz object which information is pulled from.
     * @return Returns an ArrayList containing the user's responses. Example: {1, 2, 3, 4}
     */
    public ArrayList<String> takeQuiz(Scanner scan, Quiz quiz){
        System.out.println("You are now taking Quiz: " + quiz.getName() + ".");

        // The arraylist which contains the quizzes questions
        ArrayList<Question> questions = new ArrayList<Question>();

        // The arraylist which contains the user's responses
        ArrayList<String> responses = new ArrayList<String>();

        // Iterate through the questions arraylist
        for(Question q : questions){

            System.out.println(q.getPrompt());


            // The arraylist which contains the answer prompts.
            ArrayList<String> answers = q.getAnswerChoices();

            int i = 0;
            for(String s : answers){
                System.out.println((i+1) + ": " + s);
                i++;
            }

            System.out.println("Please select your answer.");
            responses.add(scan.nextLine());

        }

        return responses;

    }

    /**
     * A helper method which will write a given ArrayList of responses to a file titled with this student's username.
     * Format Example:
     *
     * username.txt:
     * CS180, QuizName1, Answer1, Answer2, Answer3, Answer4
     * CS193, QuizName1, Answer1, Answer2, Answer3, Answer4
     *
     * @param courseName The courseName which will be written to the file
     * @param responses An ArrayList containing a student's responses
     */
    public void writeQuizSubmissionToFile(String courseName, ArrayList<String> responses){

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(this.getUsername(), true));
        } catch (IOException e) {
            System.out.println("This person's username is a directory!");
            return;
        }
        try {
            bw.write(courseName);
            for(String s : responses){
                bw.write(", " + s);

            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
