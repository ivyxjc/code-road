package xyz.ivyxjc.coderoad.java.basic.concurrent;

public class FakeSharing implements Runnable {
    private final static long ITERATIONS = 500L * 1000L * 1000L;
    private static int THREADS_NUM = 8;
    private static VolatileLong[] longs;
    private final int arrayIndex;

    private FakeSharing(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    private static long preventOptimize() {
        VolatileLong v = new VolatileLong();
        return v.p1 + v.p2 + v.p3 + v.p4 + v.p5 + v.p6 + v.p7;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(1000);
        longs = new VolatileLong[THREADS_NUM];
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new VolatileLong();
        }
        long res = preventOptimize();
        System.out.println(res);
        final long start = System.nanoTime();
        runTest();
        final long end = System.nanoTime();
        System.out.println("cost time is: " + (end - start) / 1000000);

    }

    private static void runTest() throws InterruptedException {
        Thread[] threads = new Thread[THREADS_NUM];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FakeSharing(i));
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }

    @Override
    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i) {
            longs[arrayIndex].value = i;
        }
    }

    private static final class VolatileLong {
        private volatile long value = 0L;

        private long p1, p2, p3, p4, p5, p6, p7;// 注释掉此行， 速度慢2倍左右
    }


}
