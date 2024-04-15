package ru.job4j.socialmediaapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.job4j.socialmediaapi.exception.BadRequestException;
import ru.job4j.socialmediaapi.exception.InternalServerErrorException;
import ru.job4j.socialmediaapi.exception.ResourceNotFoundException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public void catchDataIntegrityViolationException(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        setResponse(e, request, response);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public void catchBadRequestException(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        setResponse(e, request, response);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public void catchResourceNotFoundException(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        setResponse(e, request, response);
    }

    @ExceptionHandler(value = {InternalServerErrorException.class})
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public void catchInternalServerErrorException(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        setResponse(e, request, response);
    }

    private static void setResponse(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> details = new HashMap<>();
        details.put("message", e.getMessage());
        details.put("type", String.valueOf(e.getClass()));
        details.put("timestamp", String.valueOf(LocalDateTime.now()));
        details.put("path", request.getRequestURI());
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(details));
        log.error(e.getLocalizedMessage());
    }
}