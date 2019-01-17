/**   
* @Title: ExampleService.java 
* @Package com.ppfuns.aaa.ad.log.service
* @Description: TODO
* @author admin
* @date 2014年6月3日 下午3:39:58 
* @version V1.0   
*/
package com.ppfuns.aaa.service;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** 
* @ClassName: RedisService 
* @Description:RedisService 
* @author admin
* @date 2014年6月3日 下午3:39:58 
*  
*/
@Service
public class RedisService {

	//@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private Lock lock = new ReentrantLock();

	//******************************************String Ops **********************************************
	/**
	 * 
	* @Title: set 
	* @Description: 添加数据 
	* @param key
	* @param value   
	* @return void   
	* @throws
	 */
	public void set(final String key, final String value) {
		this.redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(key.getBytes(), value.getBytes());
				return null;
			}
		});
	}

	/**
	 * 
	* @Title: append 
	* @Description: 追加数据 
	* @param key
	* @param value
	* @return   
	* @return long   
	* @throws
	 */
	public long append(final String key, final String value) {
		long sum = this.redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.append(key.getBytes(), value.getBytes());
			}
		});

		return sum;
	}

	/**
	 * 
	* @Title: set 
	* @Description: 添加带有过期时间的数据 
	* @param key
	* @param value
	* @param seconds   
	* @return void   
	* @throws
	 */
	public void set(final String key, final String value, final long seconds) {
		this.redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				connection.setEx(key.getBytes(), seconds, value.getBytes());
				return null;
			}
		});
	}

	/***
	 * 
	* @Title: del 
	* @Description: 删除key下数据 
	* @param key
	* @return   
	* @return long   
	* @throws
	 */
	public long del(final String key) {
		long s = this.redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				long sum = connection.del(key.getBytes());
				return sum;
			}
		});

		return s;
	}

	/**
	 * 
	* @Title: del 
	* @Description: 删除数据keys数据 
	* @param keys
	* @return 返回删除数据size  
	* @return long   
	* @throws
	 */
	public long del(final String... keys) {
		long s = this.redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				long sum = 0;
				for (String key : keys) {
					sum = sum + connection.del(key.getBytes());
				}

				return sum;
			}
		});

		return s;
	}

	/**
	 * 
	* @Title: get 
	* @Description: 获取数据 
	* @param key
	* @return   
	* @return String   
	* @throws
	 */
	public String get(final String key) {
		String value = this.redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				if (connection.exists(key.getBytes())) {
					byte[] values = connection.get(key.getBytes());
					return redisTemplate.getStringSerializer().deserialize(values);
				}
				return null;
			}
		});
		return value;
	}

	/**
	 * 
	* @Title: size 
	* @Description: 获取key下size 
	* @param key
	* @return   
	* @return long   
	* @throws
	 */
	public long size(final String key) {
		long s = this.redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				long sum = connection.sCard(key.getBytes());
				return sum;
			}
		});
		return s;

	}

	/**
	 * 
	* @Title: exist 
	* @Description: 是否存在key 
	* @param key
	* @return true/false  
	* @return boolean   
	* @throws
	 */
	public boolean exist(final String key) {

		boolean b = this.redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				boolean bool = connection.exists(key.getBytes());
				return bool;
			}
		});

		return b;
	}

	/**
	 * 
	* @Title: keys 
	* @Description: 获取keys 
	* @param pattern
	* @return   
	* @return Set<byte[]>   
	* @throws
	 */
	public List<String> keys(final String pattern) {
		return this.redisTemplate.execute(new RedisCallback<List<String>>() {
			@Override
			public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
				Set<byte[]> set = connection.keys(pattern.getBytes());
				List<String> list = new ArrayList<String>();
				for (byte[] b : set) {
					list.add(new String(b));
				}
				return list;
			}
		});
	}

	//******************************************List Ops**************************************************

	/**
	 * 
	* @Title: listSetR 
	* @Description: 左添加数据 
	* @param key
	* @param value
	* @return 添加数据size  
	* @return long   
	* @throws
	 */
	public long listSetR(final String key, final String value) {
		return this.redisTemplate.boundListOps(key).rightPush(value);
	}

	/**
	 * 
	* @Title: listSetR 
	* @Description: 添加带有过期时间的数据 
	* @param key
	* @param value
	* @param seconds
	* @return   
	* @return long   
	* @throws
	 */
	public long listSetR(final String key, final String value, final long seconds) {
		long sum = this.redisTemplate.boundListOps(key).rightPush(value);
		boolean bool = this.redisTemplate.boundListOps(key).expire(seconds, TimeUnit.SECONDS);
		if (bool) {
			return sum;
		}
		return 0;
	}

	/**
	 * 
	* @Title: listSetR 
	* @Description: 批量添加数据 
	* @param key
	* @param values
	* @return   
	* @return long   
	* @throws InterruptedException
	 */
	public long listSetR(final String key, final String... values) throws InterruptedException {
		lock.lockInterruptibly();
		try {
			return this.redisTemplate.opsForList().rightPushAll(key, values);
		} finally {
			lock.unlock();
		}

	}

	/**
	 * 
	* @Title: listSetR 
	* @Description: 带有过期时间的批量数据添加 
	* @param key
	* @param seconds
	* @param values
	* @return
	* @throws InterruptedException   
	* @return long   
	* @throws
	 */
	public long listSetR(final String key, final long seconds, final String... values) throws InterruptedException {
		lock.lockInterruptibly();
		long sum = 0;
		try {
			sum = this.redisTemplate.boundListOps(key).rightPushAll(values);
			boolean bool = this.redisTemplate.boundListOps(key).expire(seconds, TimeUnit.SECONDS);
			if (bool) {
				return sum;
			}
		} finally {
			lock.unlock();
		}
		return 0;
	}

	/**
	 * 
	* @Title: listDel 
	* @Description: 删除数据 
	* @param key
	* @return   
	* @return boolean   
	* @throws
	 */
	public boolean listDel(final String key) {
		this.redisTemplate.boundListOps(key).expire(1, TimeUnit.MILLISECONDS);
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {

		}
		return this.redisTemplate.boundListOps(key).persist();
	}

	/**
	 * 
	* @Title: listGet 
	* @Description: 获取集合数据 
	* @param key
	* @return   
	* @return String   
	* @throws
	 */
	public String listGet(final String key) {
		return this.redisTemplate.boundListOps(key).leftPop();

	}

	/**
	 * 
	* @Title: listSize 
	* @Description: 获取集合大小 
	* @param key
	* @return   
	* @return long   
	* @throws
	 */
	public long listSize(final String key) {
		return this.redisTemplate.boundListOps(key).size();
	}

}
