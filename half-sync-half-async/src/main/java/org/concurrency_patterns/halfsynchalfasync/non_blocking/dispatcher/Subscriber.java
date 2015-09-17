package org.concurrency_patterns.halfsynchalfasync.non_blocking.dispatcher;

/**
 * @author: Tomas Zezula
 * Date: 24/08/2014
 */
public interface Subscriber {

    void onResult(boolean result);
}
