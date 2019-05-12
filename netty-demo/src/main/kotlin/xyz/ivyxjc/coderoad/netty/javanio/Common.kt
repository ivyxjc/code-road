package xyz.ivyxjc.coderoad.netty.javanio

import xyz.ivyxjc.coderoad.netty.Base
import java.io.File
import java.io.FileOutputStream
import java.io.RandomAccessFile


fun buildFile(path: String = "/data/nio-data.txt", mode: String = "rw"): RandomAccessFile {
    val url = Base::class.java.getResource(path)
    println(File(url.toURI()))
    return RandomAccessFile(File(url.toURI()), mode)
}

fun buildOutputFile(filename: String, appendable: Boolean): FileOutputStream {
    val url = Base::class.java.getResource("/log4j2.xml")
    val log4jPath = File(url.toURI()).absolutePath
    val directorPath = log4jPath.substring(0, log4jPath.length - 11)
    val filePath = directorPath + filename
    return FileOutputStream(File(filePath), appendable)
}