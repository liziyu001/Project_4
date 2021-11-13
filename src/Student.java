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

}
