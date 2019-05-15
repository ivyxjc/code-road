package xyz.ivyxjc.coderoad.ivy.bench

import org.openjdk.jmh.annotations.*
import xyz.ivyxjc.coderoad.ivy.leaf.snowflake.SnowflakeIdGenV1
import xyz.ivyxjc.coderoad.ivy.leaf.snowflake.SnowflakeIdGenV2
import java.util.concurrent.TimeUnit

@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
open class SnoflakeIdGenV1Bench {
    val idGen = SnowflakeIdGenV1()

    @Benchmark
    @Synchronized
    fun genId() {
        idGen.get("")
    }
}


@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
open class SnoflakeIdGenV2Bench {
    val idGen = SnowflakeIdGenV2()

    @Benchmark
    @Synchronized
    fun genId() {
        idGen.get("")
    }
}


