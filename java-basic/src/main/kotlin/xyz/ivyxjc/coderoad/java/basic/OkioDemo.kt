package xyz.ivyxjc.coderoad.java.basic

import okio.buffer
import okio.sink
import okio.source
import org.apache.commons.lang3.StringUtils
import xyz.ivyxjc.ivy.tools.commons.Charsets
import java.io.File

val topLevelClass = object : Any() {}::class.java.enclosingClass

fun main() {
    val input = topLevelClass.classLoader.getResourceAsStream("sample1.txt")
    val source = input!!.source()
    val bSource = source.buffer()
    println(bSource.readUtf8())
    println("++++++++++++++++++")
    val res = bSource.readString(Charsets.UTF_8)
    println(res)
    val writePath="H:\\IVY\\Code\\code-road\\java-basic\\src\\main\\resources\\sample2.txt"
    println(writePath)
    val file = File(writePath)
    if (!file.exists()) {
        file.createNewFile()
    }
    val sink = file.sink()
    val bSink = sink.buffer()
    bSink.writeUtf8("hello world\n")
    bSink.writeUtf8("world hello")
    bSink.flush()
    sink.flush()
}

