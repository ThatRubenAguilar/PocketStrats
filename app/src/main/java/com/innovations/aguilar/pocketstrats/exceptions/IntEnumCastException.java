package com.innovations.aguilar.pocketstrats.exceptions;

import java.lang.reflect.Type;

/**
 * Created by Ruben on 7/6/2017.
 */

public class IntEnumCastException extends RuntimeException {
    static final String exceptionMsg = "Id '%s' did not match any know enum of type '%s'";

    public IntEnumCastException(int id, Type enumType) {
        super(String.format(exceptionMsg, id, enumType.getClass().getName()));
    }

    public IntEnumCastException(int id, Type enumType, Throwable cause) {
        super(String.format(exceptionMsg, id, enumType.getClass().getName()), cause);
    }
}
