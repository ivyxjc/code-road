package xyz.ivyxjc.coderoad.java.basic.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static xyz.ivyxjc.coderoad.java.basic.queue.CommonConstants.ADD_COUNT;
import static xyz.ivyxjc.coderoad.java.basic.queue.CommonConstants.THREADS_COUNT;

public class ArrayBlockingQueueBench {

    private static final BlockingQueue<LogEvent> blockingQueue =
            new ArrayBlockingQueue<>(THREADS_COUNT * ADD_COUNT);


    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[THREADS_COUNT];
        long t1 = System.nanoTime();
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new QueueProducer(blockingQueue);
        }

        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i].start();
        }

        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i].join();
        }
        long t2 = System.nanoTime();
        System.out.println(blockingQueue.size());
        System.out.println("cost: " + (t2 - t1) / 1000000);

    }


}
