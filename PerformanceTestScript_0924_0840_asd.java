// 代码生成时间: 2025-09-24 08:40:00
package com.example.performance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@RestController
public class PerformanceTestScript {

    private final RestTemplate restTemplate;
    private final ExecutorService executorService;

    public PerformanceTestScript(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.executorService = Executors.newFixedThreadPool(10); // Adjust thread pool size as needed
    }

    @GetMapping("/testPerformance")
    public String testPerformance() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        AtomicInteger counter = new AtomicInteger(0);

        List<Runnable> tasks = new ArrayList<>();

        // Create tasks to simulate requests
        for (int i = 0; i < 100; i++) { // Adjust the number of requests
            tasks.add(() -> {
                try {
                    restTemplate.getForObject("http://localhost:8080/api/resource", String.class); // Replace with actual URL
                    counter.incrementAndGet();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        // Execute tasks in parallel
        tasks.forEach(executorService::execute);
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        return "Performance Test Completed. Total requests: " + counter.get() + ", Duration: " + duration + " ms";
    }

    public static void main(String[] args) {
        SpringApplication.run(PerformanceTestScript.class, args);
    }
}
