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

/**
 * 〈一句话功能简述〉<br>
 * 〈点对点模式生产者〉
 *
 * @author coldwind
 * @create 2018/8/20
 * @since 1.0.0
 */
public class TopicProducer {
    public static void main(String[] args) throws JMSException {
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
        //创建消息生产者
        MessageProducer producer = session.createProducer(topic);
        //创建消息
        TextMessage textMessage = session.createTextMessage("activemq发布订阅模式入门小demo");
        //发送消息
        producer.send(textMessage);
        //关闭资源
        producer.close();
        session.close();
        connection.close();



    }

}
