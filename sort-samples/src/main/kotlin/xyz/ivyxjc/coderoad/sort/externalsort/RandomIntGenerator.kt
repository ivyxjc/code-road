package xyz.ivyxjc.coderoad.sort.externalsort

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.util.*


fun main() {
    var count = 0L
    var random = Random()
    val out = BufferedWriter(FileWriter(File("abc.txt")))
    val t1 = System.currentTimeMillis()
    out.use {
        while (count < 1 * Math.pow(10.0, 7.0)) {
            val randLong = random.nextLong()
            it.write("${randLong}\n")
            count++
        }
    }
    println("costs: ${System.currentTimeMillis() - t1}")
}