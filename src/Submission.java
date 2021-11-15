import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
/** 
 * Represents the student's submission of the quiz
 */
public class Submission {
    //The username of the person whose submission is being graded
    private String username;
    //Determines if the submisison is graded or not
    private boolean graded;
    //An array of answers for each question
    private int[] answers;
    //The list of grades for each question
    private int[] subGrades;
    //Total amount of points/grades received on the quiz
    private int totalGrades;
    //The time when the quiz was submitted
    private String timestamp;
    /**
     * Constructs a newly allocated Submission object with the specified account and list of answers
     * Starts off with no submissions graded 
     * @param username The specified username of the person to be used in construction
     * @param answers The specified list of the student's answers to be used in construction
     */
    public Submission(String username, int[] answers) {
        this.username = username;
        this.answers = answers;
        graded = false;
        subGrades = new int[answers.length];
        totalGrades = 0;
        this.timestamp = new SimpleDateFormat("yyyy.MM.dd HH-mm-ss").format(new java.util.Date());
    }
    /**
     * Returns the String representation of Submission
     * Example:
     * //CHANGE THIS -MANAS**************W
     *Submission{Account="1, username, password, false", graded=true, answers=["1","2","3","4"], subGrades=["2","2","1","1"], totalGrades=6};
     * @return Returns the String representation of Submission
     */
    @Override
    public String toString() {
        return "Submission: " + "\n" +
                "Username: " + username + "\n" +
                "Graded: " + graded + "\n" +
                "Answers: " + Arrays.toString(answers) + "\n" +
                "Points: " + Arrays.toString(subGrades) + "\n" +
                "Total Points: " + totalGrades + "\n" +
                "Time Submitted: " + this.timestamp;
    }
    /**
     * Constructs a newly allocated Submission object with the specified account, graded status, list of answers and subgrades, and total grade
     * @param username The specified username of the person to be used in construction
     * @param graded The specified grade status of the person to be used in construction
     * @param answers The specified list of the student's answers to be used in construction
     * @param subGrades The specified list of subgrades of each question to be used in construction
     * @param totalGrades The specified total amount of points earned to be used in construction
     */
    public Submission(String username, boolean graded, int[] answers, int[] subGrades, int totalGrades) {
        this.username = username;
        this.graded = graded;
        this.answers = answers;
        this.subGrades = subGrades;
        this.totalGrades = totalGrades;
    }
    //KEEP THIS
    public Submission() {

    }
    /**
     * Returns the time stamp of when the submission was submitted
     * @return the time stamp of when the submission was submitted
     */
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    /**
     * Returns the account of the person who made a submission
     * @return the account of the person who made a submission
     */
    public String getUsername() {
        return username;
    }
    /**
     * Updates the person's account using the specified account
     * @param username The specified username that is going to be used in the update
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Returns the list of answers of the person who submitted the quiz
     * @return the list of answers of the person who submitted the quiz
     */
    public int[] getAnswers() {
        return answers;
    }
    /**
     * Updates the list of answers using the specified list of answers
     * @param answers The specified list of answers that is going to be used in the update
     */
    public void setAnswers(int[] answers) {
        this.answers = answers;
    }
    /**
     * Returns the list of sub grades for the person who submitted the quiz
     * @return the list of sub grades for the person who submitted the quiz
     */
    public int[] getSubGrades() {
        return subGrades;
    }
    /**
     * Updates the list of sub grades using the specified list of sub grades
     * @param answers The specified list of sub grades that is going to be used in the update
     */
    public void setSubGrades(int[] subGrades) {
        this.subGrades = subGrades;
    }
    /**
     * Returns the total points earned for the person who submitted the quiz
     * @return the total points earned for the person who submitted the quiz
     */
    public int getTotalGrades() {
        return totalGrades;
    }
    /**
     * Updates the total points earned using the specified total points earned
     * @param answers The specified total points earned that is going to be used in the update
     */
    public void setTotalGrades(int totalGrades) {
        this.totalGrades = totalGrades;
    }
    /**
     * Updates the graded status using the specified list of answers
     * @param answers The specified grade status that is going to be used in the update
     */
    public void setGraded(boolean graded){
        this.graded = graded;
    }
    /**
     * Returns the grade status of the submission
     * @return the grade status of the submission
     */
    public boolean isGraded() {
        return graded;
    }
    /**
     * Updates the time stamp of the submission from when the student submits their answers
     */
    /*
    public void setTimestamp() {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(String.valueOf(System.currentTimeMillis()));
            long ts = date.getTime();
            res = String.valueOf(ts);
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
            this.timestamp = timestamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

     */
}
