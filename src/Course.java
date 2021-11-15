import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * The course made and provided by the teacher which is taken by the student
 */

public class Course {
    //Name of the course
    private String name;
     // List of quizzes in the course
    private ArrayList<Quiz> courseQuiz;
     /**
     * Constructs a newly allocated object with the specified name, and course quiz list
     * @param name The specified name of the course to be used in construction
     * @param courseQuiz The specified list of quizzes in the course to be used in construction
     */
    public Course(String name, ArrayList<Quiz> courseQuiz) {
        this.name = name;
        this.courseQuiz = courseQuiz;
    }
     /**
     * Constructs a newly allocated object with the specified name and an empty list of quizzes in the course
     * @param name The specified name of the course to be used in construction
     */
    public Course(String name) {
        this.name = name;
        this.courseQuiz = new ArrayList<>();
    }
     /**
     * Returns the name of the course
     * @return the name of the course
     */
    public String getName() {
        return name;
    }
     /**
     * Updates the name of the course using the specified name
     * @param name The specified name that is going to be used in the update
     */
    public void setName(String name) {
        this.name = name;
    }
     /**
     * Returns the list of quizzes in the course 
     * @return the list of quizzes in the course
     */
    public ArrayList<Quiz> getCourseQuiz() {
        return courseQuiz;
    }
    /**
     * Updates the list of quizzes in the course using the specified course quiz list 
     * @param courseQuiz The specified list of course quizzes that is going to be used in the update
     */
    public void setCourseQuiz(ArrayList<Quiz> courseQuiz) {
        this.courseQuiz = courseQuiz;
    }
     /**
     *Deleting quiz based on the name (name must be unique)
     * @param name The name of the quiz to be deleted
     */
    public void deleteQuiz(String name) {
        boolean found = false;
        for (int i = 0; i < courseQuiz.size(); i++) {
            if (courseQuiz.get(i).getName().equals(name)) {
                courseQuiz.remove(i);
                System.out.println("Quiz: " + name + " has been successfully removed");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("A quiz with this name doesn't exist!");
        }

    }
     /**
     * Creates the question prompt and answer choices and determines if the quiz created is valid or not
     * @param name The name of the quiz that is being found and added if it is found
     * @param scanner The scanner object that allows the teacher to enter the question prompt, answer choices, and correct answer
     * @return Returns if the quiz can be added or not
     */
    public boolean addQuiz(String name, Scanner scanner) {
        boolean isFound = false;
        for(Quiz quiz : courseQuiz) {
            if (quiz.getName().equals(name)) {
                isFound = true;
            }
        }
        if (!isFound) {
            ArrayList<Question> questions = new ArrayList<>();

            boolean isEnough;
            do {
                System.out.println("Enter prompt of the question: ");
                String prompt = scanner.nextLine();
                System.out.println("How many choices will question include?");
                int amountOfChoices = 0;
                while (true) {
                    try {
                        amountOfChoices = Integer.parseInt(scanner.nextLine());
                        if (amountOfChoices <= 0) {
                            System.out.println("Please enter a valid amount of choices!");
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Please enter a valid amount of choices!");
                    }

                }
                ArrayList<String> options = new ArrayList<>();
                //Initialize array of options from asking a user to enter list of questions.
                for (int i = 0; i < amountOfChoices; i++) {
                    System.out.println((i+1) + ": ");
                    String answerChoice = scanner.nextLine();
                    options.add((i + 1) + ". " + answerChoice);
                }
                ArrayList<String> answerChoice = options;
                System.out.println("What is a correct answer?(Enter a value from 1- # of questions)");
                // -1 because user will see list of questions start from 1, but actually it starts from 0
                int correctAnswer;
                while (true) {
                    try {
                        correctAnswer = Integer.parseInt(scanner.nextLine()) - 1;
                        if (correctAnswer <= -1 || correctAnswer > options.size() - 1) {
                            System.out.println("Please enter a valid answer!");
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Please enter a valid answer!");
                    }
                }
                Question question = new Question(prompt, answerChoice, correctAnswer);
                questions.add(question);
                System.out.println("Do you want to add another question? (Y/N)");
                String answer = scanner.nextLine().toLowerCase();
                if (answer.equals("n")) {
                    isEnough = true;
                }
                else if (answer.equals("y")) {
                    isEnough = false;
                }
                else {
                    System.out.println("Invalid option");
                    isEnough = true;
                }
            } while (!isEnough);
            while (true) {
                System.out.println("Do you want to randomize this quiz?(Y/N)");
                String randomize = scanner.nextLine();
                if (randomize.equalsIgnoreCase("Y")) {
                    Quiz quiz = new Quiz(name, questions, true);
                    courseQuiz.add(quiz);
                    System.out.println("Quiz has been successfully added!");
                    return true;
                }
                else if (randomize.equalsIgnoreCase("N")) {
                    Quiz quiz = new Quiz(name, questions, false);
                    courseQuiz.add(quiz);
                    System.out.println("Quiz has been successfully added!");
                    return true;
                } else {
                    System.out.println("Please enter a valid input!");
                }

            }

        }
        else {
            System.out.println("Quiz with such name already exist");
            return false;
        }
    }
     /** 
     * Allows the teacher to edit the quiz based on the name of the quiz they want to edit
     * @param name The name of the quiz that the teacher wants to edit
     * @param scanner The Scanner object that allows the teacher to enter a new name for the quiz and redo the same process as the addQuiz(String name, Scanner scanner) method
     * @return Returns if the new edited quiz is valid or not
     */
    public boolean editQuiz(String name, Scanner scanner) {
        boolean isFound = false;
        for(Quiz quiz : courseQuiz) {
            if (quiz.getName().equals(name)) {
                isFound = true;
            }
        }
        if (isFound) {
            Manager m = new Manager();
            m.deleteAccessibleQuiz(this.getName(), name);
            String newName;
            boolean isValid = true;
            while (true) {
                System.out.println("Enter the new name: ");
                newName = scanner.nextLine();
                for (Quiz quiz : courseQuiz) {
                    if (quiz.getName().equals(newName)) {
                        isValid = false;
                        break;
                    } else {
                        isValid = true;
                    }
                }
                if (!isValid) {
                    System.out.println("Quiz with this name already exists!");
                } else {
                    break;
                }
            }
            boolean isEnough;
            ArrayList<Question> questions = new ArrayList<>();
            do {
                System.out.println("Enter prompt of the question: ");
                String prompt = scanner.nextLine();
                System.out.println("How many choices will question include?");
                int amountOfChoices = 0;
                while (true) {
                    try {
                        amountOfChoices = Integer.parseInt(scanner.nextLine());
                        if (amountOfChoices <= 0) {
                            System.out.println("Please enter a valid amount of choices!");
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Please enter a valid amount of choices!");
                    }

                }
                ArrayList<String> options = new ArrayList<>();
                //Initialize array of options from asking a user to enter list of questions.
                for (int i = 0; i < amountOfChoices; i++) {
                    System.out.println((i+1) + ": ");
                    String answerChoice = scanner.nextLine();
                    options.add((i + 1) + ". " + answerChoice);
                }
                ArrayList<String> answerChoice = options;
                System.out.println("What is a correct answer?(Enter a value from 1- # of questions)");
                // -1 because user will see list of questions start from 1, but actually it starts from 0
                int correctAnswer;
                while (true) {
                    try {
                        correctAnswer = Integer.parseInt(scanner.nextLine()) - 1;
                        if (correctAnswer <= -1 || correctAnswer > options.size() - 1) {
                            System.out.println("Please enter a valid answer!");
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Please enter a valid answer!");
                    }
                }
                Question question = new Question(prompt, answerChoice, correctAnswer);
                questions.add(question);
                System.out.println("Do you want to add another question? (Y/N)");
                String answer = scanner.nextLine().toLowerCase();
                if (answer.equals("n")) {
                    isEnough = true;
                }
                else if (answer.equals("y")) {
                    isEnough = false;
                }
                else {
                    System.out.println("Invalid option");
                    isEnough = true;
                }
            } while (!isEnough);
            while (true) {
                System.out.println("Do you want to randomize this quiz?(Y/N)");
                String randomize = scanner.nextLine();
                if (randomize.equalsIgnoreCase("Y")) {
                    Quiz quizToAdd = new Quiz(name, questions, true);
                    for (int i = 0; i < courseQuiz.size(); i++) {
                        if (courseQuiz.get(i).getName().equals(name)) {
                            courseQuiz.set(i, quizToAdd);
                            System.out.println("Quiz has been successfully edited");
                            return true;
                        }
                    }
                }
                else if (randomize.equalsIgnoreCase("N")) {
                    Quiz quizToAdd = new Quiz(name, questions, false);
                    for (int i = 0; i < courseQuiz.size(); i++) {
                        if (courseQuiz.get(i).getName().equals(name)) {
                            courseQuiz.set(i, quizToAdd);
                            System.out.println("Quiz has been successfully edited");
                            return true;
                        }
                    }
                } else {
                    System.out.println("Please enter a valid input!");
                }
            }
        }
        else {
            System.out.println("There is no quiz with such name: " + name);
            return false;
        }
    }

     /**
     * Creates and adds a quiz successfully based on the quiz from the file that it is parsing through
     * @param q The quiz object that is being parsed to help create and add quiz objects into an array list
     */
    public boolean addQuizFromFile(Quiz q) {
        try {
            for (Quiz quiz : courseQuiz) {
                if (quiz.getName().equals(q.getName())) {
                    return false;
                }
            }
            ArrayList<Question> questions = q.getQuestions();
            for (int i = 0; i < questions.size(); i++) {
                Question question = questions.get(i);
                if (question.getCorrectAnswer() >= question.getAnswerChoices().size()) {
                    return false;
                }
            }
            courseQuiz.add(q);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

     /**
      * Returns the String representation of the course
      * Example:
      * CourseName: course
      * IsRandom: false
      * Name of Quiz: quiz 
      * Prompt of Question: What's your name
      * 1. Ram
      * 2. Manas
      * 3. Leo
      * 4. William
      * Correct Answer: 1
      * @return the String representation of the course
      */
    public String toString() {
        String finalToReturn = "CourseName: " + name + "\n";
        for (Quiz quiz : courseQuiz) {
            finalToReturn += quiz.toString();
        }
        return finalToReturn;
    }

}
