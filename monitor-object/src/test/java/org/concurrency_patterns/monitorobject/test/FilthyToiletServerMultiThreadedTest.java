package org.concurrency_patterns.monitorobject.test;

import org.concurrency_patterns.monitorobject.FilthyToiletServer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.concurrency_patterns.monitorobject.ToiletServer;
import org.concurrency_patterns.monitorobject.ToiletFloodedException;
import org.zezutom.concurrencypatterns.test.util.TestExecutor;

import static org.junit.Assert.assertTrue;

/**
 * @author Tomas Zezula
 * Date: 27/07/2014
 */
public class FilthyToiletServerMultiThreadedTest {

    public static final long MIN_WAIT_MILLIS = 100;

    public static final long MAX_WAIT_MILLIS = 120;

    private static volatile ToiletServer toiletServer;

    private static Runnable oneTimePatron;

    private static Runnable peacefulMind;

    private static Runnable frequentFlier;

    private static int toiletFloodedCount;

    private enum Load {
        MODERATE("a moderate"),
        HEAVY("a heavy"),
        EXTREME("an extreme");

        private String value;

        private Load(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    @BeforeClass
    public static void setUp() {

        oneTimePatron = new Runnable() {
            @Override
            public void run() {
                setUsageTest(100, 1);
            }
        };

        peacefulMind = new Runnable() {
            @Override
            public void run() {
                setUsageTest(MAX_WAIT_MILLIS, 1);
            }
        };

        frequentFlier = new Runnable() {
            @Override
            public void run() {
                setUsageTest(105, 3);
            }
        };
    }

    private static void setUsageTest(long acquireMillis, int visitCount) {
        for (int i = 0; i < visitCount; i++) {
            try {
                if (toiletServer.enter()) {
                    Thread.sleep(acquireMillis);
                    toiletServer.quit();
                }
            } catch (InterruptedException e) {
                // Don't bother
            } catch (ToiletFloodedException e) {
                toiletFloodedCount++;
            }

            // Another round ahead?
            if (i < visitCount - 1) {
                final long waitPeriod = (long) (Math.random() * (MAX_WAIT_MILLIS - MIN_WAIT_MILLIS) + MIN_WAIT_MILLIS);
                try {
                    Thread.sleep(waitPeriod);
                } catch (InterruptedException e) {
                    // No worries
                }
            }
        }
    }

    @Before
    public void init() {
        toiletFloodedCount = 0;
        toiletServer = new FilthyToiletServer();
    }

    @Test
    public void testRegularTraffic() {
        runTest(Load.MODERATE, TestExecutor.get(10, 5), oneTimePatron);
    }

    @Test
    public void testPeakHour() {
        runTest(Load.HEAVY, TestExecutor.get(10, 15), frequentFlier, peacefulMind);
    }

    @Test
    public void testBusyBeyondBelief() {
        runTest(Load.EXTREME, TestExecutor.get(20, 25), oneTimePatron, peacefulMind, frequentFlier);
    }

    private void runTest(Load load, TestExecutor executor, Runnable ... clients) {
        executor.runTest(clients);
        assertIncidents(load);
    }

    private void assertIncidents(Load load) {
        System.out.println(String.format("The toiletServer was flooded %d times under %s load.", toiletFloodedCount, load));
        assertTrue("The toiletServer was unexpectedly clean.", toiletFloodedCount > 0);
    }
}
