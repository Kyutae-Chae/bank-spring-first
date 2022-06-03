package bank.kakao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public void log() {
        logger.trace("Trace");
        logger.debug("Debug");
        logger.info("Info");
        logger.warn("Warn");
        logger.error("Error");
    }

    public void info(String log) {
        logger.info("[BANK] " + log);
    }

    public void warn(String log) {
        logger.warn("[BANK] " + log);
    }

    public void error(String log) {
        logger.error("[BANK] " + log);
    }
}
