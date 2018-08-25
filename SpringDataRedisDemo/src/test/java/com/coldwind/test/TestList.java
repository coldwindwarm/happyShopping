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
public class TestList {
    //注入redis模板
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 测试存和取  list类型的数据  右压栈--后添加的对象在后边
     */
    @Test
    public void testSetValue1(){
        redisTemplate.boundListOps("listName").rightPush("吴承恩");
        redisTemplate.boundListOps("listName").rightPush("曹雪芹");
        redisTemplate.boundListOps("listName").rightPush("施耐庵");
    }
    @Test
    public void testGetValue(){
        List listName = redisTemplate.boundListOps("listName").range(0, 10);
        for (Object o : listName) {
            System.out.println(o);
        }
    }

    /**
     * 测试存和取  list类型的数据  左压栈--后添加的对象在前边
     */
    @Test
    public void testSetValue12(){
        redisTemplate.boundListOps("listName").leftPush("吴承恩");
        redisTemplate.boundListOps("listName").leftPush("曹雪芹");
        redisTemplate.boundListOps("listName").leftPush("施耐庵");
    }
    @Test
    public void testDeleteValue(){
        redisTemplate.delete("listName");
    }
    /**
     * 根据索引查询元素
     */
    @Test
    public void testSearchByIndex(){
        Object listName = redisTemplate.boundListOps("listName").index(1);
        System.out.println(listName);
    }
    /**
     * 移除某个集合中的元素
     */
    @Test
    public void testRemoveByIndex(){
      redisTemplate.boundListOps("listName").remove(1,"施耐庵");
    }
}
