package br.edu.baozi.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IntegridadeControllerAdvice {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflito() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "A operação viola uma restrição do banco. Exclua primeiro o pedido vinculado.");
    }
}
