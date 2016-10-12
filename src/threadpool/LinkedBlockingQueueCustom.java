package threadpool;

import java.util.LinkedList;
import java.util.List;

public class LinkedBlockingQueueCustom<E>
        implements BlockingQueueCustom<E> {

    private List<E> queue;
    private int maxSize;

    public static int sSize=0;

    public static int getsSize() {
        return sSize;
    }
    
    public LinkedBlockingQueueCustom(int maxSize) {
        this.maxSize = maxSize;
        this.queue = new LinkedList();
    }

    @Override
    public synchronized void put(E item)
            throws InterruptedException {
        if (this.queue.size() == this.maxSize) {
            wait();
        }

        this.queue.add(item);
        notifyAll();
    }

    @Override
    public synchronized E take()
            throws InterruptedException {
        if (this.queue.isEmpty() && ThreadPool.isPoolShutDownInitiated()==false) {
            wait();
        }

        notifyAll();
        if (this.queue.isEmpty()) {
            return null;
        }
        E e=(E) this.queue.remove(0);
//        System.out.println("Removed Element "+size());
        sSize=size();
        return e;
    }

    @Override
    public synchronized int size() {
        return this.queue.size();
    }
}
