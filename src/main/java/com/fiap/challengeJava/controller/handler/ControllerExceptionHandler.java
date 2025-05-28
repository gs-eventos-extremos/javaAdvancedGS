package com.fiap.challengeJava.controller.handler;

import com.fiap.challengeJava.service.exceptions.InvalidCredentialsException;
import com.fiap.challengeJava.service.exceptions.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.TransientObjectException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.time.DateTimeException;
import java.time.Instant;
import java.util.NoSuchElementException;


@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ModelAndView usernameNotFoundException(UsernameNotFoundException e, HttpServletRequest request, Model model) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        model.addAttribute("message", e.getMessage());
        return new ModelAndView("error");
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ModelAndView invalidCredentialsException(InvalidCredentialsException e, HttpServletRequest request, Model model) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        model.addAttribute("message", e.getMessage());
        return new ModelAndView("error");
    }

    @ExceptionHandler(DateTimeException.class)
    public ModelAndView dateTimeException(DateTimeException e, HttpServletRequest request, Model model) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        model.addAttribute("message", e.getMessage());
        return new ModelAndView("error");
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ModelAndView userAlreadyExistsException(UserAlreadyExistsException e, HttpServletRequest request, Model model) {
        HttpStatus status = HttpStatus.CONFLICT;

        model.addAttribute("message", e.getMessage());
        return new ModelAndView("error");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView methodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request, Model model) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError error = new ValidationError(Instant.now(), status.value(), "Dados inválidos", request.getRequestURI());

        exception.getBindingResult().getFieldErrors().forEach(fieldError -> error.addError(fieldError.getField(), fieldError.getDefaultMessage()));

        StringBuilder message = new StringBuilder();
        error.getErrors().forEach(fieldMessage -> message.append(fieldMessage.getFieldName()).append(": ").append(fieldMessage.getMessage()).append(". "));

        model.addAttribute("message", message.toString());
        return new ModelAndView("error");
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ModelAndView noSuchElementException(NoSuchElementException e, HttpServletRequest request, Model model) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        model.addAttribute("message", e.getMessage());
        return new ModelAndView("error");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ModelAndView httpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request, Model model) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        model.addAttribute("message", e.getMessage());
        return new ModelAndView("error");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ModelAndView httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request, Model model) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        model.addAttribute("message", e.getMessage());
        return new ModelAndView("error");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView jdbcSQLIntegrityConstraintViolationException(DataIntegrityViolationException e, HttpServletRequest request, Model model) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        model.addAttribute("message", e.getMessage());
        return new ModelAndView("error");
    }

}
