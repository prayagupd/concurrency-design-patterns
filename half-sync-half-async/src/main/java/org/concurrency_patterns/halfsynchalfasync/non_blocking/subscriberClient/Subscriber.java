package org.concurrency_patterns.halfsynchalfasync.non_blocking.subscriberClient;

/**
 * @author: Tomas Zezula
 * Date: 24/08/2014
 */
public interface Subscriber {

    void onResult(boolean result);
}
