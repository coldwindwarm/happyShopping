/**
 * FileName: testQueueSend
 * Author:   coldwind
 * Date:     2018/8/20 16:47
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */

import com.coldwind.demo.QueueProducer;
import com.coldwind.demo.TopicProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author coldwind
 * @create 2018/8/20
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-jms-producer.xml")
public class testQueueSend {
    @Autowired
    private QueueProducer queueProducer;
    @Test
    public void testQueueSend(){
        queueProducer.sendTextMessage("你好,spring_queue");
    }
}
