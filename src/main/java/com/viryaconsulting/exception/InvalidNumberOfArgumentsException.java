package com.viryaconsulting.exception;

import static com.viryaconsulting.util.Constants.WRONG_PARAMETER_LENGTH;

public class InvalidNumberOfArgumentsException extends IllegalArgumentException {

    public InvalidNumberOfArgumentsException(int number, String value) {
        super(String.format(WRONG_PARAMETER_LENGTH, number, value));
    }
}
