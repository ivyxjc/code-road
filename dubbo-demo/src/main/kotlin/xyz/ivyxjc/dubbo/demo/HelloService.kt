//package xyz.ivyxjc.dubbo.demo
//
//import org.apache.dubbo.config.annotation.Method
//import org.apache.dubbo.config.annotation.Service
//
//interface HelloService {
//    fun sayHello(name: String): String
//
//    fun sayGoodbye(name: String): String {
//        return "Goodbye, $name "
//    }
//}
//
//@Service(version = Constants.VERSION)
//class HelloServiceImpl : HelloService {
//    override fun sayHello(name: String): String {
//        println("provider received invoke of sayHello: $name");
//        return "Annotation, hello $name";
//    }
//}