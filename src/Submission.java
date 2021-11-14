import java.util.Arrays;

public class Submission {
    private Account Account;
    private boolean graded;
    private int[] answers;
    private int[] subGrades;
    private int totalGrades;
    public Submission(Account Account, int[] answers) {
        this.Account = Account;
        this.answers = answers;
        graded = false;
        subGrades = new int[answers.length];
        totalGrades = 0;
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

}
