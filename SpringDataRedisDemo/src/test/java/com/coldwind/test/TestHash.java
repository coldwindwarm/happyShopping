/**
 * FileName: TestValue
 * Author:   coldwind
 * Date:     2018/8/16 14:14
 * Description: 测试SpringDataRedis
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.coldwind.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈测试SpringDataRedis〉
 *
 * @author coldwind
 * @create 2018/8/16
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-redis.xml")
public class TestHash {
    //注入redis模板
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 测试存和取  lhashMap类型的数据  右压栈--后添加的对象在后边
     */
    @Test
    public void testSetValue1() {
        redisTemplate.boundHashOps("HashName").put("a", "卢俊义");
        redisTemplate.boundHashOps("HashName").put("b", "鲁智深");
        redisTemplate.boundHashOps("HashName").put("c", "燕青");
    }

    /**
     * 根据某个key获取对应的值
     */
    @Test
    public void testGetValue() {
        Object o = redisTemplate.boundHashOps("HashName").get("a");
        System.out.println(o);
    }

    /**
     * 获取所有的key值
     */
    @Test
    public void testGetKeys() {
        Object hashName = redisTemplate.boundHashOps("HashName").keys();
        System.out.println(hashName);
    }

    /**
     * 获取所有的value值
     */
    @Test
    public void testGetValues() {
        Object hashName = redisTemplate.boundHashOps("HashName").values();
        System.out.println(hashName);
    }

    /**
     * 根据某个key移除对应的值
     */
    @Test
    public void testDeleteValueByKey() {
        redisTemplate.boundHashOps("HashName").delete("a");
    }
}
