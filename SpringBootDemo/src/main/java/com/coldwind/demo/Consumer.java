/**
 * FileName: Consumer
 * Author:   coldwind
 * Date:     2018/8/25 17:29
 * Description: 消费者
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.coldwind.demo;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈消费者〉
 *
 * @author coldwind
 * @create 2018/8/25
 * @since 1.0.0
 */
@Component
public class Consumer {
    @JmsListener(destination = "name")
    public void readMessage(String text){
        System.out.println("接收到消息"+text);
    }
    @JmsListener(destination = "map")
    public void readMessage(Map map){
        System.out.println(map);
    }
}
