package factory;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SingletonLogger {
    private static SingletonLogger singletonLogger;
    private static Logger logger;
    private static FileHandler fh;

    public SingletonLogger(String loggerName, String fileName) {
        if (singletonLogger != null) {
            return;
        }

        try {
            fh = new FileHandler(fileName);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }

        SimpleFormatter sf = new SimpleFormatter();
        fh.setFormatter(sf);
        logger = Logger.getLogger(loggerName);
        logger.setUseParentHandlers(false);
        logger.addHandler(fh);

        singletonLogger = this;
    }

    public Logger getLogger() {
        return SingletonLogger.logger;
    }
}
