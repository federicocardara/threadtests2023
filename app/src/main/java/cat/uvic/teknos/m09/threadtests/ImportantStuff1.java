package cat.uvic.teknos.m09.threadtests;

import java.io.Serializable;

public class ImportantStuff1 implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(10*1000);
            System.out.println("Done important stuff 1");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
