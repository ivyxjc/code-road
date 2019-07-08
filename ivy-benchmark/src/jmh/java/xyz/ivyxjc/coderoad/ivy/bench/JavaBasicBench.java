package xyz.ivyxjc.coderoad.ivy.bench;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class JavaBasicBench {
    private long index = 1024 * 1024;
    private long indexMask = index - 1;


    @Benchmark
    public long mod() {
        return 561235 % index;
    }

    @Benchmark
    public long bitMod() {
        return 561235 & indexMask;
    }

}
