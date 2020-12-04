package com.viryaconsulting;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;

import static com.viryaconsulting.util.Constants.INVOKE_METHOD_CALLED_PRINT_MSG;

public class ITMain extends BaseTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void init() throws IOException {
        System.setOut(new PrintStream(outContent));
        if (resourceUtilsMockedStatic != null) {
            resourceUtilsMockedStatic.close();
            resourceUtilsMockedStatic = null;
        }
        if (validationUtilsMockedStatic != null) {
            validationUtilsMockedStatic.close();
            validationUtilsMockedStatic = null;
        }
    }

    @Test
    public void test_mainSuccess_NoArgument() throws IOException, URISyntaxException {
        Main.main(new String[0]);
        String output = outContent.toString();
        Assert.assertTrue(output.contains("T1,MSFT,0,200"));
        Assert.assertTrue(output.contains("T2,IBM,105,100"));
        Assert.assertTrue(output.contains("T1,IBM,50,200"));
        Assert.assertTrue(output.contains("T3,MSFT,-6,0"));
        Assert.assertTrue(output.contains(INVOKE_METHOD_CALLED_PRINT_MSG));
    }

    @Test
    public void test_mainSuccess_With_File_Argument_Argument() throws IOException, URISyntaxException {
        File mockInputFile = getMockInputFile();
        Main.main(new String[]{mockInputFile.getAbsolutePath()});
        String output = outContent.toString();
        Assert.assertTrue(output.contains("T1,MSFT,0,200"));
        Assert.assertTrue(output.contains("T2,IBM,105,100"));
        Assert.assertTrue(output.contains("T1,IBM,50,200"));
        Assert.assertTrue(output.contains("T3,MSFT,-6,0"));
        Assert.assertTrue(output.contains(INVOKE_METHOD_CALLED_PRINT_MSG));

    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}
