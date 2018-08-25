/**
 * FileName: QueneProducer
 * Author:   coldwind
 * Date:     2018/8/25 17:26
 * Description: 生产者
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.coldwind.demo.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈生产者〉
 *
 * @author coldwind
 * @create 2018/8/25
 * @since 1.0.0
 */
@RestController
public class QueneProducer {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
//    后缀名写.do和.action或者不写后缀名都能访问
    @RequestMapping("/send")
    public void send(String text){
        jmsMessagingTemplate.convertAndSend("name",text);
    }
    @RequestMapping("/sendMap")
    public void sendMap(){
        Map map = new HashMap();
        map.put("mobile","13999");
        map.put("name","coldwind");
        jmsMessagingTemplate.convertAndSend("map",map);
    }
    @RequestMapping("/sendsms")
    public void sendSms() {
        Map map = new HashMap();
        map.put("mobile", "13900001111");
        map.put("template_code", "SMS_85735065");
        map.put("sign_name", "黑马");
        map.put("param", "{\"number\":\"102931\"}");
        jmsMessagingTemplate.convertAndSend("sms", map);
    }

}
