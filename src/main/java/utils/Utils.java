package utils;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by chaimturkel on 6/29/16.
 */
public class Utils {

    static public org.apache.http.HttpResponse getResponse(String url) throws RuntimeException {
        try {
            final RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3 * 1000).build();
            final HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
            HttpGet httpGet = new HttpGet(url);
            org.apache.http.HttpResponse response = httpClient.execute(httpGet);
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    static public String httpEntityToString(org.springframework.http.HttpEntity entity) {
//        try {
//            String res = EntityUtils.toString(entity);
//            EntityUtils.consume(entity);
//            return res;
//        } catch (Exception e) {
//            return "";
//        }
//    }

    public static void runWithSubscription(Logger log, Flux<?> observable) {
        runWithSubscription(log, observable, 0, false);
    }

    public static void runWithSubscription(Logger log, Flux<?> observable, int sleepMilli) {
        runWithSubscription(log, observable, sleepMilli, false);
    }

    public static void runWithSubscription(Logger log, Flux<?> observable, int sleepMilli, boolean threadInfo) {
        observable
                .subscribe(
                        value -> {
                            if (threadInfo) {
                                log.info("Observing: " + Thread.currentThread().getName() + " - " + value);
                            } else log.info(value.toString());
                        },
                        error -> log.severe("error"),
                        () -> log.info("completed")
                );

        if (sleepMilli > 0) {
            try {
                Thread.sleep(sleepMilli);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void runWithSubscription(Logger log, Mono<?> observable) {
        runWithSubscription(log, observable, 0, false);
    }


    public static void runWithSubscription(Logger log, Mono<?> observable, int sleepMilli, boolean threadInfo) {
        observable
                .subscribe(
                        value -> {
                            if (threadInfo) {
                                log.info("Observing: " + Thread.currentThread().getName() + " - " + value);
                            } else log.info(value.toString());
                        },
                        error -> log.severe("error: " + error),
                        () -> log.info("completed")
                );

        if (sleepMilli > 0) {
            try {
                Thread.sleep(sleepMilli);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void quietSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
