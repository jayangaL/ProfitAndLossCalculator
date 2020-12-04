package com.viryaconsulting.exception;

import static com.viryaconsulting.util.Constants.NULL_TRADE_PARAMETERS;

public class NullTradeInputException extends IllegalArgumentException {

    public NullTradeInputException() {
        super(NULL_TRADE_PARAMETERS);
    }
}
