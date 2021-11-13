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
    public Submission gradeSubmission(Scanner s, Submission sub) {
        
    }

    //Adds the quiz from the file with a randomized question and answer choice order
    public void randomizeQuiz(Course course, String filename) {
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
                Question question = new Question(promptOfQuestion, (ArrayList<String>) answerChoices, correctAnswer);
                questions.add(question);
            }
            //Shuffles the question and answer choice order 
            Collections.shuffle(questions);
            for (int i = 0; i < questions.size(); i++) {
                Collections.shuffle(answerChoices.get(i));
            }
            //Creates and adds the quiz with a randomized order
            Quiz quiz = new Quiz(nameOfQuiz, questions);
            courseQuiz.add(quiz);
            System.out.println("Quiz has been successfully added");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
