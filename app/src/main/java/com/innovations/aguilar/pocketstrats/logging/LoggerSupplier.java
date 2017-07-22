package com.innovations.aguilar.pocketstrats.logging;

import com.google.common.base.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ruben on 7/19/2017.
 */

public class LoggerSupplier implements Supplier<Logger> {
    private Class<?> classType;

    public LoggerSupplier(Class<?> classType) {

        this.classType = classType;
    }

    @Override
    public Logger get() {
        return LoggerFactory.getLogger(classType);
    }
}
