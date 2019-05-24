package xyz.ivyxjc.coderoad.deep.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTimer {

    private static final Logger log = LoggerFactory.getLogger(LogTimer.class);

    public static void main(String[] args) {
        int count = 500;
        LogTimer logTimer = new LogTimer();
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            logTimer.doLog();
        }
        long t2 = System.currentTimeMillis();
        log.info("time cost: {}", t2 - t1);
        System.out.println("time cost" + (t2 - t1));
    }

    public void doLog() {
        log.info("Test log");
    }
}
