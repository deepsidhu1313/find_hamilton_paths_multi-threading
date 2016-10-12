package threadpool;

import java.util.logging.Level;
import java.util.logging.Logger;

class ThreadPoolsThread
        extends Thread {

    private BlockingQueueCustom<Runnable> taskQueue;
    private ThreadPool threadPool;

    public ThreadPoolsThread(BlockingQueueCustom<Runnable> queue, ThreadPool threadPool) {
        this.taskQueue = queue;
        this.threadPool = threadPool;
    }

    public void run() {
        for (;;) {
            Runnable runnable = null;
            while ((!this.threadPool.isPoolShutDownInitiated()) && (this.taskQueue.size() == 0)) {
//            System.out.println("Thread " + Thread.currentThread().getName() + " is in first loop");
            }

            if ((this.threadPool.isPoolShutDownInitiated()) && (this.taskQueue.size() == 0)) {
                break;
            }
// System.out.println("Thread " + Thread.currentThread().getName() + " is ready");

            try {
                runnable = (Runnable) this.taskQueue.take();
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadPoolsThread.class.getName()).log(Level.SEVERE, null, Thread.currentThread().getName()+" : "+ex);
                break;
            }

            if (runnable == null) {
               continue;
            }

//            System.out.println("Thread " + Thread.currentThread().getName() + " has taken the task");

            runnable.run();
//            System.out.println("Thread " + Thread.currentThread().getName() + " stopped");

            if ((this.threadPool.isPoolShutDownInitiated())
                    && (this.taskQueue.size() == 0)) {
                interrupt();

                try {
                    Thread.sleep(1L);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadPoolsThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
