/**
 * FileName: QueueProducer
 * Author:   coldwind
 * Date:     2018/8/20 16:36
 * Description: 点对点生产者
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.coldwind.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * 〈一句话功能简述〉<br> 
 * 〈点对点生产者〉
 *
 * @author coldwind
 * @create 2018/8/20
 * @since 1.0.0
 */
@Component
public class QueueProducer {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination queueTextDestination;

    /**
     * 发送文本信息
     */
    public void sendTextMessage(final String text){
        jmsTemplate.send(queueTextDestination,new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(text);
            }
        });

    }
}
