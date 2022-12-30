package com.pagonxt.gpp.executor.exception.handler;

import com.pagonxt.gpp.executor.exception.ExecutionNotFoundException;
import com.pagonxt.gpp.executor.exception.IllegalStateMachineException;
import com.pagonxt.gpp.executor.exception.TransitionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionErrorHandler {

  @ExceptionHandler(IllegalStateMachineException.class)
  public ResponseEntity<String> handleIllegalStateMachineException(IllegalStateMachineException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ExecutionNotFoundException.class)
  public ResponseEntity<String> handleExecutionNotFoundException(ExecutionNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(TransitionNotFoundException.class)
  public ResponseEntity<String> handleTransitionNotFoundException(TransitionNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

}
