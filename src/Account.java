import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Account {
    //The username of the person's account
    private String username;
    //The password of the person's account
    private String password;
    //Determines if the person is a student or not
    private boolean isStudent;
    //Represents the account id number of the person 
    private static int id = 1;
    private int accountId;

    /** Constructs a newly allocated Account object with the specified username, password, and boolean isStudent
    * The account id increments by 1 each time a person's account is created
    * @ param username The specified username of the person to be used in construction
    * @ param password The specified password of the person to be used in construction
    * @ param isStudent The specified boolean to be used in construction that determines whether the person is a student or not
    */ 
    public Account(String username, String password, boolean isStudent) {
        this.username = username;
        this.password = password;
        this.isStudent = isStudent;
        this.accountId = id;
        id++;
    }

    public Account(Account a) {
        this.username = a.getUsername();
        this.password = a.getPassword();
        this.isStudent = a.isStudent();
        this.accountId = id;
        id++;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) { this.accountId = accountId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return isStudent == account.isStudent && accountId == account.accountId
                && Objects.equals(username, account.username) && Objects.equals(password, account.password);
    }

    @Override
    public String toString() {
        return  accountId + ", " + username + ", " + password + ", " + isStudent;
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

    /**
     * A helper method which first confirms the target submission exists, then prompts the user to grade it.
     *
     * @param s The scanner object using System.in which grabs the user's input when grading
     * @param courseName The course which the quiz should belong to
     * @param quizName The specified quiz name
     * @return An ArrayList which contains each assigned point value corresponding to an answer
     */
    public ArrayList<Integer> gradeSubmissionViaFile(Scanner s, String courseName, String quizName){

        Scanner reader = null;
        try {
            reader = new Scanner(new FileReader(this.username + ".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String submission = reader.nextLine();
        boolean found = false;
        String[] line = null;
        while(submission!=null){
            line = submission.split(",");
            if(line[0].equals(courseName) && line[1].equals(quizName)){
                found = true;
                break;
            }
            submission = reader.nextLine();
        }
        if(!found){
            System.out.println("The specified submission was not found!");
            return null;
        }

        ArrayList<Integer> grades = new ArrayList<Integer>();
        for(int i=2; i<line.length; i++){
            System.out.println("Answer for the " + (i-1) + " question is: " + line[i]);
            System.out.println("How many points would you give for this answer?");

            grades.add(Integer.parseInt(s.nextLine()));
        }

        return grades;

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

    /**
     * A picky method which will look through a file, add its contents to an ArrayList, then iterate through the ArrayList and append each component to a specified location.
     * @param fileName The file that the method parses through. File must be formatted in the Quiz format or else the method will return an error.
     * @param targetCourse The name of the course the method will append the data to. Do not include a ".txt" as the method automatically adds one.
     */
    public void uploadQuizFromFile(String fileName, String targetCourse){

        // Reading and writing
        Scanner scan = null;
        BufferedWriter bw = null;

        // Sees if the file exists
        try {
            scan = new Scanner(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("File name was invalid");
            return;
        }

        // Sees if the target location to write to exists
        try {
            bw = new BufferedWriter(new FileWriter(targetCourse + ".txt", true));
        } catch (IOException e) {
            System.out.println("Target course was invalid");
            return;
        }

        // This arraylist will contain valid data to be appended at the end.
        ArrayList<String> contents = new ArrayList<String>();

        // The name of the quiz.
        String name = scan.nextLine();

        // Check if the name is formatted correctly before adding it to the ArrayList.
        if(!name.contains("Name of Quiz:")){
            System.out.println("File was not formatted correctly");
            return;
        }
        contents.add(name);

        // Iterate through the file
        while(scan.hasNext()){

            // Checks and adds the question.
            String questionPrompt = scan.nextLine();
            if(!questionPrompt.contains("Prompt of Question:")){
                System.out.println("File was not formatted correctly");
                return;
            }
            contents.add(questionPrompt);

            // Iterates through the remaining answers.
            String answer = scan.nextLine();
            while(answer!=null && !answer.contains("Correct Answer:")){
                contents.add(answer);
                answer = scan.nextLine();
            }

            // Makes sure that a correct answer prompt was provided
            if(answer==null){
                System.out.println("File is missing a correct answer prompt");
                return;
            }

            contents.add(answer);
        }

        // Appends the contents of the ArrayList to the specified location
        for(String s : contents){
            try {
                bw.newLine();
                bw.append(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Closes the BufferedWriter for safety
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
