package com.dummy.myerp.technical.exception;

import org.junit.Assert;
import org.junit.Test;

public class FunctionalExceptionTest {

    private static final String ERROR_MESSAGE = "Test Functional Exception";
    @Test
    public void testShouldCreateFunctionalExceptionWithMessage() {
        FunctionalException exception = new FunctionalException(ERROR_MESSAGE);
        Assert.assertEquals(ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    public void testShouldCreateFunctionalExceptionWithThrowable() {
        FunctionalException exception = new FunctionalException(new Exception());
        Assert.assertEquals(Exception.class, exception.getCause().getClass());
    }

    @Test
    public void testShouldCreateFunctionalExceptionWithMessageAndThrowable() {
        FunctionalException exception = new FunctionalException(ERROR_MESSAGE, new Exception());
        Assert.assertEquals(ERROR_MESSAGE, exception.getMessage());
        Assert.assertEquals(Exception.class, exception.getCause().getClass());
    }

}