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

import java.util.Set;

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
public class TestSet {
    //注入redis模板
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 测试存和取  set类型的数据
     */
    @Test
    public void testSetValue(){
        redisTemplate.boundSetOps("nameSet").add("吴承恩");
        redisTemplate.boundSetOps("nameSet").add("曹雪芹");
        redisTemplate.boundSetOps("nameSet").add("施耐庵");
    }
    @Test
    public void testGetValue(){
        Set nameSet = redisTemplate.boundSetOps("nameSet").members();
        for (Object o : nameSet) {
            System.out.println(o);
        }
    }

    /**
     * 删除所有值
     */
    @Test
    public void testDeleteValue(){
        redisTemplate.delete("nameSet");
    }
    /**
     * 删除某一个值
     */
    @Test
    public void testDeleteOneValue(){
        redisTemplate.boundSetOps("nameSet").remove("吴承恩");
    }

}
