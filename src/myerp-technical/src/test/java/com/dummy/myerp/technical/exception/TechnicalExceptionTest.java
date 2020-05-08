package com.dummy.myerp.technical.exception;

import org.junit.Assert;
import org.junit.Test;

public class TechnicalExceptionTest {

    private static final String ERROR_MESSAGE = "Test Technical Exception";
    @Test
    public void testShouldCreateTechnicalExceptionWithMessage() {
        TechnicalException exception = new TechnicalException(ERROR_MESSAGE);
        Assert.assertEquals(ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    public void testShouldCreateTechnicalExceptionWithThrowable() {
        TechnicalException exception = new TechnicalException(new Exception());
        Assert.assertEquals(Exception.class, exception.getCause().getClass());
    }

    @Test
    public void testShouldCreateTechnicalExceptionWithMessageAndThrowable() {
        TechnicalException exception = new TechnicalException(ERROR_MESSAGE, new Exception());
        Assert.assertEquals(ERROR_MESSAGE, exception.getMessage());
        Assert.assertEquals(Exception.class, exception.getCause().getClass());
    }

}