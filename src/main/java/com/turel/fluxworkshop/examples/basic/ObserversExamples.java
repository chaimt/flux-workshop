package com.turel.fluxworkshop.examples.basic;


import reactor.core.Disposable;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by chaimturkel on 10/10/16.
 */
public class ObserversExamples {
    static Logger log = Logger.getLogger(ObserversExamples.class.getCanonicalName());

    /**
     * simple example for how to do a Disposable.
     */
    public static void Disposable(){
        log.info("Disposable");

        final Flux<Integer> data = Flux.range(1, 5)
                .map(i -> i * 2)
                .map(i -> i * 10);

        Disposable subscribe = data.subscribe(
                number -> {
                    log.info("onNext -  " + number.toString());
                },
                error -> log.severe("error"),
                () -> log.info("completed"));
        subscribe.dispose();
    }

    /**
     * use a range 1-5 to create an observable
     * you need to add subscribeOn(Schedulers.newThread()) so it will run on another thread.
     * add sleep in map after 3 items
     * add Disposable
     * dispose before stream finishes
     */
    public static void dispose(){
        log.info("dispose");

        final Flux<Integer> data = Flux.range(1, 5)
                .subscribeOn(Schedulers.newSingle("test"))
                .map(i -> {
                    try {
                        if (i>2) Thread.sleep(700);
                    } catch (InterruptedException e) {
                    }
                    return i;
                });

        final Disposable subscribe = data.subscribe(
                number -> {
                    log.info("onNext -  " + number.toString());
                },
                error -> log.severe("error"),
                () -> log.info("completed")
        );


        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
        }

        subscribe.dispose();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

    /**
     * use a range 1-5 to create an observable
     * add map transformation
     * add doOnNext to print the current item
     * add 2 Disposables
     *
     * see in log how many times the original stream was run?
     *
     */
    public static void multipleDisposable(){
        log.info("multipleDisposable");

        final Flux<Integer> data = Flux.range(1, 5)
                .map(i -> i * 10)
                .doOnNext(i -> log.info("doOnNext - " + i));

        final Disposable subscribe = data.subscribe(
                number -> {
                    log.info("onNext -  " + number.toString());
                },
                error -> log.severe("error"),
                () -> log.info("completed")
        );

        final Disposable subscribe1 = data.subscribe(
                number -> {
                    log.info("onNext1 -  " + number.toString());
                },
                error -> log.severe("error"),
                () -> log.info("completed")
        );

        subscribe.dispose();
        subscribe1.dispose();
    }


    /**
     * use a range 1-5 to create an observable
     * add doOnNext to print the current item
     * add map transformation
     * add 2 Disposables
     *
     * Find a way so that original stream is run only once
     * see: https://medium.com/@p.tournaris/rxjava-one-observable-multiple-subscribers-7bf497646675#.y9npmnxhk
     */
    public static void multipleDisposableSingleRun(){
        log.info("multipleDisposableSingleRun");

        final Flux<Integer> data = Flux.range(1, 5)
                .map(i -> i * 10)
                .doOnNext(i -> log.info("doOnNext - " + i));


        final ConnectableFlux<Integer> publish = data.publish();

        final Disposable subscribe = publish.subscribe(
                number -> {
                    log.info("onNext -  " + number.toString());
                },
                error -> log.severe("error"),
                () -> log.info("completed")
        );

        final Disposable subscribe1 = publish.subscribe(
                number -> {
                    log.info("onNext1 -  " + number.toString());
                },
                error -> log.severe("error"),
                () -> log.info("completed")
        );

        publish.connect();
        log.info("refCount: " + publish.refCount());

        subscribe.dispose();
        subscribe1.dispose();
    }

    /**
     * use a range 1-5 to create an observable
     * add doOnNext to print the current item
     * add map transformation
     * add 2 Disposables
     *
     * Now use PublishSubject to Find a way so that original stream is run only once
     */
    public static void publishSubject(){
        log.info("PublishSubject");

//        PublishSubject<Integer> publisher = PublishSubject.create();
//        final Flux<Integer> data = Flux.range(1, 5)
//                .map(i -> i * 10)
//                .doOnNext(i -> log.info("doOnNext - " + i));
//
//
//        final Disposable subscribe = publisher.subscribe(
//                number -> {
//                    log.info("onNext -  " + number.toString());
//                },
//                error -> log.severe("error"),
//                () -> log.info("completed")
//        );
//
//        final Disposable subscribe1 = publisher.subscribe(
//                number -> {
//                    log.info("onNext1 -  " + number.toString());
//                },
//                error -> log.severe("error"),
//                () -> log.info("completed")
//        );
//
//        data.subscribe(publisher);
//
//        subscribe.dispose();
//        subscribe1.dispose();

    }

    /**
     * create PublishSubject with two Disposables
     * use the PublishSubject object to notify all Disposables by calling the onNext
     *
     */
    public static void publishSubjectNotification(){
        log.info("publishSubjectNotification");

//        PublishSubject<Integer> publisher = PublishSubject.create();
//
//        final Disposable subscribe = publisher.subscribe(
//                number -> {
//                    log.info("onNext -  " + number.toString());
//                },
//                error -> log.severe("error"),
//                () -> log.info("completed")
//        );
//
//        final Disposable subscribe1 = publisher.subscribe(
//                number -> {
//                    log.info("onNext1 -  " + number.toString());
//                },
//                error -> log.severe("error"),
//                () -> log.info("completed")
//        );
//
//        publisher.onNext(1);
//        publisher.onNext(2);
//
//        subscribe.dispose();
//        subscribe1.dispose();

    }

    /**
     * create PublishSubject with two Disposables
     * use the PublishSubject object to notify all Disposables by calling the onNext
     *
     * Register the second subscriber after calling the onNext once
     * Call the onNext a secound time.
     *
     * Which subscribers got which notifications?
     *
     */
    public static void publishSubjectLateDisposable(){
        log.info("publishSubjectLateDisposable");

//        Flux.c
//        PublishSubject<Integer> publisher = PublishSubject.create();
//
//        final Disposable subscribe = publisher.subscribe(
//                number -> {
//                    log.info("onNext -  " + number.toString());
//                },
//                error -> log.severe("error"),
//                () -> log.info("completed")
//        );
//
//
//        publisher.onNext(1);
//
//        final Disposable subscribe1 = publisher.subscribe(
//                number -> {
//                    log.info("onNext1 -  " + number.toString());
//                },
//                error -> log.severe("error"),
//                () -> log.info("completed")
//        );
//        publisher.onNext(2);
//
//        subscribe.dispose();
//        subscribe1.dispose();

    }


    /**
     * create PublishSubject with two Disposables
     * use the PublishSubject object to notify all Disposables by calling the onNext
     *
     * Register the second subscriber after calling the onNext once
     * Call the onNext a secound time.
     *
     * Use ReplaySubject to fix previous issue so that second subscriber will get all onNext calls
     *
     * How does it work, what is the expense for the feature?
     */
    public static void replaySubject(){
        log.info("replaySubject");

//        ReplaySubject<Integer> publisher = ReplaySubject.create();
//
//        final Disposable subscribe = publisher.subscribe(
//                number -> {
//                    log.info("onNext -  " + number.toString());
//                },
//                error -> log.severe("error"),
//                () -> log.info("completed")
//        );


//        publisher.onNext(1);
//
//        final Disposable subscribe1 = publisher.subscribe(
//                number -> {
//                    log.info("onNext1 -  " + number.toString());
//                },
//                error -> log.severe("error"),
//                () -> log.info("completed")
//        );
//        publisher.onNext(2);
//
//        subscribe.dispose();
//        subscribe1.dispose();

    }

    /**
     * create ReplaySubject with two Disposables
     * use the ReplaySubject object to notify all Disposables by calling the onNext
     *
     * Register the second subscriber after calling the onNext once
     * Call the onNext a secound time.
     *
     * Use ReplaySubject to fix previous issue so that second subscriber will get all onNext calls
     *
     * Limit the time of the cache to 1 secound
     */

    public static void replaySubjectWithTime(){
        log.info("replaySubjectWithTime");

//        ReplaySubject<Integer> publisher = ReplaySubject.createWithTime(1, TimeUnit.SECONDS,Schedulers.computation());
//
//        final Disposable subscribe = publisher.subscribe(
//                number -> {
//                    log.info("onNext -  " + number.toString());
//                },
//                error -> log.severe("error"),
//                () -> log.info("completed")
//        );
//
//
//        publisher.onNext(1);
//
//        try {
//            Thread.sleep(1100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        final Disposable subscribe1 = publisher.subscribe(
//                number -> {
//                    log.info("onNext1 -  " + number.toString());
//                },
//                error -> log.severe("error"),
//                () -> log.info("completed")
//        );
//
//        publisher.onNext(2);
//
//        subscribe.dispose();
//        subscribe1.dispose();

    }

    /**
     * create BehaviorSubject with two Disposables
     * use the BehaviorSubject object to notify all Disposables by calling the onNext
     *
     * Register the second subscriber after calling the onNext twice
     * Call the onNext a third time.
     *
     * How is this different than the ReplaySubject or PublishSubject
     *
     */
    public static void behaviorSubject(){
        log.info("behaviorSubject");

//        BehaviorSubject<Integer> publisher = BehaviorSubject.create();
//
//        final Disposable subscribe = publisher.subscribe(
//                number -> {
//                    log.info("onNext -  " + number.toString());
//                },
//                error -> log.severe("error"),
//                () -> log.info("completed")
//        );
//
//
//        publisher.onNext(1);
//        publisher.onNext(2);
//
//        final Disposable subscribe1 = publisher.subscribe(
//                number -> {
//                    log.info("onNext1 -  " + number.toString());
//                },
//                error -> log.severe("error"),
//                () -> log.info("completed")
//        );
//        publisher.onNext(3);
//
//        subscribe.dispose();
//        subscribe1.dispose();

    }

    /**
     * create AsyncSubject with two Disposables
     * use the AsyncSubject object to notify all Disposables by calling the onNext
     *
     * Register the second subscriber after calling the onNext twice
     * Call the onNext a third time.
     *
     * Make sure to call the onComplete
     *
     * What does this Subject object do?
     *
     */
    public static void asyncSubject(){
        log.info("asyncSubject");

//        AsyncSubject<Integer> publisher = AsyncSubject.create();
//
//        final Disposable subscribe = publisher.subscribe(
//                number -> {
//                    log.info("onNext -  " + number.toString());
//                },
//                error -> log.severe("error"),
//                () -> log.info("completed")
//        );
//
//
//        publisher.onNext(1);
//        publisher.onNext(2);
//
//        final Disposable subscribe1 = publisher.subscribe(
//                number -> {
//                    log.info("onNext1 -  " + number.toString());
//                },
//                error -> log.severe("error"),
//                () -> log.info("completed")
//        );
//        publisher.onNext(3);
//        publisher.onCompleted();
//
//        subscribe.dispose();
//        subscribe1.dispose();

    }

    public static void main(String[] args) {
        Disposable();
        dispose();
        multipleDisposable();
        multipleDisposableSingleRun();
        publishSubject();
        publishSubjectNotification();
        publishSubjectLateDisposable();
        replaySubject();
        replaySubjectWithTime();
        behaviorSubject();
        asyncSubject();
    }
}
