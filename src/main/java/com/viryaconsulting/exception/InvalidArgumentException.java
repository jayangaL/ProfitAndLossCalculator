package com.viryaconsulting.exception;

import static com.viryaconsulting.util.Constants.EMPTY_PARAMETER_VALUE;
import static com.viryaconsulting.util.Constants.WRONG_PARAMETER_VALUE;

public class InvalidArgumentException extends IllegalArgumentException {

    public InvalidArgumentException(int number,  String param, String expect, String value) {
        super(String.format(WRONG_PARAMETER_VALUE, number, param, expect, value));
    }

    public InvalidArgumentException(int number, String param) {
        super(String.format(EMPTY_PARAMETER_VALUE, number, param));
    }
}
