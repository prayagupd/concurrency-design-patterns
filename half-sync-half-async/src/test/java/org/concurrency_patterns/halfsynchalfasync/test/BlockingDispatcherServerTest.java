package org.concurrency_patterns.halfsynchalfasync.test;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;
import org.concurrency_patterns.halfsynchalfasync.blocking.BlockingDispatcherServer;
import org.zezutom.concurrencypatterns.test.util.DataUtil;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author: Tomas Zezula
 * Date: 24/08/2014
 */
public class BlockingDispatcherServerTest {

    public static final String IMAGE = "audrey_hepburn.jpeg";

    public static final String OUT_TEST = "audrey-test.txt";

    public static final String OUT_ORIGINAL = "audrey.txt";

    private BlockingDispatcherServer app = new BlockingDispatcherServer();

    @After
    public void cleanUp() {
        final File asciiFile = DataUtil.getFile(OUT_TEST);
        if (asciiFile.exists()) asciiFile.delete();
    }

    @Test
    public void asciiArtRocks() throws IOException {
        assertTrue(app.convertToAscii(IMAGE, OUT_TEST));
        assertTrue(FileUtils.contentEquals(DataUtil.getFile(OUT_ORIGINAL), DataUtil.getFile(OUT_TEST)));
    }

}
