/**
 * FileName: TestQueueRecieve
 * Author:   coldwind
 * Date:     2018/8/20 17:05
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author coldwind
 * @create 2018/8/20
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-jms-consumer-queue.xml")
public class TestQueueRecieve {
    @Test
    public void testQueueRecieve(){
        try {
            //让连接不关闭
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
