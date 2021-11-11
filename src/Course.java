import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

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
    //deleting quiz based on the name (name must be unique)
    public void deleteQuiz(String name){
        Optional<Quiz> quiz  = courseQuiz.stream().parallel().filter(val -> val.getName().equals(name)).findFirst();
        if (quiz.isPresent()){
            courseQuiz.remove(quiz);
            System.out.println("Quiz: "+ name + "has been succesfully removed");
        }
        else {
            System.out.println("Such name doesn't exits in the list of quizes");
        }

    }
    public void addQuiz(String name, Scanner scanner){
        boolean isFound = false;
        for(Quiz quiz : courseQuiz){
            if (quiz.getName().equals(name)) isFound = true;
        }
        if (!isFound){
            ArrayList<Question> questions = new ArrayList<>();

            boolean isEnough;
            do{
                System.out.println("Initialization of questions");
                System.out.println("Enter prompt of the question: ");
                String prompt = scanner.nextLine();
                System.out.println("How many choices will question include?");
                int amountOfChoices = Integer.parseInt(scanner.nextLine());
                String[] options = new String[amountOfChoices];
                //Initialize array of options from asking a user to enter list of questions.
                for (int i =0; i< options.length; i++){
                    System.out.println((i+1) + ": ");
                    String answerChoice = scanner.nextLine();
                    options[i] = answerChoice;
                }
                ArrayList<String> answerChoice = (ArrayList<String>) Arrays.asList(options);
                System.out.println("What is a correct answer?");
                // -1 because user will see list of questions start from 1, but actually it starts from 0
                int correctAnswer = Integer.parseInt(scanner.nextLine()) - 1;
                Question question = new Question(prompt, answerChoice, correctAnswer);
                questions.add(question);
                System.out.println("Do you want to add another question? (Y/N)");
                String answer = scanner.nextLine().toLowerCase();
                if (answer.equals("n")){
                    isEnough = true;
                }
                else if (answer.equals("y")){
                    isEnough = false;
                }
                else {
                    System.out.println("Invalid option");
                    isEnough = true;
                }
            }while (!isEnough);
            Quiz quiz = new Quiz(name, questions);
            courseQuiz.add(quiz);
            System.out.println("Quiz has been successfully added!");
        }
        else System.out.println("Quiz with such name already exist");
    }
    public void editQuiz(String name, Scanner scanner){
        boolean isFound = false;
        for(Quiz quiz : courseQuiz){
            if (quiz.getName().equals(name)) isFound = true;
        }
        if (isFound){
            System.out.println("Enter the new name: ");
            String newName = scanner.nextLine();
            boolean isEnough;
            ArrayList<Question> questions = new ArrayList<>();
            do{
                System.out.println("Initialization of questions");
                System.out.println("Enter prompt of the question: ");
                String prompt = scanner.nextLine();
                System.out.println("How many choices will question include?");
                int amountOfChoices = Integer.parseInt(scanner.nextLine());
                String[] options = new String[amountOfChoices];
                //Initialize array of options from asking a user to enter list of questions.
                for (int i =0; i< options.length; i++){
                    System.out.println((i+1) + ": ");
                    String answerChoice = scanner.nextLine();
                    options[i] = answerChoice;
                }
                ArrayList<String> answerChoice = (ArrayList<String>) Arrays.asList(options);
                System.out.println("What is a correct answer?");
                // -1 because user will see list of questions start from 1, but actually it starts from 0
                int correctAnswer = Integer.parseInt(scanner.nextLine()) - 1;
                Question question = new Question(prompt, answerChoice, correctAnswer);
                questions.add(question);
                System.out.println("Do you want to add another question? (Y/N)");
                String answer = scanner.nextLine().toLowerCase();
                if (answer.equals("n")){
                    isEnough = true;
                }
                else if (answer.equals("y")){
                    isEnough = false;
                }
                else {
                    System.out.println("Invalid option");
                    isEnough = true;
                }
            }while (!isEnough);
            Optional<Quiz> quiz  = courseQuiz.stream().parallel().filter(val -> val.getName().equals(name)).findFirst();
            Quiz quizToAdd = new Quiz(name, questions);
            if (quiz.isPresent()){
                courseQuiz.set(courseQuiz.indexOf(quiz), quizToAdd);
                System.out.println("Quiz has been successfully edited");
            }
            else {
                System.out.println("There is no quiz with such name: "+ name);
            }
        }
        else{
            System.out.println("There is no quiz with such name: "+ name);
        }
    }
    public String toString(){
        String finalToReturn = name;
        for (Quiz quiz : courseQuiz){
            finalToReturn += quiz.toString();
        }
        return finalToReturn;
    }
}
