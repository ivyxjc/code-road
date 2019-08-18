//package xyz.ivyxjc.dubbo.demo
//
//import org.springframework.context.annotation.AnnotationConfigApplicationContext
//import org.springframework.context.annotation.ComponentScan
//import org.springframework.context.annotation.Configuration
//import org.springframework.context.annotation.PropertySource
//import xyz.ivyxjc.dubbo.demo.action.ConsumerAction
//
//
//@Configuration
////@EnableDubbo(scanBasePackages = ["xyz.ivyxjc.dubbo.demo.action"])
//@PropertySource("classpath:dubbo-consumer.properties")
//@ComponentScan(value = ["xyz.ivyxjc.dubbo.demo.action"])
//open class ConsumerApplication
//
//
//fun main() {
//    val context = AnnotationConfigApplicationContext(ConsumerApplication::class.java)
//    context.start()
//    val consumserAction = context.getBean("consumerAction") as ConsumerAction
//    val t1 = System.currentTimeMillis()
//    println("+++++++++++++++++++++++++++++++++++++++")
//    for (i in 0 until 10000) {
//        consumserAction.doSayHello("world")
//    }
//    println("+++++++++++++++++++++++++++++++++++++++")
//    val t2 = System.currentTimeMillis()
//    println(t2 - t1)
//}
//
