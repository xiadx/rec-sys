package log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

public class Log4jDemoI {

    private static final Logger logger = Logger.getLogger(Log4jDemoI.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        logger.debug("This is debug message.");
        logger.info("This is info message.");
        logger.error("This is error message.");
    }

}
