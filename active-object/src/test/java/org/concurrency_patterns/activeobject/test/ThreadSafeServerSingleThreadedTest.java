package org.concurrency_patterns.activeobject.test;

import org.concurrency_patterns.activeobject.Server;
import org.junit.Before;
import org.junit.Test;
import org.concurrency_patterns.activeobject.ThreadSafeServer;

import static org.junit.Assert.assertEquals;

/**
 * @author Tomas Zezula
 *
 * Proves the core functionality, all tests should pass.
 */
public class ThreadSafeServerSingleThreadedTest {

    // The value the server is initialized with
    public static final long INITIAL_VALUE = 10L;

    private Server server;

    @Before
    public void init() {
        server = new ThreadSafeServer(INITIAL_VALUE);
    }

    @Test
    public void get() {
        assertEquals(INITIAL_VALUE, server.get());
    }

    @Test
    public void incrementAndGet() {
        final long expected = INITIAL_VALUE + 1;
        assertEquals(expected, server.incrementAndGet());
        assertEquals(expected, server.get());
    };

    @Test
    public void getAndIncrement() {
        assertEquals(INITIAL_VALUE, server.getAndIncrement());
        assertEquals(INITIAL_VALUE + 1, server.get());
    };

    @Test
    public void decrementAndGet() {
        final long expected = INITIAL_VALUE - 1;
        assertEquals(expected, server.decrementAndGet());
        assertEquals(expected, server.get());
    };

    @Test
    public void getAndDecrement() {
        assertEquals(INITIAL_VALUE, server.getAndDecrement());
        assertEquals(INITIAL_VALUE - 1, server.get());
    };
}
