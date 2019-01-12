package com.perhammer.joshua.registration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TeamNameNotUniqueAdvice {

    @ResponseBody
    @ExceptionHandler(TeamNameNotUniqueException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String teamNameNotUniqueHandler(TeamNameNotUniqueException tnnue) {
        return tnnue.getMessage();
    }}
