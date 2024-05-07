package server;

import java.util.Iterator;
import java.util.LinkedList;

public class BlockingQueue<T> implements Iterable<T> {

    protected LinkedList<T> queue;
    protected int capacity;


    public BlockingQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
    }

    public int size() {
        return queue.size();
    }

    public boolean isFull() {
        return queue.size() == capacity;
    }

    synchronized public T get() {
        notify();
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
        while (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        queue.add(el);
        notify();
    }

    synchronized public void remove(T obj) {
        this.queue.remove(obj);
        notify();
    }

    @Override
    synchronized public Iterator<T> iterator() {
        return queue.iterator();
    }
}