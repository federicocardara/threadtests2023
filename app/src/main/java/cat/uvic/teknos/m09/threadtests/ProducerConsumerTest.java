package cat.uvic.teknos.m09.threadtests;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProducerConsumerTest {
    private static final int MIN_ELEMENTS = 0;
    private static final int MAX_ELEMENTS = 5;
    private List<Integer> numbers = new ArrayList<>();
    private boolean stop;

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    private Random random = new Random();

    public void produce() {
        while (!stop) {
            synchronized (this) {
                if (numbers.size() < MAX_ELEMENTS) {
                    var nextInt = random.nextInt(0, 1000);
                    numbers.add(nextInt);
                    System.out.println("Added " + random.nextInt(0, 1000));
                    notify();
                } else {
                    System.out.println("numbers already has 5 elements");
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("Exiting synchronization");
            }
        }

        System.out.println("Producer ended");
    }

    public void consume() {
        while(!stop) {
            synchronized (this) {
                if (numbers.size() > MIN_ELEMENTS) {
                    var removedInt = numbers.remove(numbers.size() - 1);
                    System.out.println("Removed: " + removedInt);
                } else {
                    System.out.println("No elements");
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                notify();
            }
        }

        System.out.println("consumer ended");
    }

    public static void main(String[] args) throws InterruptedException {
        var producerConsumerTest = new ProducerConsumerTest();

        var t1 = new Thread(producerConsumerTest::produce);
        var t2 = new Thread(producerConsumerTest::consume);

        t1.start();
        t2.start();

        Thread.sleep(10*1000);

        producerConsumerTest.setStop(true);

        t1.join();
        t2.join();

        System.out.println("produce/consumer test ended");
    }
}
