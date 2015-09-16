package org.concurrency_patterns.halfsynchalfasync.non_blocking;

/**
 * @author: Tomas Zezula
 * Date: 24/08/2014
 */
public interface ResultSubscriber {

    void onResult(boolean result);
}
