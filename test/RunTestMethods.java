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
    public void testExpectedOne() {
        // Set the input
        // Separate each input with a newline (\n).
        String input = "Line One\nLine Two\n";

        // Pair the input with the expected result
        String expected = "Insert the expected output here"

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

    public void outputStart() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

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
