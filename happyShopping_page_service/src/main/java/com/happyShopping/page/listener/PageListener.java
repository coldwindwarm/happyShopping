/**
 * FileName: PageListener
 * Author:   coldwind
 * Date:     2018/8/20 20:18
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.happyShopping.page.listener;

import com.happyShopping.page.service.ItemPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author coldwind
 * @create 2018/8/20
 * @since 1.0.0
 */
@Component
public class PageListener implements MessageListener {
    @Autowired
    private ItemPageService itemPageService;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        System.out.println("监听到消息....");
        try {
            String id = textMessage.getText();
            itemPageService.generateItemPageHtml(Long.parseLong(id));
            System.out.println("监听到生成了页面 :...." + id + ".html");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
