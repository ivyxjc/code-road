package xyz.ivyxjc.coderoad.kafka

import org.slf4j.LoggerFactory

fun <T> loggerFor(clz: Class<T>) = LoggerFactory.getLogger(clz)



