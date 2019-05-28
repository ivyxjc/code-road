package xyz.ivyxjc.coderoad.java.basic.queue;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static xyz.ivyxjc.coderoad.java.basic.queue.CommonConstants.THREADS_COUNT;

public class DisruptorMain {
    private static final Logger log = LoggerFactory.getLogger(DisruptorMain.class);

    public static void main(String[] args) throws InterruptedException {
        LogFactory factory = new LogFactory();
        int bufferSize = 1024;

        // 构造 Disruptor
        Disruptor<LogEvent> disruptor = new Disruptor<>(factory, bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.MULTI,
                new YieldingWaitStrategy());

        // 设置消费者
//        disruptor.handleEventsWith(new LogHandler());

        // 启动 Disruptor
        disruptor.start();

        // 生产者要使用 Disruptor 的环形数组
        RingBuffer<LogEvent> ringBuffer = disruptor.getRingBuffer();
        Thread[] threads = new LogProducer[THREADS_COUNT];

        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new LogProducer(ringBuffer);
        }

        long t1 = System.nanoTime();
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i].start();
        }

        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i].join();
        }

        long t2 = System.nanoTime();
        log.info("cost: {}", (t2 - t1) / 1000000);

    }
}
