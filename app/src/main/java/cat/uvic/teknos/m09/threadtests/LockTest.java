package cat.uvic.teknos.m09.threadtests;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    private static String message;
    private static Random random = new Random();
    public static void main(String[] args) {
       for (var i = 1; i <= 10; i++) {
           var t1 = new Thread(LockTest::printMessage, "Background thread-" + i);
           var t2 = new Thread(LockTest::setMessage, "Interactive thread-" + i);

           t1.start();
           t2.start();
       }

        System.out.println("Working...");
    }

    public static void printMessage() {
        lock.lock();
        try {
            if (message == null) {
                condition.await();
            } else {
                System.out.println(Thread.currentThread().getName() + " prints: " + message);
                message = null;
                condition.signalAll();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public static void setMessage() {
        lock.lock();
        try {
            if (message == null) {
                var scanner = new Scanner(System.in);
                System.out.println("[" + Thread.currentThread().getName() + "] Type a new message");
                message = scanner.nextLine();

                condition.signalAll();
            } else {
                condition.await();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
