import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RunTestMethods {

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
}
