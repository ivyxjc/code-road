package xyz.ivyxjc.coderoad.java.basic.queue;

import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogHandler implements EventHandler<LogEvent> {

    private Logger log = LoggerFactory.getLogger(LogHandler.class);

    @Override
    public void onEvent(LogEvent event, long sequence, boolean endOfBatch) throws Exception {
        log.debug("event is: {}", event);
    }
}
