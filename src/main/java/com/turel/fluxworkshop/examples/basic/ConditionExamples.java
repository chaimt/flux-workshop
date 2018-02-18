package com.turel.fluxworkshop.examples.basic;

import reactor.core.publisher.Flux;
import utils.Utils;

import java.util.logging.Logger;

/**
 * Created by chaimturkel on 10/10/16.
 */
public class ConditionExamples {
    static Logger log = Logger.getLogger(ConditionExamples.class.getCanonicalName());

    /**
     * create a range 1-5
     * run the range twice
     */
    public static Flux<Integer> repeat() {
        log.info("subscription");
        return Flux.range(1, 5)
                .repeat(2);
    }


    /**
     * create a range 1-5
     * run while number is below or equal 3 (do not use filter)
     */
    public static Flux<Integer> whileAbove() {
        log.info("subscription");
        return Flux.range(1, 5)
                .takeWhile(i -> i <= 3);
    }

    public static void main(String[] args) {
        Utils.runWithSubscription(log,repeat());
        Utils.runWithSubscription(log, whileAbove());
    }
}
