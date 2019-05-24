package xyz.ivyxjc.coderoad.kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.*


class ProducerDemo {

    private val producer: KafkaProducer<String, String>

    companion object {
        private val TOPIC = "ivy-leaf"
        private val log = loggerFor(ProducerDemo::class.java)
    }

    constructor() {
        val props = Properties()
        props.load(ProducerDemo::class.java.classLoader.getResourceAsStream("producer.properties"))
        props.load(ProducerDemo::class.java.classLoader.getResourceAsStream("private.properties"))
        producer = KafkaProducer(props)
    }

    fun produce() {
        var count = 0
        while (count < 50) {
            val value = "kafka, this is ${count}th message "
            producer.send(ProducerRecord(TOPIC, value))
            count++
            log.debug("this is {}th message", count)
        }
        producer.flush()
    }

}

fun main() {
    val producerDemo = ProducerDemo()
    producerDemo.produce()
}
