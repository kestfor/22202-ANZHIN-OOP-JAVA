package threadpool;

import java.util.LinkedList;

public class ThreadPool extends Thread {

    protected LinkedList<Worker> workers;

    public ThreadPool(int workersNum, BlockingQueue<Runnable> queue) {
        this.workers = new LinkedList<>();
        for (int i = 0; i < workersNum; i++) {
            workers.add(new Worker(queue));
        }
    }

    @Override
    public void run() {
        for (Worker worker : workers) {
            worker.start();
        }
    }
}
