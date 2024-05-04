package threadpool;

public class Worker extends Thread {

    public BlockingQueue<Runnable> queue;

    public Worker(BlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            Runnable task = queue.get();
            task.run();
        }
    }
}
