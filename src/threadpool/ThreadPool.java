package threadpool;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadPool {

    public BlockingQueueCustom<Runnable> taskQueue;
    int size = 0;
    int taskExecuted = 0;
    ThreadPoolsThread[] threadPoolsThread;

    public int getTaskExecuted() {
        return this.taskExecuted;
    }

    public synchronized void dectaskExec() {
        this.taskExecuted -= 1;
    }

    public int getSize() {
        return this.size;
    }

    public BlockingQueueCustom<Runnable> getTaskQueue() {
        return this.taskQueue;
    }

    public int getTaskQueueSize() {
        return this.taskQueue.size();
    }

    private static boolean poolShutDownInitiated = false;

    public ThreadPool(int nThreads) {
        this.taskQueue = new LinkedBlockingQueueCustom(nThreads);
        this.size = nThreads;
        this.threadPoolsThread = new ThreadPoolsThread[nThreads + 1];

        for (int i = 1; i <= nThreads; i++) {
            this.threadPoolsThread[i] = new ThreadPoolsThread(this.taskQueue, this);
            this.threadPoolsThread[i].setName("" + i);

            this.threadPoolsThread[i].start();
        }
    }

    public synchronized boolean execute(Runnable task) throws Exception {
        if (this.poolShutDownInitiated) {
           return false;
              //  throw new Exception("ThreadPool has been shutDown, no further tasks can be added");
           
        }
        this.taskExecuted += 1;

        try {
            this.taskQueue.put(task);
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public static synchronized boolean isPoolShutDownInitiated() {
        return ThreadPool.poolShutDownInitiated;
    }

    public synchronized void shutdown() {
        this.poolShutDownInitiated = true;
    }
}
