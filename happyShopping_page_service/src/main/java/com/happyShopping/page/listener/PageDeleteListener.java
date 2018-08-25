/**
 * FileName: PageDeleteListener
 * Author:   coldwind
 * Date:     2018/8/20 20:46
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.happyShopping.page.listener;

import com.happyShopping.page.service.ItemPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author coldwind
 * @create 2018/8/20
 * @since 1.0.0
 */
@Component
public class PageDeleteListener implements MessageListener {
    @Autowired
    private ItemPageService itemPageService;

    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            System.out.println("监听到消息....");
            Long[] ids = (Long[]) objectMessage.getObject();
            itemPageService.deleteGenerateItemPageHtml(ids);
            System.out.println("删除静态页面成功....");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
