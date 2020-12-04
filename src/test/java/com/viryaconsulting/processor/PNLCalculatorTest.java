package com.viryaconsulting.processor;

import com.viryaconsulting.BaseTest;
import com.viryaconsulting.model.Trade;
import com.viryaconsulting.model.TradeTransactionResult;
import com.viryaconsulting.model.TradeTransactionSignature;
import com.viryaconsulting.model.TradeTransactionTree;
import com.viryaconsulting.util.ValidationUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public class PNLCalculatorTest extends BaseTest {

    private PNLCalculator pnlCalculator;

    @Before
    public void init() {
        pnlCalculator = Mockito.spy(new PNLCalculator());
    }

    @Test
    public void test_success_buildTradeQueueMap_with_TradeList_Argument() {

        List<Trade> mockTradeList = getMockTradeList();

        Map<TradeTransactionSignature, Queue<Trade>> tradeTransactionSignatureQueueMap = pnlCalculator
                .buildTradeQueueMap(mockTradeList);
        Map<TradeTransactionSignature, Queue<Trade>> expectedTransactionSignatureQueueMap = getMockTradeQueueMap();

        Mockito.verify(pnlCalculator).buildTradeQueueMap(mockTradeList);
        Mockito.verify(pnlCalculator, Mockito.times(1)).buildTradeQueueMap(mockTradeList);

        Assert.assertEquals(expectedTransactionSignatureQueueMap.size(), tradeTransactionSignatureQueueMap.size());
        Assert.assertEquals(expectedTransactionSignatureQueueMap.toString(), tradeTransactionSignatureQueueMap.toString());

    }

    @Test
    public void test_success_buildTradeTransactionTrees_with_tradeTransactionSignatureQueueMap_Argument() {

        Map<TradeTransactionSignature, Queue<Trade>> tradeTransactionSignatureQueueMap = getMockTradeQueueMap();
        List<TradeTransactionTree> expectedTransactionTreeList = getMockTransactionTreeList();

        validationUtilsMockedStatic.when(() -> ValidationUtils.isBuyOrSale(Mockito.anyString())).thenReturn(true);
        List<TradeTransactionTree> tradeTransactionTreeList = pnlCalculator.buildTradeTransactionTreeList(tradeTransactionSignatureQueueMap);

        Mockito.verify(pnlCalculator).buildTradeTransactionTreeList(tradeTransactionSignatureQueueMap);
        Mockito.verify(pnlCalculator, Mockito.times(1)).buildTradeTransactionTreeList(tradeTransactionSignatureQueueMap);

        Assert.assertNotNull(tradeTransactionTreeList);
        Assert.assertEquals(expectedTransactionTreeList.size(), tradeTransactionTreeList.size());
        Assert.assertEquals(expectedTransactionTreeList.toString(), tradeTransactionTreeList.toString());
    }

    @Test
    public void test_success_buildTradeTransactionTrees_with_tradeTransactionSignatureQueueMap_WrongBuyAndSell_Argument() {

        Map<TradeTransactionSignature, Queue<Trade>> mockTradeQueueMapWithInvalidBuyOrSell = getMockTradeQueueMapWithInvalidBuyOrSell();
        List<TradeTransactionTree> expectedTransactionTreeList = getMockTransactionTreeList();

        validationUtilsMockedStatic.when(() -> ValidationUtils.isBuyOrSale(Mockito.anyString()))
                .thenReturn(true, false, true, true, true, true, true, true);

        List<TradeTransactionTree> tradeTransactionTrees = pnlCalculator.buildTradeTransactionTreeList(mockTradeQueueMapWithInvalidBuyOrSell);

        Mockito.verify(pnlCalculator).buildTradeTransactionTreeList(mockTradeQueueMapWithInvalidBuyOrSell);
        Mockito.verify(pnlCalculator, Mockito.times(1)).buildTradeTransactionTreeList(mockTradeQueueMapWithInvalidBuyOrSell);

        Assert.assertNotNull(tradeTransactionTrees);
        Assert.assertEquals(expectedTransactionTreeList.size(), tradeTransactionTrees.size());
        Assert.assertEquals(expectedTransactionTreeList.toString(), tradeTransactionTrees.toString());
    }

    @Test
    public void test_success_buildTradeTransactionResult_with_transactionTreeArgument() {

        TradeTransactionTree mockTradeTransactionTree = getMockTradeTransactionTree();
        TradeTransactionResult expectedTradeTransactionResult = getMockTradeTransactionResult();

        TradeTransactionResult tradeTransactionResult = pnlCalculator.buildTradeTransactionResult(mockTradeTransactionTree);

        Mockito.verify(pnlCalculator).buildTradeTransactionResult(mockTradeTransactionTree);
        Mockito.verify(pnlCalculator, Mockito.times(1)).buildTradeTransactionResult(mockTradeTransactionTree);

        Assert.assertNotNull(expectedTradeTransactionResult);
        Assert.assertEquals(expectedTradeTransactionResult.toString(), tradeTransactionResult.toString());
    }

    @Test
    public void test_success_buildTradeTransactionResult_with_transactionTreeNoSaleArgument() {

        TradeTransactionTree mockTradeTransactionTreeNoSale = getMockTradeTransactionTreeNoSale();
        TradeTransactionResult expectedTradeTransactionResultNoSale = getMockTradeTransactionResultNoSale();

        TradeTransactionResult tradeTransactionResult = pnlCalculator.buildTradeTransactionResult(mockTradeTransactionTreeNoSale);

        Mockito.verify(pnlCalculator).buildTradeTransactionResult(mockTradeTransactionTreeNoSale);
        Mockito.verify(pnlCalculator, Mockito.times(1)).buildTradeTransactionResult(mockTradeTransactionTreeNoSale);

        Assert.assertNotNull(expectedTradeTransactionResultNoSale);
        Assert.assertEquals(expectedTradeTransactionResultNoSale.toString(), tradeTransactionResult.toString());
    }

    @Test
    public void test_success_buildTradeTransactionResult_with_transactionTreeMatchFirstSaleToFirstTradeArgument() {

        TradeTransactionTree mockTradeTransactionTreeMatchFirstSaleToFirstTrade =
                getMockTradeTransactionTreeMatchFirstSaleToFirstTrade();
        TradeTransactionResult expectedTradeTransactionResultMatchFirstSaleToFirstTrade =
                getMockTradeTransactionResultMatchFirstSaleToFirstTrade();

        TradeTransactionResult tradeTransactionResult = pnlCalculator.buildTradeTransactionResult(mockTradeTransactionTreeMatchFirstSaleToFirstTrade);

        Mockito.verify(pnlCalculator).buildTradeTransactionResult(mockTradeTransactionTreeMatchFirstSaleToFirstTrade);
        Mockito.verify(pnlCalculator, Mockito.times(1)).buildTradeTransactionResult(mockTradeTransactionTreeMatchFirstSaleToFirstTrade);

        Assert.assertNotNull(expectedTradeTransactionResultMatchFirstSaleToFirstTrade);
        Assert.assertEquals(expectedTradeTransactionResultMatchFirstSaleToFirstTrade.toString(), tradeTransactionResult.toString());
    }

    @Test
    public void test_success_buildTradeTransactionResult_with_transactionTreeEmptyBuyQueueArgument() {

        TradeTransactionTree mockTradeTransactionTreeEmptyBuyQueue =
                getMockTradeTransactionTreeEmptyBuyQueue();
        TradeTransactionResult emptyTradeTransactionResultEmptyBuyQueue =
                getMockTradeTransactionResultEmptyBuyQueue();

        TradeTransactionResult tradeTransactionResult = pnlCalculator.buildTradeTransactionResult(mockTradeTransactionTreeEmptyBuyQueue);

        Mockito.verify(pnlCalculator).buildTradeTransactionResult(mockTradeTransactionTreeEmptyBuyQueue);
        Mockito.verify(pnlCalculator, Mockito.times(1)).buildTradeTransactionResult(mockTradeTransactionTreeEmptyBuyQueue);

        Assert.assertNotNull(emptyTradeTransactionResultEmptyBuyQueue);
        Assert.assertEquals(emptyTradeTransactionResultEmptyBuyQueue.toString(), tradeTransactionResult.toString());
    }

    @Test
    public void test_success_buildTradeTransactionResultList_with_transactionTreeListArgument() {

        List<TradeTransactionTree> mockTransactionTreeList = getMockTransactionTreeList();
        List<TradeTransactionResult> expectedTradeTransactionResultList = getMockTradeTransactionResultList();

        Mockito.doReturn(
                expectedTradeTransactionResultList.get(0),
                expectedTradeTransactionResultList.get(1),
                expectedTradeTransactionResultList.get(2),
                expectedTradeTransactionResultList.get(3)).when(pnlCalculator).buildTradeTransactionResult(Mockito.any(TradeTransactionTree.class));

        pnlCalculator.buildTradeTransactionResultList(mockTransactionTreeList);

        Mockito.verify(pnlCalculator).buildTradeTransactionResultList(mockTransactionTreeList);
        Mockito.verify(pnlCalculator, Mockito.times(1)).buildTradeTransactionResultList(mockTransactionTreeList);
        Mockito.verify(pnlCalculator, Mockito.times(4)).buildTradeTransactionResult(Mockito.any(TradeTransactionTree.class));
    }

    @Test
    public void test_success_generateTransactionResultList_with_TradeList_argument(){
        List<Trade> mockTradeList = getMockTradeList();
        Map<TradeTransactionSignature, Queue<Trade>> mockTradeQueueMap = getMockTradeQueueMap();
        List<TradeTransactionTree> mockTransactionTreeList = getMockTransactionTreeList();
        List<TradeTransactionResult> mockTradeTransactionResultList = getMockTradeTransactionResultList();

        Mockito.doReturn(mockTradeQueueMap).when(pnlCalculator).buildTradeQueueMap(mockTradeList);
        Mockito.doReturn(mockTransactionTreeList).when(pnlCalculator).buildTradeTransactionTreeList(mockTradeQueueMap);
        Mockito.doReturn(mockTradeTransactionResultList).when(pnlCalculator).buildTradeTransactionResultList(mockTransactionTreeList);

        pnlCalculator.generateTransactionResultList(mockTradeList);

        Mockito.verify(pnlCalculator).buildTradeTransactionResultList(mockTransactionTreeList);
        Mockito.verify(pnlCalculator, Mockito.times(1)).buildTradeQueueMap(mockTradeList);
        Mockito.verify(pnlCalculator, Mockito.times(1)).buildTradeTransactionTreeList(mockTradeQueueMap);
        Mockito.verify(pnlCalculator, Mockito.times(1)).buildTradeTransactionResultList(mockTransactionTreeList);


    }
}
