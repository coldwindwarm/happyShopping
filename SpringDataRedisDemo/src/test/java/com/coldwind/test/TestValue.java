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
public class TestValue {
    //注入redis模板
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 测试存和取普通的键值对
     */
    @Test
    public void testSetValue(){
        redisTemplate.boundValueOps("name").set("coldwind");
    }
    @Test
    public void testGetValue(){
        String name = (String) redisTemplate.boundValueOps("name").get();
        System.out.println(name);
    }

    /**
     * 删除某一个值
     */
    @Test
    public void testDeleteValue(){
        redisTemplate.delete("name");
    }

}
