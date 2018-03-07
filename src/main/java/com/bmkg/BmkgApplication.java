package com.bmkg;

import com.bmkg.async.DataAsync;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication(
        scanBasePackages = {"com.bmkg", "com.bmkg.controller"})
@EnableAsync
@EnableScheduling
public class BmkgApplication implements CommandLineRunner {

    @Autowired
    private DataAsync bmkg;

    public static void main(String[] args) {
        SpringApplication.run(BmkgApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
//        bmkg.data();
    }

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(11);
        executor.setMaxPoolSize(11);
//        executor.setQueueCapacity(500);
//        executor.setThreadNamePrefix("GithubLookup-");
        executor.initialize();
        return executor;
    }

}
