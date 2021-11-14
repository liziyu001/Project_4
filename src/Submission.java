import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Submission {
    private Account Account;
    private boolean graded;
    private int[] answers;
    private int[] subGrades;
    private int totalGrades;
    private String timestamp;
    public Submission(Account Account, int[] answers) {
        this.Account = Account;
        this.answers = answers;
        graded = false;
        subGrades = new int[answers.length];
        totalGrades = 0;
        this.timestamp = new SimpleDateFormat("yyyy.MM.dd HH-mm-ss").format(new java.util.Date());
    }
    @Override
    public String toString() {
        return "Submission{" +
                ", Account=" + Account +
                ", graded=" + graded +
                ", answers=" + Arrays.toString(answers) +
                ", subGrades=" + Arrays.toString(subGrades) +
                ", totalGrades=" + totalGrades +
                '}';
    }

    public Submission(Account Account, boolean graded, int[] answers, int[] subGrades, int totalGrades) {
        this.Account = Account;
        this.graded = graded;
        this.answers = answers;
        this.subGrades = subGrades;
        this.totalGrades = totalGrades;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Account getAccount() {
        return Account;
    }

    public void setAccount(Account Account) {
        this.Account = Account;
    }

    public int[] getAnswers() {
        return answers;
    }

    public void setAnswers(int[] answers) {
        this.answers = answers;
    }

    public int[] getSubGrades() {
        return subGrades;
    }

    public void setSubGrades(int[] subGrades) {
        this.subGrades = subGrades;
    }

    public int getTotalGrades() {
        return totalGrades;
    }

    public void setTotalGrades(int totalGrades) {
        this.totalGrades = totalGrades;
    }

    public void setGraded(boolean graded){
        this.graded = graded;
    }

    public boolean isGraded() {
        return graded;
    }

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
        } catch (ParseException e){
            e.printStackTrace();
        }

    }
}
