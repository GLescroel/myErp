package com.dummy.myerp.technical.exception;

import org.junit.Assert;
import org.junit.Test;

public class NotFoundExceptionTest {

    private static final String ERROR_MESSAGE = "test Not Found Exception";
    @Test
    public void testShouldCreateNotFoundException() {
        NotFoundException exception = new NotFoundException();
        exception.initCause(new NotFoundException());
        Assert.assertEquals(NotFoundException.class, exception.getCause().getClass());
    }

    @Test
    public void testShouldCreateNotFoundExceptionWithMessage() {
        NotFoundException exception = new NotFoundException(ERROR_MESSAGE);
        Assert.assertEquals(ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    public void testShouldCreateNotFoundExceptionWithThrowable() {
        NotFoundException exception = new NotFoundException(new Exception());
        Assert.assertEquals(Exception.class, exception.getCause().getClass());
    }

    @Test
    public void testShouldCreateNotFoundExceptionWithMessageAndThrowable() {
        NotFoundException exception = new NotFoundException(ERROR_MESSAGE, new Exception());
        Assert.assertEquals(ERROR_MESSAGE, exception.getMessage());
        Assert.assertEquals(Exception.class, exception.getCause().getClass());
    }

}