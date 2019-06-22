package Distribute;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

/**
 * 分布式锁工具类
 *
 * @author ljp
 */
public class DistributeLockUtils implements MyLock {
    /**
     * redis中锁的key值
     */
    public static final String KEY = "DLock";

    /**
     * 过期时间
     */
    public static final int EXPIRESTIME = 5000;

    /**
     * 获取锁成功的标识
     */
    public static final String SUCCESS = "OK";

    /**
     * 最大自旋数
     */
    public static final int MAX_TIME = 100000;

    /**
     * 释放锁的lua脚本,判断是否和原本占有锁的value值一致,是则删除key即释放锁的意思
     */
    public static final String LUA_RELEASELOCK =
                    "local comValue = ARGV[1];\n" +
                    "local lockValue = redis.call('get',KEYS[1]);\n" +
                    "if lockValue == comValue then return redis.call('del',KEYS[1]); else return 0; end";
    public static String lua_release_sha;

    private static final JedisPool JEDIS_POOL = new JedisPool();


    private ThreadLocal<String> threadLocal = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return UUID.randomUUID().toString();
        }
    };
    private Jedis jedis;

    /**
     * 线程的唯一标识,作为redis中锁的value,表明占有锁的意思
     */
    private String value;

    /**
     * 获取分布式锁
     * 阻塞方法,只有获取分布式锁时才会返回
     * 自旋次数超过最大限制抛出
     */
    public void lock() throws Exception {
        if (jedis == null) {
            jedis = JEDIS_POOL.getResource();
        }
        value = threadLocal.get();
        int getTime = 0;
        //自旋获取锁,获取成功就返回
        while (!tryLock()) {
//            getTime++;
//            if (getTime > MAX_TIME) {
//                throw new Exception("Busy!try again!");
//            }

        }
    }

    public void unlock() {
        if (lua_release_sha == null || !jedis.scriptExists(lua_release_sha)) {
            lua_release_sha = jedis.scriptLoad(LUA_RELEASELOCK);
        }
        jedis.evalsha(lua_release_sha, 2, KEY, value);
        jedis.close();
    }


    /**
     * 新版本的set,也可以使用LUA脚本
     *
     * @param
     * @return 获取锁是否成功
     */
    private boolean tryLock() {
        String set = jedis.set(KEY, value, "NX", "PX", EXPIRESTIME);
        return SUCCESS.equals(set);
    }

}
