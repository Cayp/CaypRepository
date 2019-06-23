package CountDown;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class TestDemo {


    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        //新建计数器,并定义数
        CountDownLatch countDownLatch = new CountDownLatch(4);
        executor.execute(new Worker(countDownLatch));
        executor.execute(new Worker(countDownLatch));
        executor.execute(new Worker(countDownLatch));
        executor.execute(new Worker(countDownLatch));
        executor.execute(new Boss(countDownLatch));
    }
}
