package org.concurrency_patterns.activeobject.test;

import org.concurrency_patterns.activeobject.Server;
import org.concurrency_patterns.activeobject.ThreadSafeServer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zezutom.concurrencypatterns.test.util.TestExecutor;

import static org.junit.Assert.assertEquals;

/**
 * @author Tomas Zezula
 *
 * Proves that the implementation of org.zezutom.concurrencypatterns.org.zezutom.concurrencypatterns.activeobject.ThreadSafeServer
 * is thread-safe, as the server - under race conditions - consistently returns expected values.
 */
public class ThreadSafeServerMultiThreadedTest {

    // The value the server is initialized with
    public static final long INITIAL_VALUE = 10L;

    // Concurrent server threads: per-method commands
    private static Runnable getCommand;
    private static Runnable incrementAndGetCommand;
    private static Runnable getAndIncrementCommand;
    private static Runnable decrementAndGetCommand;
    private static Runnable getAndDecrementCommand;

    // Multi-threaded org.zezutom.concurrencypatterns.monitorobject.test executor
    private static TestExecutor testExecutor;

    // An instance of the tested class. Being 'volatile' indicates it's going to be used by multiple threads
    private static volatile Server server;

    // The value of the server prior to any testing
    private long startValue;

    @BeforeClass
    public static void init() {
        // Instantiates the server with the initial value
        server = new ThreadSafeServer(INITIAL_VALUE);

        // Initializes multi-threaded org.zezutom.concurrencypatterns.monitorobject.test executor
        testExecutor = TestExecutor.get();

        // Initializes individual commands
        getCommand =                new Runnable() {@Override public void run() { server.get(); } };
        incrementAndGetCommand =    new Runnable() {@Override public void run() { server.incrementAndGet(); } };
        getAndIncrementCommand =    new Runnable() {@Override public void run() { server.getAndIncrement(); } };
        decrementAndGetCommand =    new Runnable() {@Override public void run() { server.decrementAndGet(); } };
        getAndDecrementCommand =    new Runnable() {@Override public void run() { server.getAndDecrement(); } };
    }

    @Before
    public void setUp() {
        startValue = server.get();
    }

    @Test
    public void get() {
        testExecutor.runTest(getCommand);
        assertEquals(startValue, server.get());
    }

    @Test
    public void incrementAndGet() {
        testExecutor.runTest(incrementAndGetCommand);
        assertEquals(getExpectedIncrementedValue(), server.get());
    }

    @Test
    public void getAndIncrement() {
        testExecutor.runTest(getAndIncrementCommand);
        assertEquals(getExpectedIncrementedValue(), server.get());
    }

    @Test
    public void decrementAndGet() {
        testExecutor.runTest(decrementAndGetCommand);
        assertEquals(getExpectedDecrementedValue(), server.get());
    }

    @Test
    public void getAndDecrement() {
        testExecutor.runTest(getAndDecrementCommand);
        assertEquals(getExpectedDecrementedValue(), server.get());
    }

    @Test
    public void runAll() {
        testExecutor.runTest(getCommand,
                incrementAndGetCommand,
                getAndIncrementCommand,
                decrementAndGetCommand,
                getAndDecrementCommand);
        assertEquals(startValue, server.get());
    }

    private long getExpectedIncrementedValue() {
        return startValue + TestExecutor.MAX_ITERATIONS * TestExecutor.DEFAULT_CONCURRENT_THREADS;
    }

    private long getExpectedDecrementedValue() {
        return startValue - TestExecutor.MAX_ITERATIONS * TestExecutor.DEFAULT_CONCURRENT_THREADS;
    }

}
