package xyz.ivyxjc.coderoad.java.basic.queue;

import com.lmax.disruptor.EventFactory;

public class LogFactory implements EventFactory<LogEvent> {
    @Override
    public LogEvent newInstance() {
        return new LogEvent();
    }
}
