public class Submission {
    private Student student;
    private boolean graded;
    private int[] answers;
    private int[] subGrades;
    private int totalGrades;
    public Submission(Student student, int[] answers) {
        this.student = student;
        this.answers = answers;
        graded = false;
        subGrades = new int[answers.length];
        totalGrades = 0;
    }

    public Submission(Student student, boolean graded, int[] answers, int[] subGrades, int totalGrades) {
        this.student = student;
        this.graded = graded;
        this.answers = answers;
        this.subGrades = subGrades;
        this.totalGrades = totalGrades;
    }


    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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
