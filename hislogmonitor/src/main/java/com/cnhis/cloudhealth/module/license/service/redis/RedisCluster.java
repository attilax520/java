package com.cnhis.cloudhealth.module.license.service.redis;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis集群操作类
 * 
 * @author xiaohui.pu
 *
 * @param <K>
 * @param <V>
 */
@Component("redisCluster")
public class RedisCluster<K, V> {

	@Resource
	RedisTemplate<K, V> redisTemplate;

	public V get(K key) {
		return redisTemplate.boundValueOps(key).get();
	}

	public void set(K key, V value) {
		redisTemplate.opsForValue().set(key, value);
	}

	public void set(K key, V value, long time, TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(key, value, time, timeUnit);
	}

	/**
	 * 递增值
	 * 
	 * @param key
	 */
	public long incrAdd(K key) {
		return redisTemplate.boundValueOps(key).increment(1);
	}

	/**
	 * 增加固定值
	 * 
	 * @param key
	 * @param value
	 */
	public long incrAdd(K key, int value) {
		return redisTemplate.boundValueOps(key).increment(value);
	}

	/**
	 * 递减值
	 * 
	 * @param key
	 */
	public long incrSub(K key) {
		return redisTemplate.boundValueOps(key).increment(-1);
	}

	/**
	 * 减少固定值
	 * 
	 * @param key
	 * @param value
	 */
	public long incrSub(K key, int value) {
		return redisTemplate.boundValueOps(key).increment(value);
	}

	/**
	 * 删除
	 * 
	 * @param key
	 */
	public void delete(K key) {
		redisTemplate.delete(key);
	}

	/**
	 * 删除
	 * 
	 * @param key
	 */
	public void del(K key) {
		redisTemplate.delete(key);
	}

	public Set<K> getKeys(K pattern) {
		return redisTemplate.keys(pattern);
	}

	/**
	 * 判断是否存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(K key) {
		return redisTemplate.hasKey(key);
	}

	public Object hashGet(K key, byte[] name) {
		return redisTemplate.boundHashOps(key).get(name);
	}
	
	/**
	 * 超时设置
	 * 
	 * @param key
	 * @param timeout
	 * @param unit
	 */
	public void expire(K key, long timeout, TimeUnit unit) {
		redisTemplate.expire(key, timeout, unit);
	}
}
