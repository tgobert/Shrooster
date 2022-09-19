package com.revature.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class ExceptionTests {
    String message = "random text";
    boolean fakeEnableSuppression = false;
    boolean fakeWritableStackTrace = false;

    @Test
    void testAuthenticationException_Success_WithMessage() {
        try {
            throw new AuthenticationException(this.message);
        } catch (Exception e) {
            assertEquals(AuthenticationException.class, e.getClass());
            assertEquals(this.message, e.getMessage());
        }
    }

    @Test
    void testUnauthorizedSessionException_Success_WithMessage() {
        try {
            throw new UnauthorizedSessionException(this.message);
        } catch (Exception e) {
            assertEquals(UnauthorizedSessionException.class, e.getClass());
            assertEquals(this.message, e.getMessage());
        }
    }

    @Test
    void testNotLoggedInException_Success() {
        try {
            throw new NotLoggedInException();
        } catch (Exception e) {
            assertEquals(NotLoggedInException.class, e.getClass());
        }
    }

    @Test
    void testNotLoggedInException_Success_WithMessage() {
        try {
            throw new NotLoggedInException(this.message);
        } catch (Exception e) {
            assertEquals(NotLoggedInException.class, e.getClass());
            assertEquals(this.message, e.getMessage());
        }
    }

    @Test
    void testNotLoggedInException_Success_WithThrowable() {
        try {
            throw new NotLoggedInException(new AuthenticationException(this.message));
        } catch (Exception e) {
            assertEquals(NotLoggedInException.class, e.getClass());
            assertEquals(this.message, e.getCause().getMessage());
            assertEquals(AuthenticationException.class, e.getCause().getClass());
        }
    }

    @Test
    void testNotLoggedInException_Success_WithThrowableAndMessage() {
        try {
            throw new NotLoggedInException(this.message, new AuthenticationException(this.message));
        } catch (Exception e) {
            assertEquals(NotLoggedInException.class, e.getClass());
            assertEquals(this.message, e.getMessage());
            assertEquals(this.message, e.getCause().getMessage());
            assertEquals(AuthenticationException.class, e.getCause().getClass());
        }
    }

    @Test
    void testNotLoggedInException_Success_WithThrowableAndMessageAndBooleans() {
        try {
            throw new NotLoggedInException(this.message, new AuthenticationException(this.message),
                    this.fakeEnableSuppression, this.fakeWritableStackTrace);
        } catch (Exception e) {
            assertEquals(NotLoggedInException.class, e.getClass());
            assertEquals(this.message, e.getMessage());
            assertEquals(this.message, e.getCause().getMessage());
            assertEquals(AuthenticationException.class, e.getCause().getClass());
        }
    }
}
