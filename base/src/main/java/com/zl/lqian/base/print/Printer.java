package com.zl.lqian.base.print;

import com.zl.lqian.base.oauth.OauthSina;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zl on 2015/9/6.
 */
public class Printer {
    private static final Logger log = LoggerFactory.getLogger(OauthSina.class);

    public static void info(String message) {
        log.info(message);
    }

    public static void debug(String message) {
        log.debug(message);
    }

    public static void warn(String message) {
        log.warn(message);
    }

    public static void error(String message) {
        log.error(message);
    }

    public static void error(String message, Throwable t) {
        log.error(message, t);
    }
}
