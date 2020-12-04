package com.viryaconsulting.util;

public class Constants {

    public static final String NUMBER = "Number";

    public static final String TRADE_ID = "Trade Id";
    public static final String STOCK_SYMBOL = "Stock Symbol";
    public static final String QUANTITY = "Quantity";
    public static final String BUY_OR_SALE = "Buy or Sale";
    public static final String PRICE = "Price";

    public static final String BUY_OR_SALE_VALID = "Character and Should be 'B' or 'S'.";

    public static final String NULL_TRADE_PARAMETERS= "Trade Must Contain 5 Parameters. Can't Be null, Should be in [<trader_id>,<stock_symbol>,<quantity>,<buy_sell>,<price>] format";
    public static final String WRONG_PARAMETER_LENGTH= "Trade Must Contain 5 Parameters. Which has %x . %s";

    public static final String WRONG_PARAMETER_VALUE= "Input number %s is Invalid %s . Should be a Valid %s. Which is not valid [%s].";
    public static final String EMPTY_PARAMETER_VALUE= "Input number %s is %s Can't be Empty.";

    public static final String INVOKE_METHOD_CALLED_PRINT_MSG = "Method invoke Called.";

    public static final String FILE_NOT_FOUND = "File not found! %S";

    private Constants(){
        throw new UnsupportedOperationException();
    }
}
