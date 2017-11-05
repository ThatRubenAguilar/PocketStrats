package com.innovations.aguilar.pocketstrats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingTestFixture {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    protected static Logger staticLog = LoggerFactory.getLogger(LoggingTestFixture.class);
}
