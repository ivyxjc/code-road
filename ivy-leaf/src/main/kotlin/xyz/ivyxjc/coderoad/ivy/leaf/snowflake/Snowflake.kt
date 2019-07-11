package xyz.ivyxjc.coderoad.ivy.leaf.snowflake

import org.slf4j.LoggerFactory
import xyz.ivyxjc.coderoad.ivy.leaf.IDGenerator
import xyz.ivyxjc.coderoad.ivy.leaf.Result
import xyz.ivyxjc.coderoad.ivy.leaf.Status


/**
 *  A very simple id generator
 *  Used as performance baseline
 *  There are some bugs in this generator:
 *      1. Cannot handle the condition of clock back
 *      2. Generate duplicate ids when speed is too fast
 *      ( not to check current time and last time)
 */
class SnowflakeIdGenV1 : IDGenerator {

    companion object {
        val workerIdLength: Int = 10
        val serialLength: Int = 12
        val wokerId = "0000000001".toLong(2)
        val maxSerialNumber = 4095L
        var serialNumber = 0L
    }


    override fun get(key: String): Result {
        var status = Status.SUCCESS
        val timeInId = System.currentTimeMillis() shl (workerIdLength + serialLength)
        val workerIdInId = wokerId shl serialLength
        val res = timeInId or workerIdInId or (serialNumber and maxSerialNumber)
        serialNumber += 1
        return Result(res, status)
    }
}


/**
 * one single-machine id generator
 *
 * If clock is taken back, the Result is with exception
 */
class SnowflakeIdGenV2 : IDGenerator {
    private val log = LoggerFactory.getLogger(SnowflakeIdGenV2::class.java)

    private var lastTimestamp: Long = System.currentTimeMillis()
    private val lock = java.lang.Object()

    companion object {
        val workerIdLength: Int = 10
        val serialLength: Int = 12
        val wokerId = "0000000001".toLong(2)
        val maxSerialNumber = 4095L
        var serialNumber = 0L
    }

    override fun get(key: String): Result {
        var status = Status.SUCCESS
        var currentTime = timeGen()
        if (currentTime < lastTimestamp) {
            val offset = lastTimestamp - currentTime
            if (offset < 5) {
                try {
                    lock.wait(5)
                    currentTime = timeGen()
                    if (currentTime < lastTimestamp) {
                        return Result(-3, Status.EXCEPTION)
                    }
                } catch (e: InterruptedException) {
                    log.error("wait interrupt", e)
                    return Result(-1, Status.EXCEPTION)
                }
            } else {
                return Result(-3, Status.EXCEPTION)
            }
        }

        val timeInId = currentTime shl (workerIdLength + serialLength)
        val workerIdInId = wokerId shl serialLength
        /*
         When time equals to the before one,
         need to check whether serialNumber reaches the max number.
         If yes, the program should wait until next millisecond coming
         */
        if (currentTime == lastTimestamp) {
            if (serialNumber and maxSerialNumber == maxSerialNumber) {
                serialNumber = 0
                untilNextMillis()
            }
        } else {
            serialNumber = 0
        }
        lastTimestamp = currentTime
        val res = timeInId or workerIdInId or (serialNumber and maxSerialNumber)
        serialNumber += 1
        return Result(res, status)
    }

    fun untilNextMillis() {
        var time = timeGen()
        while (lastTimestamp == time) {
            time = timeGen()
        }
    }

    fun timeGen(): Long {
        return System.currentTimeMillis();
    }
}


fun main() {
    println(4095L.toString(2))
    println(4095L and 4095L == 4095L)
    val idGenerator = SnowflakeIdGenV1()
    for (i in 0 until 100) {
        val res = idGenerator.get("hello")
        println(res.id.toString(2))
    }

}