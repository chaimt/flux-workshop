package com.turel.fluxworkshop.examples.basic;

import com.google.gson.JsonParser;
import reactor.core.publisher.Flux;

import java.util.logging.Logger;

/**
 * Created by chaimturkel on 10/7/16.
 */
public class RealAppExample {
    static Logger log = Logger.getLogger(RealAppExample.class.getCanonicalName());

    /**
     * combine all requests
     * map them to requests using: Utils.getResponse(url)
     * add a retry of 2 in case of network failure
     * filer out all request that are not HttpStatus.SC_OK
     * aggregate to list
     * on subscribe print elements
     */
    public static void realApp() {
        final JsonParser jsonParser = new JsonParser();

        Flux<String> urlRequest1 = Flux.just("http://jsonplaceholder.typicode.com/posts/1");
        Flux<String> urlRequest2 = Flux.just("http://jsonplaceholder.typicode.com/posts/2");
        Flux<String> urlRequest3 = Flux.just("http://jsonplaceholder.typicode.com/error/2");

        Flux<String> allRequests = Flux.concat(urlRequest1, urlRequest2, urlRequest3);


        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        realApp();
    }
}
