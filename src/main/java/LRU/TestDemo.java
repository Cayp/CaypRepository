package LRU;

import java.util.Random;

public class TestDemo {
    public static void main(String[] args) {
        LRUTest lruTest = new LRUTest();
        for (int i = 0; i < 100; i++) {
            Random random = new Random();
            int i1 = random.nextInt(5);
            String cache = lruTest.getCache("key" + i1);
            if (cache == null) {
                System.out.println("缓存未命中");
                lruTest.saveCache(new LRUTest.CacheNode("key" + i1, "value" + i1));
            }else{
                System.out.println("缓存 value:"+cache);
            }

        }

    }
}
