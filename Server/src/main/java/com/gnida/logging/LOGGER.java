package com.gnida.logging;

import java.util.Arrays;
import java.util.logging.Logger;

public class LOGGER {
    private static final Logger logger = Logger.getLogger(LOGGER.class.getName());

    public static void log(Object... objects) {
        logger.info(Arrays.toString(objects));
    }
}
