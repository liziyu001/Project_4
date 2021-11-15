import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
/**
 * A quiz that be created by the teacher and taken by the student
 */
public class Quiz {
    //Name of the quiz
    private String name;
    //List of questions on the quiz
    private ArrayList<Question> questions;
    //List of submissions that the students make after completing the quiz
    private ArrayList<Submission> submissions;
    /**
     * Constructs a newly allocated Quiz object with the specified name and list of quiz questions
     * The list of submissions is set to an empty array list
     * @param name The specified name of the quiz to be used in construction
     * @param questions The specified list of quiz questions to be used in construction
     */
    public Quiz(String name, ArrayList<Question> questions) {
        this.name = name;
        this.questions = questions;
        this.submissions = new ArrayList<>();
    }
    /**
     * Constructs a newly allocated object with the specified name and empty list of quiz questions and quiz submissions
     * @param name The specified name of the quiz to be used in construction
     */
    public Quiz(String name) {
        this.name = name;
        questions = new ArrayList<>();
        submissions = new ArrayList<>();
    }
    /**
     * Returns the name of the quiz
     * @return the name of the quiz
     */
    public String getName() {
        return name;
    }
    /**
     * Updates the name of the quiz using the specified name
     * @param name The specified name that is going to be used in the update
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Returns the list of quiz questions
     * @return the list of quiz questions
     */
    public ArrayList<Question> getQuestions() {
        return questions;
    }
    /**
     * Updates the list of quiz questions using the specified list of quiz questions
     * @param questions The specified list of quiz questions that is going to be used in the update
     */
    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
    // ADD COMMENT-MANAS
    public void  setSubmissions(ArrayList<Submission> submissions) {
        this.submissions = submissions;
    }

    public ArrayList<Submission> getSubmissions() {
        return submissions;
    }

    /**
     * Determines whether or not the specified object is equal to this quiz
     * Returns true if the object is an instance of quiz and if their names and list of questions are the same; otherwise it returns false
     * @ param o The specified object that is going to be used for comparison
     * @return Returns if the object and quiz are equal to each other or not
     */
    public boolean equals(Object o) {
        if (o instanceof Quiz) {
            Quiz that = (Quiz) o;
            return that.getName().equals(name) && questions.equals(that.getQuestions());
        }
        return false;
    }
    /**
     * Returns the string representation of the quiz
     * Example: 
     * Name of Quiz: quiz
     * Prompt of Question: What's your name
     * 1. Ram 
     * 2. Manas
     * 3. Leo
     * 4. William
     * Correct Answer: 1
     * @return The String representation of the quiz
     */
    public String toString() {
        String output = "Name of Quiz: " + name + "\n";
        for (Question question : questions) {
            output += question.toString();
        }
        return output;
    }
    /**
     * Returns the submission of the person's account that submitted it
     * @param username The username of the person who is submitting the quiz
     * @return the submission of the person's account that submitted it
     */
    public Submission getSubmissionOfAccount(String username, String timestamp) {
        Submission submission = null;
        for(Submission sub : submissions) {
            if (sub.getUsername().equals(username) && sub.getTimestamp().equals(timestamp)) {
                submission = sub;
            }
        }
        return submission;
    }
    /**
     * Shows the grade the person got on their quiz based on their submission
     * Quiz may not be graded if the submission is null, or if there was an error that was encountered when trying to grade their submission
     * @param username The username that the person submitted their quiz on, and is awaiting their results on the quiz
     */
    public void showResultsOfQuiz(String username, String timestamp) {
        Submission submission = getSubmissionOfAccount(username, timestamp);
        if (submission == null) {
            System.out.println("You didn't submit your response to that quiz");
        }
        else {
            if (submission.isGraded()) {
                int max;
                for (int i =0; i<submission.getSubGrades().length; i++) {
                    System.out.println("Your grade for " + (i + 1) + " question was" + submission.getSubGrades()[i]);
                }
                System.out.println("Your total grade: " + submission.getTotalGrades());
            }
            else {
                System.out.println("Your quiz was not graded");
            }
        }
    }
    /** 
     * Shows the submissions that the teacher has not graded yet
     * Increments an int to determine how many submissions have not been graded yet
     */
    public void showAllSubmission(String coursename) {
        Manager m = new Manager();
        String file = m.searchAccessibleQuizzes(coursename, this.getName());
        ArrayList<Submission> subs = m.convertSubmissions(coursename, this.getName());
        int i = 1;
        for (Submission sub: subs) {
            if (!sub.isGraded()) {
                System.out.println(i + ". View submission of "+ sub.getUsername() + " " + sub.getTimestamp());
            }
            i++;
        }
    }
    //NEED TO COMMENT
    public  int checkIfGraded(String coursename) {
        Manager m = new Manager();
        String file = m.searchAccessibleQuizzes(coursename, this.getName());
        ArrayList<Submission> subs = m.convertSubmissions(coursename, this.getName());
        int count = 0;
        for (Submission sub: subs) {
            if (!sub.isGraded()) {
                count++;
            }
        }
        return count;
    }
    /**
     * Gets the submission of a certain person based on their ID
     * @param index Represents the ID number of the student 
     * @return the submission of a certain person based on their ID
     */
    public Submission getSubmissionById(int index) {
        return submissions.get(index);
    }
    /**
     * Adds the responses made by the specified account owner, puts the responses into a submission, and stores the submission in a submissions list
     * @param Account The account of the person writing their responses and submitting
     * @param responses A list of the answers that the person with the specified account put
     */
    public Submission addSubmission(Account Account, ArrayList<String> responses) {
        try {
            ArrayList<Integer> answers = new ArrayList<>();
            for (String s : responses) {
                answers.add(Integer.parseInt(s));
            }
            int[] ans = new int[answers.size()];
            for (int i = 0; i < ans.length; i++) {
                ans[i] = answers.get(i);
            }
            Submission submission = new Submission(Account.getUsername(), ans);
            submissions.add(submission);
            System.out.println("Quiz has been taken");
            return submission;
        } catch (Exception e) {
            System.out.println("Your responses were invalid!");
            return null;
        }
    }
    /**
     * Adds a submission of the person with the specified account from parsing through the file with the specific filename
     * @param account The account of the person who is taking the quiz and submitting it
     * @param filename The name of the file that is being parsed to help read the person's submission
     */
    //not needed
    /*
    public void addSubmissionViaFile(Account account, String filename) {

           txt should have format like this:
           1, 2, 4, 1
           each number is an answer for the each question if number of answer is bigger than amount of question throw error.

        try {
            String data = new String(Files.readAllBytes(Paths.get(filename)));
            String[] answers = data.split(",");
            int[] ans = new int[answers.length];
            for (int i = 0; i < ans.length; i++) {
                ans[i] = Integer.parseInt(answers[i]);
            }
            if (questions.size() < ans.length) { 
                System.out.println("Amount of answers is bigger than amount of questions(");
            }
            else {
                Submission submission = new Submission(account, ans);
                submissions.add(submission);
                System.out.println("Quiz has been successfully added via File");
            }
        } catch (Exception e) {
            System.out.println("Wrong format...");
        }
    }
    */
    /**
     * Edits the submission of the student of each question, and for the total grade
     * @param values An arraylist of integer values that represents the amount of sub grades that are needed in the submission
     * @param username The username of the person whose submission needs to be edited
     */


    public Submission EditSubmission(ArrayList<Integer> values, String username, String timestamp) {
        int totalGrade = 0;
        int[] subGrades = new int[values.size()];
        for (int i = 0; i < subGrades.length; i++) {
            subGrades[i] = values.get(i);
            totalGrade += values.get(i);
        }
        Submission submission = getSubmissionOfAccount(username, timestamp);
        if (submission == null) {
            System.out.println("Something went wrong");
            return null;
        }
        else {
            Submission submissionToAdd = new Submission(submission.getUsername(), true, submission.getAnswers(), subGrades, totalGrade);
            //System.out.println("submissionto add:");
            //System.out.println(submissionToAdd);
            submissionToAdd.setTimestamp(timestamp);
            submissions.set(submissions.indexOf(submission), submissionToAdd);
            System.out.println("Quiz has been successfully graded");
            return submissionToAdd;
        }
    }




}
