package com.happyShopping.manager.controller;

import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.happyShopping.model.Goods;
import com.happyShopping.model.Item;
import com.happyShopping.sellergoods.service.GoodsService;
import entity.GoodsGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;


import entity.PageResult;
import entity.Result;

import javax.jms.*;

/**
 * controller
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private GoodsService goodsService;
/*	@Reference
    private ItemSearchService itemSearchService;*/
    /*@Reference(timeout = 40000)
    private ItemPageService itemPageService;*/

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination queueSolrDestination;
    @Autowired
    private Destination queueSolrDeleteDestination;
    @Autowired
    private Destination topicPageDestination;
    @Autowired
    private Destination topicPageDeleteDestination;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public List<Goods> findAll() {
        return goodsService.findAll();
    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows) {
        return goodsService.findPage(page, rows);
    }

    /**
     * 增加
     *
     * @param goods
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Goods goods) {
        try {
            goodsService.add(goods);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     *
     * @param goodsGroup
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody GoodsGroup goodsGroup) {
        try {


            goodsService.update(goodsGroup);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public GoodsGroup findOne(Long id) {
        return goodsService.findOne(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            goodsService.delete(ids);
            //activemq的生产者,传递ids消息  用于删除相应的索引
            jmsTemplate.send(queueSolrDeleteDestination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    ObjectMessage objectMessage = session.createObjectMessage(ids);
                    return objectMessage;
                }
            });
            //activemq的生产者,传递ids消息 用于删除静态页面

            jmsTemplate.send(topicPageDeleteDestination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createObjectMessage(ids);
                }
            });

//			itemSearchService.deleteByGoodsIds(Arrays.asList(ids));
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     *
     * @param goods
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody Goods goods, int page, int rows) {
        return goodsService.findPage(goods, page, rows);
    }

    /**
     * 批量修改状态
     *
     * @param ids
     * @param status
     * @return
     */
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status) {
        try {
            //调用搜索服务和sellergoods 的服务
            //只有已审核的商品的SKU列表才缓存到索引库中
            if (status.equals("1")) {
                List<Item> list = goodsService.findItemListByGoodsIdAndStatus(ids, status);
                if (list != null && list.size() > 0) {
                    String jsonString = JSON.toJSONString(list);
                    jmsTemplate.send(queueSolrDestination, new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {
                            return session.createTextMessage(jsonString);
                        }
                    });

//					itemSearchService.saveSKUList(list);
                } else {
                    System.out.println("该商品没有具体的SKU列表");
                }
            }


            for (Long id : ids) {
                //静态页面生成  activemq 生产者生成ids给activemq
                jmsTemplate.send(topicPageDestination, new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage(id + "");
                    }
                });
//				generateHtml(id);
            }

            goodsService.updateStatus(ids, status);
            return new Result(true, "修改状态成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, "修改状态失败");
        }
    }
    /**
     * 生成静态页(测试)
     */
    /*@RequestMapping("/generateHtml")
	public void generateHtml(Long goodsId){
		itemPageService.generateItemPageHtml(goodsId);
	}*/
}
