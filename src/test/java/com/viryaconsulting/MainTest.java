package com.viryaconsulting;

import com.viryaconsulting.processor.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;

import static com.viryaconsulting.util.Constants.INVOKE_METHOD_CALLED_PRINT_MSG;
import static org.junit.Assert.assertEquals;

public class MainTest extends BaseTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Mock
    Worker worker;

    @Before
    public void init() throws IOException {
        System.setOut(new PrintStream(outContent));
        worker = Mockito.mock(Worker.class);

    }

    @Test
    public void test_invoke() throws IOException, URISyntaxException {
        File mockResultFile = getMockResultFile();
        String fileName = "input.dat";
        Mockito.when(worker.process(fileName)).thenReturn(mockResultFile);
        Main.invoke(worker, fileName);
        assertEquals(INVOKE_METHOD_CALLED_PRINT_MSG, outContent.toString().trim());

    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}
