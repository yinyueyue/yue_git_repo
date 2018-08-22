package com.pax.sms.core.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

/**
 * com.pax.sms.cache
 *
 * @author yyyty
 * @time :  2018/6/19
 * @description:
 */
@Component
public class PaxRedisTemplate {


    private static final Logger LOGGER = LoggerFactory.getLogger(PaxRedisTemplate.class);

    @Autowired
    private JedisCluster jedisCluster;

    @Value("${spring.redis.session.timeout}")
    private int EXPIRE_SECONDS;


    private static final String KEY_SPLIT = ":"; //用于隔开缓存前缀与缓存键值

    /**
     * 设置缓存
     *
     * @param prefix 缓存前缀（用于区分缓存，防止缓存键值重复）
     * @param key    缓存key
     * @param value  缓存value
     */
    public void set(String prefix, String key, String value) {
        jedisCluster.set(prefix + KEY_SPLIT + key, value);
        LOGGER.info("RedisUtil:set cache key={},value={}", prefix + KEY_SPLIT + key, value);
    }

    /**
     * 设置缓存 存储存储对象类型
     *
     * @param prefix 缓存前缀（用于区分缓存，防止缓存键值重复）
     * @param key    缓存key
     * @param value  缓存value
     * @param t      缓存对象类型
     */
    public <T> void set(String prefix, String key, String value, T t) {
        //内部没有实现序列化操作，为了高性能采用自定义的序列化操作，而不使用Serializable这个类
        RuntimeSchema<T> schema = RuntimeSchema.createFrom((Class<T>) t.getClass());
        byte[] bytes = ProtobufIOUtil.toByteArray(t, schema,
                LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        jedisCluster.set((prefix + KEY_SPLIT + key).getBytes(), bytes);
        LOGGER.info("RedisUtil:set cache key={},value={}", prefix + KEY_SPLIT + key, value);
    }

    /**
     * 设置缓存，并且自己指定过期时间
     *
     * @param prefix
     * @param key
     * @param value
     * @param expireTime 过期时间
     */
    public <T> void setWithExpireTimeObj(String prefix, String key, T value, int expireTime) {
        RuntimeSchema<T> schema = RuntimeSchema.createFrom((Class<T>) value.getClass());
        byte[] bytes = ProtobufIOUtil.toByteArray(value, schema,
                LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        jedisCluster.setex((prefix + KEY_SPLIT + key).getBytes(), expireTime, bytes);
        LOGGER.info("RedisUtil:setWithExpireTime cache key={},value={},expireTime={}", prefix + KEY_SPLIT + key, value,
                expireTime);
    }

    /**
     * 设置缓存，并且自己指定过期时间
     *
     * @param prefix
     * @param key
     * @param value
     * @param expireTime 过期时间
     */
    public void setWithExpireTimeStr(String prefix, String key, String value, int expireTime) {
        jedisCluster.setex(prefix + KEY_SPLIT + key, expireTime, value);
        LOGGER.info("RedisUtil:setWithExpireTime cache key={},value={},expireTime={}", prefix + KEY_SPLIT + key, value,
                expireTime);
    }

    /**
     * 设置缓存，并且由配置文件指定过期时间
     *
     * @param prefix
     * @param key
     * @param value
     */
    public void setWithExpireTime(String prefix, String key, String value) {
        jedisCluster.setex(prefix + KEY_SPLIT + key, EXPIRE_SECONDS, value);
        LOGGER.info("RedisUtil:setWithExpireTime cache key={},value={},expireTime={}", prefix + KEY_SPLIT + key, value,
                EXPIRE_SECONDS);
    }

    /**
     * 获取指定key的缓存
     *
     * @param prefix
     * @param key
     */
    public String get(String prefix, String key) {
        String value = jedisCluster.get(prefix + KEY_SPLIT + key);
        LOGGER.info("RedisUtil:get cache key={},value={}", prefix + KEY_SPLIT + key, value);
        return value;
    }

    /**
     * 获取指定key的缓存
     *
     * @param key
     */
    public String get(String key) {
        String value = jedisCluster.get(key);
        LOGGER.info("RedisUtil:get cache key={},value={}", key, value);
        return value;
    }

    /**
     * 获取指定key的缓存
     *
     * @param prefix
     * @param key
     */
    public <T> T get(String prefix, String key, T t) {
        RuntimeSchema<T> schema = RuntimeSchema.createFrom((Class<T>) t.getClass());
        byte[] bytes = jedisCluster.get((prefix + KEY_SPLIT + key).getBytes());
        if (bytes != null) {
            //空对象
            T data = schema.newMessage();
            ProtobufIOUtil.mergeFrom(bytes, data, schema);//seckill被反序列化
            LOGGER.info("RedisUtil:get cache key={},value={}", prefix + KEY_SPLIT + key, data);
            return data;
        }
        return null;
    }

    /**
     * 删除指定key的缓存
     *
     * @param prefix
     * @param key
     */
    public void deleteWithPrefix(String prefix, String key) {
        jedisCluster.del(prefix + KEY_SPLIT + key);
        LOGGER.info("RedisUtil:delete cache key={}", prefix + KEY_SPLIT + key);
    }

    public void deleteWithPrefixBytes(String prefix, String key) {
        jedisCluster.del((prefix + KEY_SPLIT + key).getBytes());
        LOGGER.info("RedisUtil:delete cache key={}", prefix + KEY_SPLIT + key);
    }

    public void delete(String key) {
        jedisCluster.del(key);
        LOGGER.info("RedisUtil:delete cache key={}", key);
    }

    public void deleteByeKey(String key) {
        jedisCluster.del(key.getBytes());
        LOGGER.info("RedisUtil:delete cache key={}", key);
    }
}
