package xyz.ivyxjc.dubbo.demo

import org.apache.dubbo.config.ProviderConfig
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import java.util.concurrent.CountDownLatch

@Configuration
@EnableDubbo(scanBasePackages = ["xyz.ivyxjc.dubbo.demo"])
@PropertySource("classpath:dubbo-provider.properties")
open class ProducerApplication {
    @Bean
    open fun providerConfig(): ProviderConfig {
        val providerConfig = ProviderConfig()
        providerConfig.timeout = 1000
        return providerConfig
    }

}

fun main() {
    val context = AnnotationConfigApplicationContext(ProducerApplication::class.java)
    context.start()
    CountDownLatch(1).await()
}