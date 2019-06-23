package CountDown;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch计数器
 * @author ljp
 */
public class Worker implements Runnable{
    private CountDownLatch countDownLatch;

    public Worker(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        System.out.println("worker is working,name:"+Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("worker work finish,name:"+Thread.currentThread().getName());
        countDownLatch.countDown();

    }
}
