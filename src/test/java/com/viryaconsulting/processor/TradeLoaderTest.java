package com.viryaconsulting.processor;

import com.viryaconsulting.BaseTest;
import com.viryaconsulting.exception.InvalidArgumentException;
import com.viryaconsulting.exception.InvalidNumberOfArgumentsException;
import com.viryaconsulting.exception.NullTradeInputException;
import com.viryaconsulting.model.Trade;
import com.viryaconsulting.util.ResourceUtils;
import com.viryaconsulting.util.ValidationUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.viryaconsulting.util.Constants.*;

public class TradeLoaderTest extends BaseTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Spy
    private TradeLoader tradeLoader;

    @Before
    public void init() {
        tradeLoader = Mockito.spy(new TradeLoader());
    }

    @Test
    public void test_success_loadTrades_file_argument() throws IOException {

        File mockInputFile = getMockInputFile();
        String[] mockLines = getMockLines();

        List<Trade> expected = getMockTradeList();

        resourceUtilsMockedStatic.when(() -> ResourceUtils.getLinesFromFile(mockInputFile)).thenReturn(mockLines);
        Mockito.doReturn(expected).when(tradeLoader).loadTrades(mockLines);
//        resourceUtilsMockedStatic.when(() -> ResourceUtils.getParametersFromLine(mockLines[0])).thenReturn(mockParametersOfLine);

//        validationUtilsMockedStatic.when(() -> ValidationUtils.isNonEmptyString(Mockito.anyString())).thenReturn(true);
//        validationUtilsMockedStatic.when(() -> ValidationUtils.isBuyOrSale(Mockito.anyString())).thenReturn(true);
//        validationUtilsMockedStatic.when(() -> ValidationUtils.isNumber(Mockito.anyString())).thenReturn(true);
//        Mockito.when(tradeLoader.createTrade(Mockito.anyString())).thenReturn(createMockTrade(1, "T1", "IBM", 200.0, 'B', 4.50));


        List<Trade> trades = tradeLoader.loadTrades(mockInputFile);
        Mockito.verify(tradeLoader).loadTrades(mockInputFile);
        Mockito.verify(tradeLoader, Mockito.times(1)).loadTrades(mockLines);

    }

    @Test
    public void test_success_loadTrades_StringLines_argument() {

        File mockInputFile = getMockInputFile();
        String[] mockLines = getMockSingleLine();
        List<Trade> mockTradeList = getMockTradeList();

        resourceUtilsMockedStatic.when(() -> ResourceUtils.getLinesFromFile(mockInputFile)).thenReturn(mockLines);
        Mockito.doReturn(mockTradeList.get(0)).when(tradeLoader).createTrade(mockLines[0]);

        List<Trade> trades = tradeLoader.loadTrades(mockLines);
        Mockito.verify(tradeLoader).loadTrades(mockLines);
        Mockito.verify(tradeLoader, Mockito.times(1)).loadTrades(mockLines);
        Mockito.verify(tradeLoader).createTrade(mockLines[0]);
        Mockito.verify(tradeLoader, Mockito.times(1)).createTrade(mockLines[0]);
        Assert.assertNotNull(trades);
        Assert.assertEquals(1, trades.size());
        Assert.assertEquals(1, trades.get(0).getIndex());

    }

    @Test
    public void test_success_createTrade_StringLine_argument() {

        String[] mockSingleLine = getMockSingleLine();
        String[] mockParametersOfLine = getMockParametersOfLine();
        List<Trade> mockTradeList = getMockTradeList();

        resourceUtilsMockedStatic.when(() -> ResourceUtils.getParametersFromLine(mockSingleLine[0])).thenReturn(mockParametersOfLine);
        Mockito.doReturn(mockTradeList.get(0)).when(tradeLoader).createTrade(mockParametersOfLine);

        tradeLoader.createTrade(mockSingleLine[0]);

        Mockito.verify(tradeLoader).createTrade(mockSingleLine[0]);
        Mockito.verify(tradeLoader, Mockito.times(1)).createTrade(mockSingleLine[0]);
        Mockito.verify(tradeLoader).createTrade(mockParametersOfLine);
        Mockito.verify(tradeLoader, Mockito.times(1)).createTrade(mockParametersOfLine);

    }

    @Test
    public void test_success_createTrade_StringParameters_arguments() {

        String[] mockParametersOfLine = getMockParametersOfLine();

        Mockito.doReturn(true).when(tradeLoader).validateTrade(mockParametersOfLine);

        tradeLoader.createTrade(mockParametersOfLine);

        Mockito.verify(tradeLoader).createTrade(mockParametersOfLine);
        Mockito.verify(tradeLoader, Mockito.times(1)).createTrade(mockParametersOfLine);
        Mockito.verify(tradeLoader).validateTrade(mockParametersOfLine);
        Mockito.verify(tradeLoader, Mockito.times(1)).validateTrade(mockParametersOfLine);

    }

    @Test
    public void test_success_validateTrade_StringArray() {

        String[] mockParametersOfLine = getMockParametersOfLine();

        validationUtilsMockedStatic.when(() -> ValidationUtils.isNonEmptyString(Mockito.anyString())).thenReturn(true);
        validationUtilsMockedStatic.when(() -> ValidationUtils.isBuyOrSale(Mockito.anyString())).thenReturn(true);
        validationUtilsMockedStatic.when(() -> ValidationUtils.isNumber(Mockito.anyString())).thenReturn(true);
        validationUtilsMockedStatic.when(() -> ValidationUtils.isPositiveNumber(Mockito.anyString())).thenReturn(true);

        Assert.assertTrue(tradeLoader.validateTrade(mockParametersOfLine));
    }

    @Test
    public void test_Exception_validateTrade_null() {

        expectedException.expect(NullTradeInputException.class);
        expectedException.expectMessage(NULL_TRADE_PARAMETERS);

        tradeLoader.validateTrade(null);

        Mockito.verify(tradeLoader).validateTrade(null);
        Mockito.verify(tradeLoader, Mockito.times(1)).validateTrade(null);
    }

    @Test
    public void test_Exception_validateTrade_LengthFour() {

        String[] mockLengthNoneFiveParametersOfLine = getMockLengthNoneFiveParametersOfLine();

        expectedException.expect(InvalidNumberOfArgumentsException.class);
        expectedException.expectMessage(String.format(WRONG_PARAMETER_LENGTH, 4, Arrays.toString(mockLengthNoneFiveParametersOfLine)));

        tradeLoader.validateTrade(mockLengthNoneFiveParametersOfLine);

        Mockito.verify(tradeLoader).validateTrade(mockLengthNoneFiveParametersOfLine);
        Mockito.verify(tradeLoader, Mockito.times(1)).validateTrade(mockLengthNoneFiveParametersOfLine);
    }

    @Test
    public void test_Exception_validateTrade_EmptyTradeId() {

        String[] mockParametersOfLine = getMockParametersOfLine();

        expectedException.expect(InvalidArgumentException.class);
        expectedException.expectMessage(String.format(EMPTY_PARAMETER_VALUE, 1, TRADE_ID));

        validationUtilsMockedStatic.when(() -> ValidationUtils.isNonEmptyString(Mockito.anyString())).thenReturn(false);

        tradeLoader.validateTrade(mockParametersOfLine);

        Mockito.verify(tradeLoader).validateTrade(mockParametersOfLine);
        Mockito.verify(tradeLoader, Mockito.times(1)).validateTrade(mockParametersOfLine);
    }

    @Test
    public void test_Exception_validateTrade_EmptyStockSymbol() {

        String[] mockParametersOfLine = getMockParametersOfLine();

        expectedException.expect(InvalidArgumentException.class);
        expectedException.expectMessage(String.format(EMPTY_PARAMETER_VALUE, 2, STOCK_SYMBOL));

        validationUtilsMockedStatic.when(() -> ValidationUtils.isNonEmptyString(Mockito.anyString())).thenReturn(true, false);

        tradeLoader.validateTrade(mockParametersOfLine);

        Mockito.verify(tradeLoader).validateTrade(mockParametersOfLine);
        Mockito.verify(tradeLoader, Mockito.times(1)).validateTrade(mockParametersOfLine);
    }

    @Test
    public void test_Exception_validateTrade_EmptyQuantity() {

        String[] mockParametersOfLine = getMockParametersOfLine();

        expectedException.expect(InvalidArgumentException.class);
        expectedException.expectMessage(String.format(EMPTY_PARAMETER_VALUE, 3, QUANTITY));

        validationUtilsMockedStatic.when(() -> ValidationUtils.isNonEmptyString(Mockito.anyString())).thenReturn(true, true, false);

        tradeLoader.validateTrade(mockParametersOfLine);

        Mockito.verify(tradeLoader).validateTrade(mockParametersOfLine);
        Mockito.verify(tradeLoader, Mockito.times(1)).validateTrade(mockParametersOfLine);
    }

    @Test
    public void test_Exception_validateTrade_InvalidQuantity() {

        String[] mockParametersOfLineInvalidQuantity = getMockParametersOfLineInvalidQuantity();

        expectedException.expect(InvalidArgumentException.class);
        System.out.println(String.format(WRONG_PARAMETER_VALUE, 3, NUMBER, QUANTITY, mockParametersOfLineInvalidQuantity[2]));
        expectedException.expectMessage(String.format(WRONG_PARAMETER_VALUE, 3, NUMBER, QUANTITY, mockParametersOfLineInvalidQuantity[2]));

        validationUtilsMockedStatic.when(() -> ValidationUtils.isNonEmptyString(Mockito.anyString())).thenReturn(true, true, true);
        validationUtilsMockedStatic.when(() -> ValidationUtils.isNumber(Mockito.anyString())).thenReturn(false);

        tradeLoader.validateTrade(mockParametersOfLineInvalidQuantity);

        Mockito.verify(tradeLoader).validateTrade(mockParametersOfLineInvalidQuantity);
        Mockito.verify(tradeLoader, Mockito.times(1)).validateTrade(mockParametersOfLineInvalidQuantity);
    }

    @Test
    public void test_Exception_validateTrade_EmptyBuyOrSale() {

        String[] mockParametersOfLineInvalidBuyOrSale = getMockParametersOfLineInvalidBuyOrSale();

        expectedException.expect(InvalidArgumentException.class);
        expectedException.expectMessage(String.format(EMPTY_PARAMETER_VALUE, 4, BUY_OR_SALE));

        validationUtilsMockedStatic.when(() -> ValidationUtils.isNonEmptyString(Mockito.anyString())).thenReturn(true, true, true, false);
        validationUtilsMockedStatic.when(() -> ValidationUtils.isNumber(Mockito.anyString())).thenReturn(true);

        tradeLoader.validateTrade(mockParametersOfLineInvalidBuyOrSale);

        Mockito.verify(tradeLoader).validateTrade(mockParametersOfLineInvalidBuyOrSale);
        Mockito.verify(tradeLoader, Mockito.times(1)).validateTrade(mockParametersOfLineInvalidBuyOrSale);
    }

    @Test
    public void test_Exception_validateTrade_InvalidBuyOrSale() {

        String[] mockParametersOfLineInvalidBuyOrSale = getMockParametersOfLineInvalidBuyOrSale();

        expectedException.expect(InvalidArgumentException.class);
        expectedException.expectMessage(String.format(WRONG_PARAMETER_VALUE, 4, BUY_OR_SALE, BUY_OR_SALE_VALID, mockParametersOfLineInvalidBuyOrSale[3]));

        validationUtilsMockedStatic.when(() -> ValidationUtils.isNonEmptyString(Mockito.anyString())).thenReturn(true, true, true, true);
        validationUtilsMockedStatic.when(() -> ValidationUtils.isNumber(Mockito.anyString())).thenReturn(true);
        validationUtilsMockedStatic.when(() -> ValidationUtils.isBuyOrSale(Mockito.anyString())).thenReturn(false);

        tradeLoader.validateTrade(mockParametersOfLineInvalidBuyOrSale);

        Mockito.verify(tradeLoader).validateTrade(mockParametersOfLineInvalidBuyOrSale);
        Mockito.verify(tradeLoader, Mockito.times(1)).validateTrade(mockParametersOfLineInvalidBuyOrSale);
    }

    @Test
    public void test_Exception_validateTrade_EmptyPrice() {

        String[] mockParametersOfLineInvalidBuyOrSale = getMockParametersOfLineInvalidBuyOrSale();

        expectedException.expect(InvalidArgumentException.class);
        expectedException.expectMessage(String.format(EMPTY_PARAMETER_VALUE, 5, PRICE));

        validationUtilsMockedStatic.when(() -> ValidationUtils.isNonEmptyString(Mockito.anyString())).thenReturn(true, true, true, true, false);
        validationUtilsMockedStatic.when(() -> ValidationUtils.isNumber(Mockito.anyString())).thenReturn(true);
        validationUtilsMockedStatic.when(() -> ValidationUtils.isBuyOrSale(Mockito.anyString())).thenReturn(true);

        tradeLoader.validateTrade(mockParametersOfLineInvalidBuyOrSale);

        Mockito.verify(tradeLoader).validateTrade(mockParametersOfLineInvalidBuyOrSale);
        Mockito.verify(tradeLoader, Mockito.times(1)).validateTrade(mockParametersOfLineInvalidBuyOrSale);
    }

    @Test
    public void test_Exception_validateTrade_InvalidPrice() {

        String[] mockParametersOfLineInvalidPrice = getMockParametersOfLineInvalidPrice();

        expectedException.expect(InvalidArgumentException.class);
        expectedException.expectMessage(String.format(WRONG_PARAMETER_VALUE, 5, PRICE, NUMBER, mockParametersOfLineInvalidPrice[4]));

        validationUtilsMockedStatic.when(() -> ValidationUtils.isNonEmptyString(Mockito.anyString())).thenReturn(true, true, true, true, true);
        validationUtilsMockedStatic.when(() -> ValidationUtils.isNumber(Mockito.anyString())).thenReturn(true, true);
        validationUtilsMockedStatic.when(() -> ValidationUtils.isPositiveNumber(Mockito.anyString())).thenReturn(false);
        validationUtilsMockedStatic.when(() -> ValidationUtils.isBuyOrSale(Mockito.anyString())).thenReturn(true);

        tradeLoader.validateTrade(mockParametersOfLineInvalidPrice);

        Mockito.verify(tradeLoader).validateTrade(mockParametersOfLineInvalidPrice);
        Mockito.verify(tradeLoader, Mockito.times(1)).validateTrade(mockParametersOfLineInvalidPrice);
    }
}
