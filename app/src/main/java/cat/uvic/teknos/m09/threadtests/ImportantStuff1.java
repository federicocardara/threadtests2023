package cat.uvic.teknos.m09.threadtests;

import java.io.Serializable;

public class ImportantStuff1 {
    public void doStuff() {
        try {
            Thread.sleep(10*1000);
            System.out.println("Done important stuff 1");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
