package log4j;

import org.apache.log4j.Logger;

public class Log4jDemoII {

    private static final Logger logger = Logger.getLogger(Log4jDemoII.class);

    public static void main(String[] args) {
        logger.debug("This is debug message.");
        logger.info("This is info message.");
        logger.error("This is error message.");
    }

}
