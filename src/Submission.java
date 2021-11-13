public class Submission {
    private Quiz quiz;
    private Student student;
    private boolean graded;
    int[] answers;
    int[] subGrades;
    int totalGrades;

    public Submission(Student student, int[] answers, Quiz quiz) {
        this.student = student;
        this.answers = answers;
        this.quiz = quiz;
        graded = false;
        subGrades = new int[answers.length];
        totalGrades = 0;
    }
    @Override
    public String toString() {
        return "Submission{" +
                "quiz=" + quiz +
                ", student=" + student +
                ", graded=" + graded +
                ", answers=" + Arrays.toString(answers) +
                ", subGrades=" + Arrays.toString(subGrades) +
                ", totalGrades=" + totalGrades +
                '}';
    }

}
