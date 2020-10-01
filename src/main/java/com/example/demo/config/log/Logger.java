package com.example.demo.config.log;

import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class Logger {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Logger.class);

    public static void info(String message) {
        log.info(message);
    }

    public static void debug(Supplier<String> message) {
        if (log.isDebugEnabled()) {
            log.debug(message.get());
        }
    }

    public static void debug(Supplier<String> message, Object... args) {
        if (log.isDebugEnabled()) {
            log.debug(MessageFormat.format(message.get(), args));
        }
    }
}
