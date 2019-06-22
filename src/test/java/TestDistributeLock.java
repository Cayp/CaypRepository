import Distribute.DistributeLockUtils;
import org.junit.Test;

import java.util.Scanner;

public class TestDistributeLock {
    public static final Runnable runnable = new Runnable() {
        public void run() {
            DistributeLockUtils distributeLockUtils = new DistributeLockUtils();
            try {
                distributeLockUtils.lock();
                System.out.println(Thread.currentThread().getName()+"获取锁");
                Thread.sleep(2000);
                distributeLockUtils.unlock();
            } catch (Exception e) {
                System.out.println("Threadname:"+Thread.currentThread().getName()+"获取锁超时");
                e.printStackTrace();
            }
        }
    } ;

    @Test
    public void test() {
        for (int i = 0; i < 100 ; i++) {
            new Thread(runnable).start();
        }
        Scanner scanner = new Scanner(System.in);
        scanner.next();
    }

}
