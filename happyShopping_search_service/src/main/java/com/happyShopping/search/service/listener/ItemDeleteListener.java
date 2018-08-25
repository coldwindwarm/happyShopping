/**
 * FileName: ItemDeleteListener
 * Author:   coldwind
 * Date:     2018/8/20 19:54
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.happyShopping.search.service.listener;

import com.happyShopping.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.Serializable;
import java.util.Arrays;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author coldwind
 * @create 2018/8/20
 * @since 1.0.0
 */
@Component
public class ItemDeleteListener implements MessageListener{
    @Autowired
    private ItemSearchService itemSearchService;
    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        System.out.println("监听到消息");
        try {
            Long[] ids = (Long[]) objectMessage.getObject();
            itemSearchService.deleteByGoodsIds(Arrays.asList(ids));
            System.out.println("删除索引成功"+ids);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
