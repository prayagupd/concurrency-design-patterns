package org.concurrency_patterns.monitorobject;

/**
 * @author Tomas Zezula
 * Date: 27/07/2014
 *
 */
public interface ToiletServer {

    boolean enter();

    void quit();

    boolean isOccupied();
}
