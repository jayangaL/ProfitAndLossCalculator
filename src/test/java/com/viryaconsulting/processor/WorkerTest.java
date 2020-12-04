package com.viryaconsulting.processor;

import com.viryaconsulting.BaseTest;
import com.viryaconsulting.model.Trade;
import com.viryaconsulting.model.TradeTransactionResult;
import com.viryaconsulting.util.ResourceUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class WorkerTest extends BaseTest {

    @Mock
    private TradeLoader tradeLoader;
    @Mock
    private PNLCalculator pnlCalculator;
    @Mock
    private ResultExporter resultExporter;

    private Worker worker;

    @Before
    public void init() {
        tradeLoader = Mockito.mock(TradeLoader.class);
        pnlCalculator = Mockito.mock(PNLCalculator.class);
        resultExporter = Mockito.mock(ResultExporter.class);
        worker = new Worker(tradeLoader, pnlCalculator, resultExporter);
    }

    @Test
    public void test_process() throws IOException, URISyntaxException {

        String filename = "/trades.dat";
        File input = new File(getClass().getResource(filename).getFile());
        resourceUtilsMockedStatic.when(() -> ResourceUtils.getFileFromResource(filename)).thenReturn(input);

        List<Trade> tradeList = getMockTradeList();
        Mockito.when(tradeLoader.loadTrades(input)).thenReturn(tradeList);

        List<TradeTransactionResult> tradeTransactionResultList = getMockTradeTransactionResultList();
        Mockito.when(pnlCalculator.generateTransactionResultList(tradeList)).thenReturn(tradeTransactionResultList);

        File mockResultFile = getMockResultFile();
        Mockito.when(resultExporter.exportDat(tradeTransactionResultList)).thenReturn(mockResultFile);

        File output = worker.process(filename);

        Mockito.verify(tradeLoader).loadTrades(input);
        Mockito.verify(tradeLoader, Mockito.times(1)).loadTrades(input);

        Mockito.verify(pnlCalculator).generateTransactionResultList(tradeList);
        Mockito.verify(pnlCalculator, Mockito.times(1)).generateTransactionResultList(tradeList);

        Mockito.verify(resultExporter).exportDat(tradeTransactionResultList);
        Mockito.verify(resultExporter, Mockito.times(1)).exportDat(tradeTransactionResultList);

    }

}
