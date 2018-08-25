/**
 * FileName: itemSearchListener
 * Author:   coldwind
 * Date:     2018/8/20 19:22
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.happyShopping.search.service.listener;

import com.alibaba.fastjson.JSON;
import com.happyShopping.model.Item;
import com.happyShopping.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author coldwind
 * @create 2018/8/20
 * @since 1.0.0
 */
@Component
public class ItemSearchListener implements MessageListener{
    @Autowired
    private ItemSearchService itemSearchService;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("监听收到消息");
            //将接收到的字符串转为item对象的list
            String text = textMessage.getText();
            List<Item> itemList = JSON.parseArray(text, Item.class);
            itemSearchService.saveSKUList(itemList);
            System.out.println("索引库导入成功");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
