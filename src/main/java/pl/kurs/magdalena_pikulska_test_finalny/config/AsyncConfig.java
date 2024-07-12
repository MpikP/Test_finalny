package pl.kurs.magdalena_pikulska_test_finalny.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Value("${task.executor.corePoolSize}")
    private int corePoolSize;

    @Value("${task.executor.maxPoolSize}")
    private int maxPoolSize;

    @Value("${task.executor.queueCapacity}")
    private int queueCapacity;

    @Bean
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("thread-");
        executor.initialize();
        return executor;
    }
}
