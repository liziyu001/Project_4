import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RunTestMethods {

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;
    private final PrintStream originalOutput = System.out;
    private final InputStream originalSysin = System.in;

    @Test(timeout = 1000)
    /**
     * Makes sure that the program can log into an account properly
     */
    public void testExpectedOne() {
        // Set the input
        // Separate each input with a newline (\n).
        String input = "2\nLeo\nhi\n0";

        // Pair the input with the expected result
        String expected = "Welcome to the System\n" +
                "1. Create account\n" +
                "2. Login\n" +
                "Enter your User ID:\n" +
                "Enter your Password:\n" +
                "Successfully logged in as Leo\n" +
                "1. View Courses\n" +
                "2. Account Setting\n" +
                "0. Exit\n" +
                "Thanks for using our program!";

        // Runs the program with the input values
        // Replace TestProgram with the name of the class with the main method
        receiveInput(input);
        ManagementSystem.main(new String[0]);

        // Retrieves the output from the program
        String stuOut = getOutput();

        // Trims the output and verifies it is correct.
        stuOut = stuOut.replace("\r\n", "\n");
        assertEquals("The output was not as expected.",
                expected.trim(), stuOut.trim());

    }

    @Test(timeout = 1000)
    /**
     * Makes sure that the program handles an incorrect password properly.
     */
    public void testExpectedTwo() {

        String input = "2\nLeo\nhi2\nLeo\nhi\n0";

        String expected = "Welcome to the System\n" +
                "1. Create account\n" +
                "2. Login\n" +
                "Enter your User ID:\n" +
                "Enter your Password:\n" +
                "Invalid ID or password\n" +
                "Enter your User ID:\n" +
                "Enter your Password:\n" +
                "Successfully logged in as Leo\n" +
                "1. View Courses\n" +
                "2. Account Setting\n" +
                "0. Exit\n" +
                "Thanks for using our program!";


        receiveInput(input);
        ManagementSystem.main(new String[0]);

        String stuOut = getOutput();

        stuOut = stuOut.replace("\r\n", "\n");
        assertEquals("The output was not as expected.",
                expected.trim(), stuOut.trim());

    }

    @Test(timeout = 1000)
    /**
     * Makes sure that the program shows courses and account actions properly.
     */
    public void testExpectedThree() {

        String input = "2\nManas\nhi\n1\n0\n3\n0\n0";

        String expected = "Welcome to the System\n" +
                "1. Create account\n" +
                "2. Login\n" +
                "Enter your User ID:\n" +
                "Enter your Password:\n" +
                "Successfully logged in as Manas\n" +
                "1. View Courses\n" +
                "2. Create a Course\n" +
                "3. Account Setting\n" +
                "0. Exit\n" +
                "Select the course you want to view\n" +
                "Courses:\n" +
                "1. CS180\n" +
                "2. CS193\n" +
                "0. Back\n" +
                "1. View Courses\n" +
                "2. Create a Course\n" +
                "3. Account Setting\n" +
                "0. Exit\n" +
                "1. Edit your username\n" +
                "2. Edit your password\n" +
                "3. Delete your account\n" +
                "0. Back\n" +
                "1. View Courses\n" +
                "2. Create a Course\n" +
                "3. Account Setting\n" +
                "0. Exit\n" +
                "Thanks for using our program!\n";


        receiveInput(input);
        ManagementSystem.main(new String[0]);

        String stuOut = getOutput();

        stuOut = stuOut.replace("\r\n", "\n");
        assertEquals("The output was not as expected.",
                expected.trim(), stuOut.trim());

    }

    @Test(timeout = 1000)
    /**
     * Makes sure that the program shows quizzes properly.
     */
    public void testExpectedFour() {

        String input = "2\nLeo\nhi\n1\n1\n0\n0";

        String expected = "Welcome to the System\n" +
                "1. Create account\n" +
                "2. Login\n" +
                "Enter your User ID:\n" +
                "Enter your Password:\n" +
                "Successfully logged in as Leo\n" +
                "1. View Courses\n" +
                "2. Account Setting\n" +
                "0. Exit\n" +
                "Select the course you want to view\n" +
                "Courses:\n" +
                "1. CS180\n" +
                "2. CS193\n" +
                "0. Back\n" +
                "Select the Quiz you want to view.\n" +
                "Quizzes:\n" +
                "1. Name of Quiz: TestQuiz\n" +
                "0. Back\n" +
                "1. View Courses\n" +
                "2. Account Setting\n" +
                "0. Exit\n" +
                "Thanks for using our program!";


        receiveInput(input);
        ManagementSystem.main(new String[0]);

        String stuOut = getOutput();

        stuOut = stuOut.replace("\r\n", "\n");
        assertEquals("The output was not as expected.",
                expected.trim(), stuOut.trim());

    }




    /*
    @Test(timeout = 1000)
    public void testExpectedThree() {

        File quizzesFile = new File("accessible_quizzes.txt");

        String input = "2\nManas\nhi\n1\n2\n0\nTestQuiz\nTestQuestion\n2\nAnswer1\nAnswer2\n1\nN\nN\n0";

        String expected = "Welcome to the System\n" +
                "1. Create account\n" +
                "2. Login\n" +
                "Enter your User ID:\n" +
                "Enter your Password:\n" +
                "Successfully logged in as Manas\n" +
                "1. View Courses\n" +
                "2. Create a Course\n" +
                "3. Account Setting\n" +
                "0. Exit\n" +
                "Select the course you want to view\n" +
                "Courses:\n" +
                "1. CS180\n" +
                "2. CS193\n" +
                "0. Back\n" +
                "There are currently no quizzes!\n" +
                "0. Create a new Quiz\n" +
                "Enter the name of the Quiz\n" +
                "Enter prompt of the question: \n" +
                "How many choices will question include?\n" +
                "1: \n" +
                "2: \n" +
                "What is a correct answer?(Enter a value from 1- # of questions)\n" +
                "Do you want to add another question? (Y/N)\n" +
                "Do you want to randomize this quiz?(Y/N)\n" +
                "Quiz has been successfully added!\n" +
                "Quiz created\n" +
                "1. View Courses\n" +
                "2. Create a Course\n" +
                "3. Account Setting\n" +
                "0. Exit\n" +
                "Thanks for using our program!";

        String time = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        File file = new File("CS193_TestQuiz_" + time + ".txt");
        file.delete();


        receiveInput(input);
        ManagementSystem.main(new String[0]);

        String stuOut = getOutput();

        stuOut = stuOut.replace("\r\n", "\n");

        assertEquals("The output was not as expected.",
                expected.trim(), stuOut.trim());

        try {
            Scanner sc = new Scanner(new FileReader("CS193.txt"));
            String s = "";
            while(sc.hasNext()){
                s += sc.nextLine();
            }

            String comparison = "CourseName: CS193" +
                    "IsRandom: false" +
                    "Name of Quiz: TestQuiz" +
                    "Prompt of Question: TestQuestion" +
                    "1. Answer1" +
                    "2. Answer2" +
                    "Correct Answer: 0";

            s.replace(" ", "");
            comparison.replace(" ", "");

            boolean equals = s.equals(comparison);

            assertTrue("The files were not equal", equals);

            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    } */

    @Before
    public void outputStart() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreInputAndOutput() {
        System.setIn(originalSysin);
        System.setOut(originalOutput);
    }

    private String getOutput() {
        return testOut.toString();
    }

    private void receiveInput(String str) {
        testIn = new ByteArrayInputStream(str.getBytes());
        System.setIn(testIn);
    }
}
