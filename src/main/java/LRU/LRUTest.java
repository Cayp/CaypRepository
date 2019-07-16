package LRU;

import java.util.HashMap;

/**
 * LRU缓存原理
 *
 * @author ljp
 */
public class LRUTest {
    private HashMap<String, CacheNode> map = new HashMap<String, CacheNode>();
    private CacheNode head;
    private CacheNode tail;
    //缓存map容量
    private static final int capacity = 5;
    private int size = 0;

    /**
     * 从map中取出cache,没有则返回null
     *
     * @param key
     * @return
     */
    public String getCache(String key) {
        CacheNode cacheNode = map.get(key);
        return cacheNode == null ? null : refresh(cacheNode).value;
    }

    /**
     * 重新放到头部
     *
     * @param cacheNode
     * @return
     */
    private CacheNode refresh(CacheNode cacheNode) {
        if (cacheNode != head) {
            head.pre = cacheNode;
            CacheNode pre = cacheNode.pre;
            pre.next = cacheNode.next;
            if (cacheNode == tail) {
                tail = pre;
            } else {
                cacheNode.next.pre = pre;
            }
            cacheNode.next = head;
            cacheNode.pre = null;
            head = cacheNode;
        }
        return head;
    }

    /**
     * 存入缓存,插入头部
     *
     * @param cacheNode
     */
    public void saveCache(CacheNode cacheNode) {
        map.put(cacheNode.key, cacheNode);
        System.out.println("存入:" + cacheNode.key);
        if (size == 0) {
            head = cacheNode;
            tail = cacheNode;
            size++;
            return;
        }
        if (size + 1 > capacity) {
            map.remove(tail.key);
            tail = tail.pre;
            tail.next = null;
            System.out.println("淘汰:" + cacheNode.key);
        } else {
            size++;
        }
        head.pre = cacheNode;
        cacheNode.next = head;
        head = cacheNode;
    }

    static class CacheNode {
        String key;
        String value;
        CacheNode pre;
        CacheNode next;

        public CacheNode(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public void setPre(CacheNode pre) {
            this.pre = pre;
        }

        public void setNext(CacheNode next) {
            this.next = next;
        }
    }
}
