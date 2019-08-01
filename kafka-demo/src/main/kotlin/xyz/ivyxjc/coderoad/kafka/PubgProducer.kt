package xyz.ivyxjc.coderoad.kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.*
import java.util.concurrent.locks.LockSupport

class PubgProducer {

    private val producer: KafkaProducer<String, String>

    companion object {
        private val TOPIC = "pubg-transmission-dev"
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
        val value = """
            {
                "type":"player_name",
                "value": "Huya_HeiMao-"
            }
        """.trimIndent()
        val future = producer.send(ProducerRecord(TOPIC, value))
//        count++
//        log.info("this is {}th message", count)
        while (!future.isDone) {
            LockSupport.parkNanos(1000000)
            if (future.isDone) {
                println(future.get())
            }
        }
        producer.flush()
    }

}

fun main() {
    val producer = PubgProducer()
    producer.produce()
}