import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Quiz {
    private String name;
    private ArrayList<Question> questions;
    private ArrayList<Submission> submissions;
    public Quiz(String name, ArrayList<Question> questions) {
        this.name = name;
        this.questions = questions;
        this.submissions = new ArrayList<>();
    }
    public Quiz(String name){
        this.name = name;
        questions = new ArrayList<>();
        submissions = new ArrayList<>();
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

    public boolean equals(Object o){
        if (o instanceof Quiz){
            Quiz that = (Quiz) o;
            return that.getName().equals(name) && questions.equals(that.getQuestions());
        }
        return false;
    }
    public String toString() {
        String output = "Name of Quiz: " + name + "\n";
        for (Question question : questions){
            output += question.toString();
        }
        return output + "\n";
    }

    public Submission getSubmissionOfAccount(Account Account){
        Submission submission = null;
        for(Submission sub : submissions){
            if (sub.getAccount().equals(Account)){
                submission = sub;
            }
        }
        return submission;
    }
    public void showResultsOfQuiz(Account Account){
        Submission submission = getSubmissionOfAccount(Account);
        if (submission == null){
            System.out.println("You didn't submit your response to that quiz");
        }
        else{
            if (submission.isGraded()){
                int max;
                for (int i =0; i<submission.getSubGrades().length; i++){
                    System.out.println("Your grade for " + i+1 + " question was" + submission.getSubGrades()[i]);
                }
                System.out.println("Your total grade: " + submission.getTotalGrades());
            }
            else{
                System.out.println("Your quiz was not graded");
            }
        }
    }
    public void showAllSubmission(){
        int i =1;
        for(Submission sub: submissions){
            if(!sub.isGraded()){
                System.out.println(i+"View submission of "+sub.getAccount().getUsername() + " " +sub.getTimestamp());
            }
            i++;
        }
    }
    public Submission getSubmissionById(int index){
        return submissions.get(index);
    }
    public void addSubmission(Account Account, ArrayList<String> responses){
        ArrayList<Integer> answers = new ArrayList<>();
        for (String s : responses){
            answers.add(Integer.parseInt(s));
        }
        int[] ans = new int[answers.size()];
        for (int i =0; i<ans.length; i++){
            ans[i] = answers.get(i);
        }
        Submission submission = new Submission(Account, ans);
        submissions.add(submission);
        System.out.println("Quiz has been taken");
    }
    public void addSubmissionViaFile(Account account, String filename)  {
           /*
           txt should have format like this:
           1, 2, 4, 1
           each number is an answer for the each question if number of answer is bigger than amount of question throw error.
            */
        try{
            String data = new String(Files.readAllBytes(Paths.get(filename)));
            String[] answers = data.split(",");
            int[] ans = new int[answers.length];
            for (int i =0; i<ans.length; i++){
                ans[i] = Integer.parseInt(answers[i]);
            }
            if(questions.size() < ans.length){
                System.out.println("Amount of answers is bigger than amount of questions(");
            }
            else{
                Submission submission = new Submission(account, ans);
                submissions.add(submission);
                System.out.println("Quiz has been successfully added via File");
            }
        }catch (Exception e){
            System.out.println("Wrong format...");
        }
    }
    public void EditSubmission(ArrayList<Integer> values, Account Account){
        int[] subGrades = new int[values.size()-1];
        for (int i =0; i<subGrades.length; i++){
            subGrades[i] = values.get(i);
        }
        int totalGrade = values.get(subGrades.length);
        Submission submission = getSubmissionOfAccount(Account);
        if(submission == null){
            System.out.println("Something went wrong");
        }
        else {
            Submission submissionToAdd = new Submission(submission.getAccount(), true, submission.getAnswers(), subGrades, totalGrade);
            submissions.set(submissions.indexOf(submission), submissionToAdd);
            System.out.println("Quiz has been successfully graded");
        }
    }


}