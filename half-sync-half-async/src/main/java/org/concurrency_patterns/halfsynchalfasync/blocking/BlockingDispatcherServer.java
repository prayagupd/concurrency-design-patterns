package org.concurrency_patterns.halfsynchalfasync.blocking;

import org.concurrency_patterns.halfsynchalfasync.AsciiArtGenerator;

/**
 * A usual single-threaded implementation.
 *
 * @author: Tomas Zezula
 * Date: 24/08/2014
 */
public class BlockingDispatcherServer {

    public boolean convertToAscii(String imgPath, String outPath) {
        return new AsciiArtGenerator().convertToAscii(imgPath, outPath);
    }

    public static void main(String[] args) {
        boolean result = new BlockingDispatcherServer().convertToAscii("audrey_hepburn01.jpeg", "audrey.txt");
        System.out.println("RESULT: " + result);
    }
}
