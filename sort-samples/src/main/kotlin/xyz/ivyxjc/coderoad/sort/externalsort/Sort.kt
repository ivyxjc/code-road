package xyz.ivyxjc.coderoad.sort.externalsort

import java.io.*
import java.util.*


fun main() {
//    sortInBatch(1000000)
    val t1 = System.currentTimeMillis()
    mergeSortedFiles(8, 10000, File(".\\eee.txt"))
    val t2 = System.currentTimeMillis();
    println(t2 - t1)
}

fun sortInBatch(cacheSize: Int) {
    val reader = BufferedReader(
        InputStreamReader(FileInputStream(".\\abc.txt")), 20 * 1024 * 1024
    )
    val t1 = System.currentTimeMillis()
    var line: String? = null
    var count = 0
    var index = 0
    val cacheArray = Array(cacheSize) { it.toLong() }
    reader.use {
        do {
            line = it.readLine()
            if (line == null) {
                break
            }
            val tmpLong = line!!.toLong()
            if (count != 0 && count % cacheSize == 0) {
                index = 0
                cacheArray.sortWith(compareBy { it })
                val writer = BufferedWriter(FileWriter(File(".\\data\\${count / cacheSize}.txt")))
                writer.use {
                    cacheArray.forEach {
                        writer.write("$it\n")
                    }
                }
            }
            cacheArray[index] = tmpLong
            count++
            index++

        } while (true)

    }
    println(count)
    println(System.currentTimeMillis() - t1)
}

fun mergeSortedFiles(fileSize: Int, batchSize: Int, output: File) {
    val bufferedWriter = BufferedWriter(FileWriter(output), 20 * 1000 * 1000)
    val files = mutableListOf<File>()
    for (index in 1..fileSize) {
        val tmp = File(".\\data\\$index.txt")
        files.add(tmp)
    }

    val bfrs = mutableListOf<BufferedReader>()
    for (file in files) {
        val bfr = BufferedReader(FileReader(file))
        bfrs.add(bfr)
    }

    val batchDataList = mutableListOf<ArrayDeque<Long>>()
    for (index in 0 until fileSize) {
        batchDataList.add(ArrayDeque())
    }

    bfrs.forEachIndexed { index, it ->
        fetchFurtherData(it, index, batchSize, batchDataList)
    }
    val priorityQueue = PriorityQueue<LongWithIndex>(compareBy { it.value })
    val flagArray = Array(fileSize) { true }

    for (index in 0 until fileSize) {
        priorityQueue.add(LongWithIndex(batchDataList[index].remove(), index))
    }

    while (!priorityQueue.isEmpty()) {
        val l = priorityQueue.remove()
        bufferedWriter.write("${l.value}\n")

        if (flagArray[l.index]) {
            priorityQueue.add(LongWithIndex(batchDataList[l.index].remove(), l.index))
            if (batchDataList[l.index].isEmpty()) {
                if (flagArray[l.index]) {
                    flagArray[l.index] = fetchFurtherData(bfrs[l.index], l.index, batchSize, batchDataList);
                }
            }
        }


    }
    bufferedWriter.flush()
    bufferedWriter.close()

}

fun fetchFurtherData(
    bufferedReader: BufferedReader,
    index: Int,
    readSize: Int,
    dataContainer: List<ArrayDeque<Long>>
): Boolean {
    var line: String?
    for (i in 0 until readSize) {
        line = bufferedReader.readLine()
        if (line == null) {
            return false
        }
        dataContainer.get(index).add(line.toLong())
    }
    return true
}

data class LongWithIndex(val value: Long, val index: Int)
