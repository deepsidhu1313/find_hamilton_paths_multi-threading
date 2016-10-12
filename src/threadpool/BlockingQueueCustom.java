package threadpool;

abstract interface BlockingQueueCustom<E>
{
  public abstract void put(E paramE)
    throws InterruptedException;
  
  public abstract E take()
    throws InterruptedException;
  
  public abstract int size();
}
