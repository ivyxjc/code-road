//package xyz.ivyxjc.dubbo.demo.action
//
//import org.apache.dubbo.config.annotation.Reference
//import org.springframework.stereotype.Component
//import xyz.ivyxjc.dubbo.demo.Constants
//import xyz.ivyxjc.dubbo.demo.HelloService
//
//@Component("consumerAction")
//class ConsumerAction {
//
//    @Reference(interfaceClass = HelloService::class, version = Constants.VERSION)
//    private lateinit var helloService: HelloService
//
//    fun doSayHello(name: String): String {
//        return helloService.sayHello(name)
//    }
//}
