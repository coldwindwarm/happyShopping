/**
 * FileName: SpringDataSolrTest
 * Author:   coldwind
 * Date:     2018/8/16 17:12
 * Description: 测试SpringDataSolr
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.coldwind.test;

import com.coldwind.pojo.Item;
import org.apache.solr.client.solrj.SolrServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Crotch;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈测试SpringDataSolr〉
 *
 * @author coldwind
 * @create 2018/8/16
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springSolr.xml")
public class SpringDataSolrTest {
    @Autowired
    private SolrTemplate solrTemplate;

    /**
     * 增加
     */
    @Test
    public void add(){
        Item item=new Item();
        item.setId(1L);
        item.setBrand("华为");
        item.setCategory("手机");
        item.setGoodsId(1L);
        item.setSeller("华为2号专卖店");
        item.setTitle("华为Mate9");
        item.setPrice(new BigDecimal(2000));

        //保存到solr并提交
        solrTemplate.saveBean(item);
        solrTemplate.commit();
    }
    /**
     * 删除全部数据
     */
    @Test
    public void testDeleteAll(){
        SimpleQuery simpleQuery = new SimpleQuery("*:*");
        solrTemplate.delete(simpleQuery);
        solrTemplate.commit();
    }
    /**
     * 按主键查询
     */
    @Test
    public void testFindOneById(){
        Item item = solrTemplate.getById(1, Item.class);
        System.out.println(item.getTitle());
    }
    /**
     * 按主键删除
     */
    @Test
    public void testDeleteById(){
        solrTemplate.deleteById("1");
        solrTemplate.commit();
    }
    /**
     * 分页查询
     */
    @Test
    public void testPageQuery(){
        SimpleQuery query = new SimpleQuery("*:*");
        //设置分页开始处的位置,默认为0
        query.setOffset(10);
        //设置每页多少条数据,默认为10
        query.setRows(20);
        //查出数据
        ScoredPage<Item> page = solrTemplate.queryForPage(query, Item.class);
        //page.getTotalElements():查出总记录数
        System.out.println("总记录数:"+page.getTotalElements());
        List<Item> content = page.getContent();
        showList(content);
    }

    /**
     * 显示记录数据
     */
    public void showList(List<Item> list){
        for (Item item : list) {
            System.out.println(item.getTitle()+item.getPrice());
        }
    }
    //首先循环插入100条测试数据
    @Test
    public void testAddList(){
        List<Item> list=new ArrayList();

        for(int i=0;i<100;i++){
            Item item=new Item();
            item.setId(i+1L);
            item.setBrand("华为");
            item.setCategory("手机");
            item.setGoodsId(1L);
            item.setSeller("华为2号专卖店");
            item.setTitle("华为Mate"+i);
            item.setPrice(new BigDecimal(2000+i));
            list.add(item);
        }

        solrTemplate.saveBeans(list);
        solrTemplate.commit();
    }
    /**
     * 条件查询
     */
    @Test
    public void testCriteriaSearch(){
        SimpleQuery query = new SimpleQuery("*:*");
        //拼接条件
        Criteria criteria = new Criteria("item_title").contains("2");
        criteria = criteria.and("item_title").contains("5");
        //将条件加入到查询语句中
        query.addCriteria(criteria);
        ScoredPage<Item> page = solrTemplate.queryForPage(query, Item.class);
        //查出总数
        System.out.println("总计数:"+page.getTotalElements());
        //打印出分页的 默认起始位置0,每页10条数据
        List<Item> content = page.getContent();
        showList(content);
    }
}
