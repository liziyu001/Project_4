import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

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
        assertEquals("Error message if output is incorrect, customize as needed",
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
        assertEquals("Error message if output is incorrect, customize as needed",
                expected.trim(), stuOut.trim());

    }

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
