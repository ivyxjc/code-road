package xyz.ivyxjc.coderoad.ivy.bench

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder
import xyz.ivyxjc.coderoad.ivy.leaf.snowflake.SnowflakeIdGen
import java.util.concurrent.TimeUnit


fun main() {
    val opt = OptionsBuilder()
        .include(LeafBenchmarkRunner::class.java.simpleName)
        .forks(1)
        .warmupIterations(5)
        .measurementIterations(5)
        .threads(8)
        .build()

    Runner(opt).run()
}

@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
open class LeafBenchmarkRunner {
    val idGen = SnowflakeIdGen()

    @Benchmark
    fun genId() {
        idGen.get("")
    }

}


