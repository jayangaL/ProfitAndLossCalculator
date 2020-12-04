package com.viryaconsulting.processor;

import com.viryaconsulting.BaseTest;
import com.viryaconsulting.model.TradeTransactionResult;
import com.viryaconsulting.util.ResourceUtils;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ResultExporterTest extends BaseTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private ResultExporter resultExporter;

    private File resultFile;

    @Before
    public void init() throws IOException {
        resultExporter = Mockito.spy(new ResultExporter());
        resultFile = temporaryFolder.newFile("result.dat");
    }

    @Test
    public void test_exportDat_Success_with_tradeTransactionResults_and_outputFile_Argument() throws IOException {

        List<TradeTransactionResult> mockTradeTransactionResultList = getMockTradeTransactionResultList();
        File expected = getMockResultFile();

        File result = resultExporter.exportDat(mockTradeTransactionResultList, resultFile);

        Mockito.verify(resultExporter).exportDat(mockTradeTransactionResultList, resultFile);
        Mockito.verify(resultExporter, Mockito.times(1)).exportDat(mockTradeTransactionResultList, resultFile);

        Assert.assertNotNull(result);
        Assert.assertEquals(getFileContent(expected), getFileContent(result));
    }

    @Test
    public void test_exportDat_Success_with_tradeTransactionResultsArgument() throws IOException {

        List<TradeTransactionResult> mockTradeTransactionResultList = getMockTradeTransactionResultList();

        File expected = getMockResultFile();
        File input = temporaryFolder.newFile("result-file.dat");
        resourceUtilsMockedStatic.when(() -> ResourceUtils.createTemporyFile(Mockito.anyString(), Mockito.anyString())).thenReturn(input);

        Mockito.doReturn(expected).when(resultExporter).exportDat(mockTradeTransactionResultList, input);

        resultExporter.exportDat(mockTradeTransactionResultList);

        Mockito.verify(resultExporter).exportDat(mockTradeTransactionResultList);
        Mockito.verify(resultExporter, Mockito.times(1)).exportDat(mockTradeTransactionResultList);
        Mockito.verify(resultExporter).exportDat(mockTradeTransactionResultList, input);
        Mockito.verify(resultExporter, Mockito.times(1)).exportDat(mockTradeTransactionResultList, input);
    }

}
