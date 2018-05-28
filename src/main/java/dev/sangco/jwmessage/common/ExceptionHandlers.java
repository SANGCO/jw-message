package dev.sangco.jwmessage.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandlers {
    public static final Logger log = LoggerFactory.getLogger(ExceptionHandlers.class);
}
