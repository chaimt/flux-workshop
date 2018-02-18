package com.turel.fluxworkshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping("/randomNumbers")
    public Flux<Double> getReactiveRandomNumbers() {
        return generateRandomNumbers(10, 500);
    }

    /**
     * Non-blocking randon number generator
     * @param amount - # of numbers to generate
     * @param delay - delay between each number generation in milliseconds
     * @return
     */
    public Flux<Double> generateRandomNumbers(int amount, int delay){
        return Flux.range(1, amount)
                .delaySubscription(Duration.of(delay, ChronoUnit.MILLIS))
                .map(i -> Math.random());
    }
}
