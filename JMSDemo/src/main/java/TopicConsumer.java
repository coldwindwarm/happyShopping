/**
 * FileName: QueneProducer
 * Author:   coldwind
 * Date:     2018/8/20 15:43
 * Description: 点对点模式生产者
 * History:
 * <author>          <time>          <version>          <desc>
 */

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 〈一句话功能简述〉<br>
 * 〈发布订阅模式生产者〉
 *
 * @author coldwind
 * @create 2018/8/20
 * @since 1.0.0
 */
public class TopicConsumer {
    public static void main(String[] args) throws JMSException, IOException {
        //创建连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.175.132:61616");
        //获取连接
        Connection connection = connectionFactory.createConnection();
        //启动连接
        connection.start();
        //获取session(第一个参数为:是否启动事务  第二个参数为:消息确认的模式)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //创建队列对象
        Topic topic = session.createTopic("test_topic");
        //创建消息消费者
        MessageConsumer consumer = session.createConsumer(topic);
        //监听消息(匿名内部类)
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage message1 = (TextMessage) message;
                try {
                    System.out.println("接收到消息:"+message1.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        //等待键盘输入  为了能够接收到信息
        System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();



    }

}
