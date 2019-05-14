package xyz.ivyxjc.coderoad.ivy.leaf.snowflake

import xyz.ivyxjc.coderoad.ivy.leaf.IDGenerator
import xyz.ivyxjc.coderoad.ivy.leaf.Result
import xyz.ivyxjc.coderoad.ivy.leaf.Status

class SnowflakeIdGen : IDGenerator {

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

fun main() {
    println(4095L.toString(2))
    val idGenerator = SnowflakeIdGen()
    for (i in 0 until 100) {
        val res = idGenerator.get("hello")
        println(res.id.toString(2))
    }

}