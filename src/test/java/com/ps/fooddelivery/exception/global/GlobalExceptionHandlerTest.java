package com.ps.fooddelivery.exception.global;

import com.ps.fooddelivery.dto.ErrorDetails;
import com.ps.fooddelivery.exception.custom.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private WebRequest webRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandleResourceNotFoundException() {
        // Arrange
        var exceptionMessage = "Resource not found";
        var exception = new ResourceNotFoundException(exceptionMessage);
        when(webRequest.getDescription(false)).thenReturn("Test description");

        // Act
        ResponseEntity<ErrorDetails> responseEntity = globalExceptionHandler.handleResourceNotFoundException(exception, webRequest);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(exceptionMessage, responseEntity.getBody().getMessage());
        assertEquals("Test description", responseEntity.getBody().getDetails());
        assertEquals(new Date().getTime(), responseEntity.getBody().getTimestamp().getTime(), 1000);
    }

    @Test
    public void testHandleGlobalException() {
        // Arrange
        var exceptionMessage = "An error occurred";
        var exception = new Exception(exceptionMessage);
        when(webRequest.getDescription(false)).thenReturn("Test description");

        // Act
        ResponseEntity<ErrorDetails> responseEntity = globalExceptionHandler.handleGlobalException(exception, webRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(exceptionMessage, responseEntity.getBody().getMessage());
        assertEquals("Test description", responseEntity.getBody().getDetails());
        assertEquals(new Date().getTime(), responseEntity.getBody().getTimestamp().getTime(), 1000);
    }
}

