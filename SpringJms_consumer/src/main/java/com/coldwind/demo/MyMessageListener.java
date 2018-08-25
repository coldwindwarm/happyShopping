/**
 * FileName: MyMessageListener
 * Author:   coldwind
 * Date:     2018/8/20 16:59
 * Description: 消费者监听器
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.coldwind.demo;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 〈一句话功能简述〉<br> 
 * 〈消费者监听器〉
 *
 * @author coldwind
 * @create 2018/8/20
 * @since 1.0.0
 */
public class MyMessageListener implements MessageListener{
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("topic模式接收到的信息:"+textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
