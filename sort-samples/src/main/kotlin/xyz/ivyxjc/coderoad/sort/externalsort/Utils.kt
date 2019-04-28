package xyz.ivyxjc.coderoad.sort.externalsort

import java.io.BufferedReader
import java.io.File
import java.io.FileReader


fun main() {
//    val count = countlines(File(".\\data\\1.txt"))
    var count = 0
    for (i in 1..9) {
        val tmp = countlines(File(".\\data\\$i.txt"))
        count += tmp

    }
    println(count)
}

fun countlines(file: File): Int {
    val reader = BufferedReader(FileReader(file))
    var line: String?
    var count = 0
    reader.use {
        do {
            line = reader.readLine()
            count++
            if (line == null) {
                return count
            }
        } while (true)
    }
    return -1
}