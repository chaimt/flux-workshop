package com.turel.fluxworkshop.examples.basic;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.logging.Logger;

/**
 * Created by chaimturkel on 7/5/16.
 * Some examples for creation of RXjava
 */
public class CreateObservableExample {

    static Logger log = Logger.getLogger(CreateObservableExample.class.getCanonicalName());

    static public void iterateExample() {
        Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        log.info("onNext: " + value);
                    }

                    @Override
                    public void onError(Throwable t) {
                        log.severe("error: " + t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        log.info("Complete!");
                    }
                });
    }

    static public Flux<String> factory() {
        return Flux.create(fluxSink -> {
            for (int ii = 0; ii < 10; ii++) {
                if (!fluxSink.isCancelled()) {
                    fluxSink.next("Pushed value " + ii);
                }
            }
            if (!fluxSink.isCancelled()) {
                fluxSink.complete();
            }
        });
    }

    static public void createExample() {
        Flux<String> createdObservable = factory();

        createdObservable.subscribe(
                (incomingValue) -> log.info("incomingValue " + incomingValue),
                (error) -> log.severe("Something went wrong" + (error).getMessage()),
                () -> log.info("This observable is finished")
        );
    }

    public static void rangeExample() {
        Flux.range(1, 5).subscribe(
                number -> log.info(String.valueOf(number)),
                error -> log.severe("error"),
                () -> log.info("completed")
        );

    }

    public static void main(String[] args) {
        CreateObservableExample.iterateExample();

        //how to create an observable with subscription
        CreateObservableExample.createExample();
        //create observable from list
        CreateObservableExample.iterateExample();
        //create observable from range
        CreateObservableExample.rangeExample();

    }
}
