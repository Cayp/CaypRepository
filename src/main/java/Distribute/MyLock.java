package Distribute;

public interface MyLock {
    void lock() throws Exception;

    void unlock();
}
