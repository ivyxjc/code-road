package xyz.ivyxjc.coderoad.java.basic.queue;

import java.util.concurrent.BlockingQueue;

import static xyz.ivyxjc.coderoad.java.basic.queue.CommonConstants.ADD_COUNT;

public class QueueProducer extends Thread {
    private BlockingQueue<LogEvent> blockingQueue;

    public QueueProducer(BlockingQueue<LogEvent> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < ADD_COUNT; i++) {
            LogEvent logEvent = new LogEvent();
            logEvent.setMsg(i + "");
            blockingQueue.offer(logEvent);
        }
    }
}
