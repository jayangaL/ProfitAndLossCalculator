package com.viryaconsulting;

import com.viryaconsulting.model.*;
import com.viryaconsulting.util.ResourceUtils;
import com.viryaconsulting.util.ValidationUtils;
import org.junit.Before;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.*;
import java.util.*;

public abstract class BaseTest {

    protected static MockedStatic<ResourceUtils> resourceUtilsMockedStatic;
    protected static MockedStatic<ValidationUtils> validationUtilsMockedStatic;

    public abstract void init() throws IOException;

    @Before
    public void prepare() {
        if (resourceUtilsMockedStatic == null)
            resourceUtilsMockedStatic = Mockito.mockStatic(ResourceUtils.class);
        if (validationUtilsMockedStatic == null)
            validationUtilsMockedStatic = Mockito.mockStatic(ValidationUtils.class);
    }

    protected Trade createMockTrade(int index, String tradeId, String stockSymbol, double quantity, char buyOrSell,
                                    double price) {
        Trade trade = new Trade();
        trade.setIndex(index);
        trade.setTraderId(tradeId);
        trade.setStockSymbol(stockSymbol);
        trade.setQuantity(quantity);
        trade.setBuyOrSell(buyOrSell);
        trade.setPrice(price);
        return trade;
    }

    protected TradeTransactionSignature createMockTradeTransactionSignature
            (Trade trade) {
        return new TradeTransactionSignature(trade);
    }

    protected Queue<Trade> createMockTradeQueue(Trade... trades) {
        Queue<Trade> tradeList = new LinkedList<>();
        for (Trade trade : trades) {
            tradeList.add(trade);
        }
        return tradeList;
    }

    protected TradeTransactionTree createMockTradeTransactionTree(TradeTransactionSignature tradeTransactionSignature,
                                                                  Queue<Trade> saleQueue, Queue<Trade> buyQueue) {

        TradeTransactionTree tradeTransactionTree = new TradeTransactionTree
                (new TradeItemSignature(tradeTransactionSignature));
        tradeTransactionTree.setSaleQueue(saleQueue);
        tradeTransactionTree.setBuyQueue(buyQueue);

        return tradeTransactionTree;

    }

    protected TradeTransactionResult getMockTradeTransactionResult(String tradeId, String stockSymbol, double pnl,
                                                                   double residual) {
        TradeTransactionResult tradeTransactionResult = new TradeTransactionResult();
        tradeTransactionResult.setTraderId(tradeId);
        tradeTransactionResult.setStockSymbol(stockSymbol);
        tradeTransactionResult.setPnl(pnl);
        tradeTransactionResult.setResidual(residual);
        return tradeTransactionResult;
    }

    protected List<Trade> getMockTradeList() {

        List<Trade> tradesList = new ArrayList<>();
        int index = 0;
        //T1,IBM,200.0,B,4.50
        tradesList.add(createMockTrade(++index, "T1", "IBM", 200.0, 'B', 4.50));
        //T2,IBM,200.0,B,4.50
        tradesList.add(createMockTrade(++index, "T2", "IBM", 200.0, 'B', 4.50));
        //T1,IBM,200.0,B,4.60
        tradesList.add(createMockTrade(++index, "T1", "IBM", 200.0, 'B', 4.60));
        //T1,MSFT,200.0,B,8.10
        tradesList.add(createMockTrade(++index, "T1", "MSFT", 200.0, 'B', 8.10));
        //T2,IBM,300.0,B,4.60
        tradesList.add(createMockTrade(++index, "T2", "IBM", 300.0, 'B', 4.60));
        //T1,IBM,200,S,4.75
        tradesList.add(createMockTrade(++index, "T1", "IBM", 200, 'S', 4.75));
        //T2,IBM,100,S,4.70
        tradesList.add(createMockTrade(++index, "T2", "IBM", 100, 'S', 4.70));
        //T3,MSFT,50,S,6.75
        tradesList.add(createMockTrade(++index, "T3", "MSFT", 50, 'S', 6.75));
        //T2,IBM,300,S,4.85
        tradesList.add(createMockTrade(++index, "T2", "IBM", 300, 'S', 4.85));
        //T3,MSFT,30,B,6.95
        tradesList.add(createMockTrade(++index, "T3", "MSFT", 30, 'B', 6.95));

        return tradesList;
    }

    protected List<TradeTransactionResult> getMockTradeTransactionResultList() {
        /*[TradeTransactionResult{traderId='T1', stockSymbol='MSFT', pnl=0.0, residual=200.0},
         TradeTransactionResult{traderId='T2', stockSymbol='IBM', pnl=104.99999999999994, residual=100.0},
         TradeTransactionResult{traderId='T1', stockSymbol='IBM', pnl=50.0, residual=200.0},
         TradeTransactionResult{traderId='T3', stockSymbol='MSFT', pnl=-6.0, residual=0.0}]*/
        List<TradeTransactionResult> transactionResultList = new ArrayList<>();
        transactionResultList.add(getMockTradeTransactionResult("T1", "MSFT", 0.0, 200.0));
        transactionResultList.add(getMockTradeTransactionResult("T2", "IBM", 104.99999999999994, 100.0));
        transactionResultList.add(getMockTradeTransactionResult("T1", "IBM", 50.0, 200.0));
        transactionResultList.add(getMockTradeTransactionResult("T3", "MSFT", -6.0, 0.0));

        return transactionResultList;

    }

    protected File getMockResultFile() {
        return new File(getClass().getResource("/result-test.dat").getFile());
    }

    protected File getMockInputFile() {
        return new File(getClass().getResource("/trades.dat").getFile());
    }

    protected String[] getMockLines() {

        return new String[]{
                "T1,IBM,200.0,B,4.50", "T2,IBM,200.0,B,4.50", "T1,IBM,200.0,B,4.60", "T1,MSFT,200.0,B,8.10",
                "T2,IBM,300.0,B,4.60", "T1,IBM,200,S,4.75", "T2,IBM,100,S,4.70", "T3,MSFT,50,S,6.75",
                "T2,IBM,300,S,4.85", "T3,MSFT,30,B,6.95"
        };
    }

    protected String[] getMockSingleLine() {
        return new String[]{
                "T1,IBM,200.0,B,4.50"
        };
    }

    protected String[] getMockParametersOfLine() {
        return new String[]{
                "T1", "IBM", "200.0", "B", "4.50"
        };
    }

    protected String[] getMockLengthNoneFiveParametersOfLine() {
        return new String[]{
                "T1", "IBM", "200.0", "B"
        };
    }

    protected String[] getMockParametersOfLineInvalidQuantity() {
        return new String[]{
                "T1", "IBM", "X", "B", "4.50"
        };
    }

    protected String[] getMockParametersOfLineInvalidBuyOrSale() {
        return new String[]{
                "T1", "IBM", "200.0", "D", "-4.50"
        };
    }

    protected String[] getMockParametersOfLineInvalidPrice() {
        return new String[]{
                "T1", "IBM", "200.0", "B", "-4.50"
        };
    }

    protected String getFileContent(File input) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(input);
        StringBuilder stringBuilder = new StringBuilder();
        while (fileInputStream.available() != 0) {
            stringBuilder.append(fileInputStream.read());
        }
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    protected Map<TradeTransactionSignature, Queue<Trade>> getMockTradeQueueMap() {

        List<Trade> mockTradeList = getMockTradeList();
        Map<TradeTransactionSignature, Queue<Trade>> tradeTransactionSignatureQueueMap = new HashMap<>();
        tradeTransactionSignatureQueueMap.put(createMockTradeTransactionSignature(mockTradeList.get(3)), createMockTradeQueue(mockTradeList.get(3)));
        tradeTransactionSignatureQueueMap.put(createMockTradeTransactionSignature(mockTradeList.get(1)), createMockTradeQueue(mockTradeList.get(1), mockTradeList.get(4)));
        tradeTransactionSignatureQueueMap.put(createMockTradeTransactionSignature(mockTradeList.get(5)), createMockTradeQueue(mockTradeList.get(5)));
        tradeTransactionSignatureQueueMap.put(createMockTradeTransactionSignature(mockTradeList.get(0)), createMockTradeQueue(mockTradeList.get(0), mockTradeList.get(2)));
        tradeTransactionSignatureQueueMap.put(createMockTradeTransactionSignature(mockTradeList.get(6)), createMockTradeQueue(mockTradeList.get(6), mockTradeList.get(8)));
        tradeTransactionSignatureQueueMap.put(createMockTradeTransactionSignature(mockTradeList.get(7)), createMockTradeQueue(mockTradeList.get(7)));
        tradeTransactionSignatureQueueMap.put(createMockTradeTransactionSignature(mockTradeList.get(9)), createMockTradeQueue(mockTradeList.get(9)));
        return tradeTransactionSignatureQueueMap;
    }

    protected List<TradeTransactionTree> getMockTransactionTreeList() {
        List<TradeTransactionTree> tradeTransactionTreeList = new ArrayList<>();
        List<Trade> mockTradeList = getMockTradeList();

        tradeTransactionTreeList.add(createMockTradeTransactionTree(
                createMockTradeTransactionSignature(mockTradeList.get(3)),
                createMockTradeQueue(),
                createMockTradeQueue(mockTradeList.get(3))));
        tradeTransactionTreeList.add(createMockTradeTransactionTree(
                createMockTradeTransactionSignature(mockTradeList.get(1)),
                createMockTradeQueue(mockTradeList.get(6), mockTradeList.get(8)),
                createMockTradeQueue(mockTradeList.get(1), mockTradeList.get(4))));
        tradeTransactionTreeList.add(createMockTradeTransactionTree(
                createMockTradeTransactionSignature(mockTradeList.get(0)),
                createMockTradeQueue(mockTradeList.get(5)),
                createMockTradeQueue(mockTradeList.get(0), mockTradeList.get(2))));
        tradeTransactionTreeList.add(createMockTradeTransactionTree(
                createMockTradeTransactionSignature(mockTradeList.get(7)),
                createMockTradeQueue(mockTradeList.get(7)),
                createMockTradeQueue(mockTradeList.get(9))));

        return tradeTransactionTreeList;
    }

    protected Map<TradeTransactionSignature, Queue<Trade>> getMockTradeQueueMapWithInvalidBuyOrSell() {

        List<Trade> mockTradeList = getMockTradeList();
        Map<TradeTransactionSignature, Queue<Trade>> tradeTransactionSignatureQueueMap = new HashMap<>();
        tradeTransactionSignatureQueueMap.put(createMockTradeTransactionSignature(mockTradeList.get(3)),
                createMockTradeQueue(mockTradeList.get(3)));
        tradeTransactionSignatureQueueMap.put(createMockTradeTransactionSignature(mockTradeList.get(1)),
                createMockTradeQueue(mockTradeList.get(1), mockTradeList.get(4)));
        tradeTransactionSignatureQueueMap.put(createMockTradeTransactionSignature(mockTradeList.get(5)),
                createMockTradeQueue(mockTradeList.get(5)));
        tradeTransactionSignatureQueueMap.put(createMockTradeTransactionSignature(mockTradeList.get(0)),
                createMockTradeQueue(mockTradeList.get(0), mockTradeList.get(2)));
        tradeTransactionSignatureQueueMap.put(createMockTradeTransactionSignature(mockTradeList.get(6)),
                createMockTradeQueue(mockTradeList.get(6), mockTradeList.get(8)));
        tradeTransactionSignatureQueueMap.put(createMockTradeTransactionSignature(mockTradeList.get(7)),
                createMockTradeQueue(mockTradeList.get(7)));
        tradeTransactionSignatureQueueMap.put(createMockTradeTransactionSignature(mockTradeList.get(9)),
                createMockTradeQueue(mockTradeList.get(9)));
        tradeTransactionSignatureQueueMap.put(createMockTradeTransactionSignature(
                createMockTrade(10, "T1", "IBM", 0, '0', 0)),
                createMockTradeQueue(createMockTrade(10, "T1", "IBM", 0, '0', 0),
                        createMockTrade(10, "T1", "IBM", 0, '0', 0)));
        return tradeTransactionSignatureQueueMap;
    }

    protected TradeTransactionTree getMockTradeTransactionTree() {
        return getMockTransactionTreeList().get(1);
    }

    protected TradeTransactionResult getMockTradeTransactionResult(){
        return getMockTradeTransactionResultList().get(1);
    }

    protected TradeTransactionTree getMockTradeTransactionTreeNoSale() {
        return getMockTransactionTreeList().get(0);
    }

    protected TradeTransactionResult getMockTradeTransactionResultNoSale(){
        return getMockTradeTransactionResultList().get(0);
    }

    protected TradeTransactionTree getMockTradeTransactionTreeMatchFirstSaleToFirstTrade() {
        return getMockTransactionTreeList().get(2);
    }

    protected TradeTransactionResult getMockTradeTransactionResultMatchFirstSaleToFirstTrade(){
        return getMockTradeTransactionResultList().get(2);
    }

    protected TradeTransactionTree getMockTradeTransactionTreeEmptyBuyQueue() {
        return getMockTransactionTreeList().get(3);
    }

    protected TradeTransactionResult getMockTradeTransactionResultEmptyBuyQueue(){
        return getMockTradeTransactionResultList().get(3);
    }

    //[TradeTransactionTree{tradeItemSignature=TradeItemSignature{traderId='T1', stockSymbol='MSFT'},
    // saleQueue=[],
    // buyQueue=[Trade{traderId='T1', stockSymbol='MSFT', quantity=200.0, buyOrSell=B, price=8.1}]},
    //
    // TradeTransactionTree{tradeItemSignature=TradeItemSignature{traderId='T2', stockSymbol='IBM'},
    // saleQueue=[Trade{traderId='T2', stockSymbol='IBM', quantity=100.0, buyOrSell=S, price=4.7}, Trade{traderId='T2', stockSymbol='IBM', quantity=300.0, buyOrSell=S, price=4.85}],
    // buyQueue=[Trade{traderId='T2', stockSymbol='IBM', quantity=200.0, buyOrSell=B, price=4.5}, Trade{traderId='T2', stockSymbol='IBM', quantity=300.0, buyOrSell=B, price=4.6}]},
    //
    // TradeTransactionTree{tradeItemSignature=TradeItemSignature{traderId='T1', stockSymbol='IBM'},
    // saleQueue=[Trade{traderId='T1', stockSymbol='IBM', quantity=200.0, buyOrSell=S, price=4.75}],
    // buyQueue=[Trade{traderId='T1', stockSymbol='IBM', quantity=200.0, buyOrSell=B, price=4.5}, Trade{traderId='T1', stockSymbol='IBM', quantity=200.0, buyOrSell=B, price=4.6}]},
    //
    // TradeTransactionTree{tradeItemSignature=TradeItemSignature{traderId='T3', stockSymbol='MSFT'},
    // saleQueue=[Trade{traderId='T3', stockSymbol='MSFT', quantity=50.0, buyOrSell=S, price=6.75}],
    // buyQueue=[Trade{traderId='T3', stockSymbol='MSFT', quantity=30.0, buyOrSell=B, price=6.95}]}]


    //0-T1,IBM,200.0,B,4.50
    //1-T2,IBM,200.0,B,4.50
    //2-T1,IBM,200.0,B,4.60
    //3-T1,MSFT,200.0,B,8.10
    //4-T2,IBM,300.0,B,4.60
    //5-T1,IBM,200,S,4.75
    //6-T2,IBM,100,S,4.70
    //7-T3,MSFT,50,S,6.75
    //8-T2,IBM,300,S,4.85
    //9T3,MSFT,30,B,6.95

//{TradeSignature{traderId='T1', stockSymbol='MSFT', buyOrSell=B}=
// [Trade{traderId='T1', stockSymbol='MSFT', quantity=200.0, buyOrSell=B, price=8.1}],

// TradeSignature{traderId='T2', stockSymbol='IBM', buyOrSell=B}=
// [Trade{traderId='T2', stockSymbol='IBM', quantity=200.0, buyOrSell=B, price=4.5}, Trade{traderId='T2', stockSymbol='IBM', quantity=300.0, buyOrSell=B, price=4.6}],

// TradeSignature{traderId='T1', stockSymbol='IBM', buyOrSell=S}=
// [Trade{traderId='T1', stockSymbol='IBM', quantity=200.0, buyOrSell=S, price=4.75}],

// TradeSignature{traderId='T1', stockSymbol='IBM', buyOrSell=B}=
// [Trade{traderId='T1', stockSymbol='IBM', quantity=200.0, buyOrSell=B, price=4.5}, Trade{traderId='T1', stockSymbol='IBM', quantity=200.0, buyOrSell=B, price=4.6}],

// TradeSignature{traderId='T2', stockSymbol='IBM', buyOrSell=S}=
// [Trade{traderId='T2', stockSymbol='IBM', quantity=100.0, buyOrSell=S, price=4.7}, Trade{traderId='T2', stockSymbol='IBM', quantity=300.0, buyOrSell=S, price=4.85}],

// TradeSignature{traderId='T3', stockSymbol='MSFT', buyOrSell=S}=
// [Trade{traderId='T3', stockSymbol='MSFT', quantity=50.0, buyOrSell=S, price=6.75}],

// TradeSignature{traderId='T3', stockSymbol='MSFT', buyOrSell=B}=
// [Trade{traderId='T3', stockSymbol='MSFT', quantity=30.0, buyOrSell=B, price=6.95}]}

}
