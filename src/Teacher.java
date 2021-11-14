import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Teacher extends Account {
    public Teacher(String username, String password) {
        super(username, password, false);
    }

    public Course createCourse(Scanner scanner) {
        System.out.println("Enter the name of the course: ");
        String nameCourse = scanner.nextLine();
        return new Course(nameCourse);
    }
   //when teacher grades submission invoke method in Quiz to editTheSubmission so the student would see it
    public ArrayList<Integer> gradeSubmission(Scanner s, Submission sub) {
       int[] subGrades = new int[sub.getSubGrades().length];
       int totalGrade = 0;
       ArrayList<Integer> toReturn = new ArrayList<>();
        for (int i = 0; i<sub.getAnswers().length; i++){
            System.out.println("Answer for the " + i+1 + "question is: " + sub.getAnswers()[i]);
            System.out.println("How many points would you give for this answer? ");
            int subGrade = Integer.parseInt(s.nextLine());
            totalGrade+=subGrade;
            subGrades[i] = subGrade;
            toReturn.add(subGrade);
        }
        return toReturn;

    }
   
    //Adds the quiz from the file with a randomized question and answer choice order
    public void randomizeQuiz(String filename) {
        try {
            Path filePath = new File(filename).toPath();
            List<String> stringList = Files.readAllLines(filePath);
            String[] stringArray = stringList.toArray(new String[]{});
            String nameOfQuiz = stringArray[0];
            ArrayList<Question> questions = new ArrayList<>();
            ArrayList<Quiz> courseQuiz = new ArrayList<>();
            for (int i = 1; i < stringArray.length; i += 4){
                String promptOfQuestion = stringArray[i];
                String answers = stringArray[i+1];
                int correctAnswer = Integer.parseInt(stringArray[i+2]);
                String[] str = answers.split(",");
                List<String> answerChoices = new ArrayList<>();
                answerChoices = Arrays.asList(str);
                Collections.shuffle(answerChoices);
                Question question = new Question(promptOfQuestion, (ArrayList<String>) answerChoices, correctAnswer);
                questions.add(question);
            }
            //Shuffles the question and answer choice order 
            Collections.shuffle(questions);
            
            //Creates and adds the quiz with a randomized order
            Quiz quiz = new Quiz(nameOfQuiz, questions);
            courseQuiz.add(quiz);
            System.out.println("Randomized quiz has been successfully added");

        } catch (Exception e) {
            System.out.println("Quiz could not be added");
        }
    }
}
