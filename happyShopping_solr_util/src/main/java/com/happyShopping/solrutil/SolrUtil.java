/**
 * FileName: SolrUtil
 * Author:   coldwind
 * Date:     2018/8/16 19:16
 * Description: solr工具类
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.happyShopping.solrutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.happyShopping.mapper.ItemMapper;
import com.happyShopping.model.Item;
import com.happyShopping.model.ItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈solr工具类〉
 *
 * @author coldwind
 * @create 2018/8/16
 * @since 1.0.0
 */
@Component
public class SolrUtil {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrTemplate solrTemplate;
    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath*:spring/spring*.xml");
        SolrUtil solrUtil = (SolrUtil) applicationContext.getBean("solrUtil");
        solrUtil.importItemData();
    }

    /**
     * 导入商品数据
     */
    public void importItemData(){
        ItemExample example = new ItemExample();
        ItemExample.Criteria criteria = example.createCriteria();
        //查出已审核的商品并导入solr索引库
        criteria.andStatusEqualTo("1");
        List<Item> itemList = itemMapper.selectByExample(example);
        for (Item item : itemList) {
            System.out.println(item.getId()+" "+ item.getTitle()+ " "+item.getPrice());
            //提取SKU的specJson字符串  转换成map
            Map map = JSON.parseObject(item.getSpec(), Map.class);
            item.setSpecMap(map);
        }

        //保存到索引库
        solrTemplate.saveBeans(itemList);
        solrTemplate.commit();
        System.out.println("结束");

    }
}
