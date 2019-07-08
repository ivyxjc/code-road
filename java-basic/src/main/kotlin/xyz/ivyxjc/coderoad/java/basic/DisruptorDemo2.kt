package xyz.ivyxjc.coderoad.java.basic

import com.lmax.disruptor.*
import java.io.IOException
import java.util.concurrent.TimeUnit

class MyDataEvent {
    var id: Int = 0
    var data: String? = null
}

class MyDataEventHandler : EventHandler<MyDataEvent> {
    @Throws(Exception::class)
    override fun onEvent(event: MyDataEvent, sequence: Long, endOfBatch: Boolean) {
        //注意这里小睡眠了一下!!
        TimeUnit.SECONDS.sleep(3)
        System.out.println("handle event's data: " + event.data + " isEndOfBatch:" + endOfBatch)
    }
}

class MyDataEventFactory : EventFactory<MyDataEvent> {
    override fun newInstance(): MyDataEvent {
        return MyDataEvent()
    }
}

class MyDataEventTranslatorWithIdAndValue : EventTranslatorVararg<MyDataEvent> {
    override fun translateTo(event: MyDataEvent?, sequence: Long, vararg args: Any?) {
        event!!.id = args[0] as Int
        event.data = args[1]?.toString()
    }
}

fun main() {
    //创建一个RingBuffer，注意容量是4。
    val ringBuffer = RingBuffer.createSingleProducer<MyDataEvent>(MyDataEventFactory(), 4)
    //创建一个事件处理器。
    val batchEventProcessor =
        /*
         * 注意参数：数据提供者就是RingBuffer、序列栅栏也来自RingBuffer
         * EventHandler使用自定义的。
         */
        BatchEventProcessor<MyDataEvent>(
            ringBuffer,
            ringBuffer.newBarrier(), MyDataEventHandler()
        )

    val batchEventProcessor2 =
        /*
         * 注意参数：数据提供者就是RingBuffer、序列栅栏也来自RingBuffer
         * EventHandler使用自定义的。
         */
        BatchEventProcessor<MyDataEvent>(
            ringBuffer,
            ringBuffer.newBarrier(), MyDataEventHandler()
        )
    //将事件处理器本身的序列设置为ringBuffer的追踪序列。
    ringBuffer.addGatingSequences(batchEventProcessor.getSequence())
    ringBuffer.addGatingSequences(batchEventProcessor2.getSequence())
    //启动事件处理器。
    Thread(batchEventProcessor).start()
    Thread(batchEventProcessor2).start()
    //往RingBuffer上发布事件
    for (i in 0..9) {
//        TimeUnit.SECONDS.sleep()
        ringBuffer.publishEvent(MyDataEventTranslatorWithIdAndValue(), i, i.toString() + "s")
        println("发布事件[$i]")
    }

    try {
        System.`in`.read()
    } catch (e: IOException) {
        e.printStackTrace()
    }

}