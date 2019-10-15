package com.hugang.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * jedis工具类
 */
@Component
public class JedisUtils {

    @Autowired
    private JedisPool jedisPool;


    /**
     * 字符串：放入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, String value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            if (null != jedis){
                jedis.close();
            }
        }
    }

    /**
     * 字符串：从缓存取出
     * @param key
     * @return 成功返回字符串，失败返回null
     */
    public String get(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            if (null != jedis){
                jedis.close();
            }
        }
    }

    /**
     * 删除一个或多个key
     * @param keys 一个key，或者String数组
     * @return 删除的key的个数
     */
    public Long del(String... keys){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(keys);
        } catch (Exception e){
            e.printStackTrace();
            return -1L;
        } finally {
            if (null != jedis){
                jedis.close();
            }
        }
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public Boolean exists(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            if (null != jedis){
                jedis.close();
            }
        }
    }

    /**
     * 删除当前数据库从所有的key
     */
    public void flushDB(){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.flushDB();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (null != jedis){
                jedis.close();
            }
        }
    }

    /**
     * 给key设置过期时间
     * @param key   健
     * @param time  过期时间
     * @return 成功返回1，失败返回-1
     */
    public Long expire(String key, int time){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.expire(key, time);
        } catch (Exception e){
            e.printStackTrace();
            return -1L;
        } finally {
            if (null != jedis){
                jedis.close();
            }
        }
    }

    /**
     * 对象存入缓存
     * @param key
     * @param object
     * @return
     */
    public Boolean setObject(String key, Object object){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key.getBytes(), SerializeUtils.serialize(object));
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            if (null != jedis){
                jedis.close();
            }
        }
    }

    /**
     * 从缓存取出对象
     * @param key
     * @return
     */
    public Object getObject(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            byte[] bytes = jedis.get(key.getBytes());
            if (null != bytes){
                return SerializeUtils.deserialize(bytes);
            }
            return null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            if (null != jedis){
                jedis.close();
            }
        }
    }

}
