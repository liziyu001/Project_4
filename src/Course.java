import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Course {
    private String name;
    private ArrayList<Quiz> courseQuiz;
    public Course(String name, ArrayList<Quiz> courseQuiz) {
        this.name = name;
        this.courseQuiz = courseQuiz;
    }
    public Course(String name) {
        this.name = name;
        this.courseQuiz = new ArrayList<>();
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

        /*
        Optional<Quiz> quiz  = courseQuiz.stream().parallel().filter(val -> val.getName().equals(name)).findFirst();
        if (quiz.isPresent()) {
            System.out.println("Before:");
            System.out.println(courseQuiz);
            courseQuiz.remove(quiz);
            System.out.println("After:");
            System.out.println(courseQuiz);
            System.out.println("Quiz: " + name + "has been successfully removed");
        }
        else {
            System.out.println("Such name doesn't exits in the list of quizzes");
        }
         */
    }
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
            Quiz quiz = new Quiz(name, questions);
            courseQuiz.add(quiz);
            //System.out.println("Course quiz list:");
            //System.out.println(courseQuiz);
            //System.out.println(this);
            System.out.println("Quiz has been successfully added!");
            return true;
        }
        else {
            System.out.println("Quiz with such name already exist");
            return false;
        }
    }
    public boolean editQuiz(String name, Scanner scanner) {
        boolean isFound = false;
        for(Quiz quiz : courseQuiz) {
            if (quiz.getName().equals(name)) {
                isFound = true;
            }
        }
        if (isFound) {
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
            Optional<Quiz> quiz  = courseQuiz.stream().parallel().filter(val -> val.getName().equals(name)).findFirst();
            Quiz quizToAdd = new Quiz(newName, questions);
            if (quiz.isPresent()) {
                courseQuiz.set(courseQuiz.indexOf(quiz) + 1, quizToAdd);
                System.out.println("Quiz has been successfully edited");
                return true;
            }
            else {
                System.out.println("There is no quiz with such name: " + name);
                return false;
            }
        }
        else {
            System.out.println("There is no quiz with such name: " + name);
            return false;
        }
    }
    public void AddQuizFromFile(String filename) {
        /*
        text file should look like this:
        Name of Quiz
        Promt of question
        Answer1, Answer2, Answer3, ANswer4
        CorrectAnswer
        -
        Promt of question
        Answer1, Answer2, Answer3, ANswer4
        CorrectAnswer
         */
        try {
            Path filePath = new File(filename).toPath();
            List<String> stringList = Files.readAllLines(filePath);
            String[] stringArray = stringList.toArray(new String[]{});
            String nameOfQuiz = stringArray[0];
            ArrayList<Question> questions = new ArrayList<>();
            for (int i = 1; i < stringArray.length; i += 4){
                String promptOfQuestion = stringArray[i];
                String answers = stringArray[i+1];
                int correctAnswer = Integer.parseInt(stringArray[i+2]);
                String[] str = answers.split(",");
                List<String> answerChoices = new ArrayList<>();
                answerChoices = Arrays.asList(str);
                Question question = new Question(promptOfQuestion, (ArrayList<String>) answerChoices, correctAnswer);
                questions.add(question);
            }
            Quiz quiz = new Quiz(nameOfQuiz, questions);
            courseQuiz.add(quiz);
            System.out.println("Quiz has been successfully added");

        } catch (IOException e) {
            System.out.println("Cannot read from a file");
        }
    }

    public String toString() {
        String finalToReturn = "CourseName: " + name + "\n";
        for (Quiz quiz : courseQuiz) {
            finalToReturn += quiz.toString();
        }
        return finalToReturn;
    }

}
