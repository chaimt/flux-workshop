package com.turel.fluxworkshop.examples.basic;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.math.MathFlux;
import utils.Utils;

import java.util.logging.Logger;

public class DebugExample {

    static Logger log = Logger.getLogger(DebugExample.class.getCanonicalName());

    public static Mono<Integer> viewStack() {

        Hooks.onOperatorDebug();

        return MathFlux.sumInt(
                Flux.just("1", "2", "a")
                        .map(d -> Integer.parseInt(d))
                        .map(d -> d+1));


    }

    public static void main(String[] args) {
        Utils.runWithSubscription(log, viewStack());
    }

}
