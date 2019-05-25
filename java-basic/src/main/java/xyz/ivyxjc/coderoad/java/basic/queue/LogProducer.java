package xyz.ivyxjc.coderoad.java.basic.queue;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogProducer extends Thread {

    private static final Logger log = LoggerFactory.getLogger(LogProducer.class);
    private static final EventTranslatorOneArg<LogEvent, String> TRANSLATOR = new EventTranslatorOneArg<LogEvent, String>() {
        @Override
        public void translateTo(LogEvent event, long sequence, String arg0) {
            event.setMsg(arg0);
        }
    };
    private RingBuffer<LogEvent> ringBuffer;

    public LogProducer(RingBuffer<LogEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < CommonConstants.ADD_COUNT; i++) {
            log.debug("publish:{} ", i);
            ringBuffer.publishEvent(TRANSLATOR, i + "");
        }
    }
}
