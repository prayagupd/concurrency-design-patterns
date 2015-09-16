package org.concurrency_patterns.monitorobject.test;

import org.junit.Before;
import org.junit.Test;
import org.concurrency_patterns.monitorobject.CleanToiletServer;
import org.concurrency_patterns.monitorobject.ToiletServer;
import org.concurrency_patterns.monitorobject.ToiletFloodedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Tomas Zezula
 * Date: 27/07/2014
 *
 */
public class CleanToiletServerSingleThreadedTest {

    private ToiletServer toiletServer;

    @Before
    public void init() {
        toiletServer = new CleanToiletServer();
    }

    @Test
    public void acquireVacantToilet() throws ToiletFloodedException {
        assertTrue(toiletServer.enter());
        assertTrue(toiletServer.isOccupied());
    }

    @Test
    public void leaveOccupiedToilet() throws ToiletFloodedException {
        toiletServer.enter();
        toiletServer.quit();
        assertFalse(toiletServer.isOccupied());
    }
}
