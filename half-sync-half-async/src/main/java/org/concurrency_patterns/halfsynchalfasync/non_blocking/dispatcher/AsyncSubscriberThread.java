package org.concurrency_patterns.halfsynchalfasync.non_blocking.dispatcher;

/**
 * @author: Tomas Zezula
 * Date: 24/08/2014
 */
public class AsyncSubscriberThread implements Runnable, Subscriber {

    private boolean result;

    private NonBlockingDispatcher app;

    private long callTime;

    private long responseTime;

    private String imgPath, outPath;

    public AsyncSubscriberThread(String imgPath, String outPath) {
        this.imgPath = imgPath;
        this.outPath = outPath;
        app = new NonBlockingDispatcher(this);
    }

    @Override
    public void onResult(boolean result) {
        responseTime = System.currentTimeMillis();
        this.result = result;
    }

    @Override
    public void run() {
        app.dispatch(imgPath, outPath);
        callTime = System.currentTimeMillis();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException("Execution interrupted!");
        }
    }

    public boolean getResult() {
        return result;
    }

    public boolean isAsynchronous() {
        return callTime <= responseTime;
    }
}
