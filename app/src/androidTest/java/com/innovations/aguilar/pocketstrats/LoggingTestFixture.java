package com.innovations.aguilar.pocketstrats;

import com.innovations.aguilar.pocketstrats.logging.LoggerSupplier;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import org.slf4j.Logger;

public class LoggingTestFixture {
    protected Supplier<Logger> log = Suppliers.memoize(new LoggerSupplier(this.getClass()));
    protected static Supplier<Logger> staticLog = Suppliers.memoize(new LoggerSupplier(LoggingTestFixture.class));
}
