package CountDown;

import java.util.concurrent.CountDownLatch;

/**
 * @author ljp
 */
public class Boss implements Runnable{

    private CountDownLatch countDownLatch;

    public Boss(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        try {
            //该方法会阻塞,等待计数器计数完才会返回
            countDownLatch.await();
            System.out.println("all worker finish!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
