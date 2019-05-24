package xyz.ivyxjc.coderoad.kafka

import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.*


class KafkaConsumerDemo {
    private val consumer: KafkaConsumer<String, String>

    companion object {
        private val TOPIC = "ivy-leaf"
        private val log = loggerFor(ProducerDemo::class.java)
    }

    constructor() {
        val props = Properties()
        props.load(ProducerDemo::class.java.classLoader.getResourceAsStream("consumer.properties"))
        props.load(ProducerDemo::class.java.classLoader.getResourceAsStream("private.properties"))
        consumer = KafkaConsumer(props)
    }

    fun consume() {
        var count = 0
        consumer.subscribe(listOf(TOPIC))
        while (count < 100) {
            val records = consumer.poll(100)
            for (record in records) {
                count++
                log.debug("offset: {}, value: {}", record.offset(), record.value())
            }
        }
    }
}

fun main() {
    val consumerDemo = KafkaConsumerDemo()
    consumerDemo.consume()
}
