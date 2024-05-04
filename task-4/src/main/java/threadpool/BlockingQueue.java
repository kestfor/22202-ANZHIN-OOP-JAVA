package threadpool;

import java.util.LinkedList;

public class BlockingQueue<T> {

    protected LinkedList<T> queue;

    public BlockingQueue() {
        this.queue = new LinkedList<>();
    }

    synchronized public T get() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return queue.pop();
    }

    synchronized public void put(T el) {
        queue.add(el);
        notify();
    }

}