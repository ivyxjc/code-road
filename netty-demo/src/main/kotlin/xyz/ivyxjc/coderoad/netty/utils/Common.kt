package xyz.ivyxjc.coderoad.netty.utils

import org.slf4j.LoggerFactory

fun <T> loggerFor(clz: Class<T>) = LoggerFactory.getLogger(clz)