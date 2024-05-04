import threadpool.BlockingQueue;
import threadpool.ThreadPool;
import static java.lang.Thread.sleep;

public class TreadPoolTest {

    public static void addTasks(BlockingQueue<Runnable> queue) {
        for (int i = 0; i < 10; i++) {
            queue.put(() -> {
                try {
                    sleep(1000);
                    System.out.println("task done");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Runnable> queue = new BlockingQueue<>();
        ThreadPool pool = new ThreadPool(5, queue);
        pool.start();
        while (true) {
            addTasks(queue);
            sleep(10000);
        }
    }
}
