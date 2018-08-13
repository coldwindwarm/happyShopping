/**
 * FileName: MapperTest
 * Author:   coldwind
 * Date:     2018/8/5 9:52
 * Description: 通用mapper类的测试
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.happyShopping.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 〈一句话功能简述〉<br> 
 * 〈通用mapper类的测试〉
 *
 * @author coldwind
 * @create 2018/8/5
 * @since 1.0.0
 */
public class MapperTest {
    private ApplicationContext applicationContext;
    @Before
    public void init(){
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring-mybatis.xml");
    }
    @Test
    public void testSpring(){
        //获取容器中所有的bean
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}
