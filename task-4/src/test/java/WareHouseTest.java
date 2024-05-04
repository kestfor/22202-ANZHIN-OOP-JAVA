import factory.Warehouses.Warehouse;
import threadpool.BlockingQueue;
import threadpool.ThreadPool;

import static java.lang.Thread.sleep;

public class WareHouseTest {
    public static void main(String[] args) throws InterruptedException {
        Warehouse<Integer> test = new Warehouse<>(10);
        Thread t1 = new Thread(() -> {
            while (true) {
                test.put(10);
                System.out.println("put value");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread t2 = new Thread(() -> {
            while (true) {
                test.get();
                System.out.println("get value");
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread t3 = new Thread(() -> {
            while (true) {
                System.out.println("curr size: " + test.getCurrentSize());
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t3.start();
        t1.start();
        t2.start();
    }
}
