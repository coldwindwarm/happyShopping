/**
 * FileName: ItemPageServiceImpl
 * Author:   coldwind
 * Date:     2018/8/19 16:52
 * Description: 实现静态页面的方法
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.happyShopping.page.service.impl;


import com.happyShopping.mapper.GoodsDescMapper;
import com.happyShopping.mapper.GoodsMapper;
import com.happyShopping.mapper.ItemCatMapper;
import com.happyShopping.mapper.ItemMapper;
import com.happyShopping.model.*;
import com.happyShopping.page.service.ItemPageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈实现静态页面的方法〉
 *
 * @author coldwind
 * @create 2018/8/19
 * @since 1.0.0
 */
@Service
public class ItemPageServiceImpl implements ItemPageService {
    //spring的注入属性值
//    @Value("${pagedir}")
    private String pagedir = "E:\\item\\";
    @Autowired
    private FreeMarkerConfig freeMarkerConfig;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsDescMapper goodsDescMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private ItemMapper itemMapper;

    /**
     * 根据商品id生成商品详细页静态页面
     *
     * @param goodsId
     * @return
     */
    @Override
    public boolean generateItemPageHtml(Long goodsId) {
        try {
            Configuration configuration = freeMarkerConfig.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            //传入数据(商品和商品描述)到模板
            Map map = new HashMap();
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
            map.put("goods", goods);
            GoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
            map.put("goodsDesc", goodsDesc);

            //商品分类

            String category1Name = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
            String category2Name = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
            String category3Name = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
            map.put("category1Name", category1Name);
            map.put("category2Name", category2Name);
            map.put("category3Name", category3Name);


            //SKU信息  已审核的  传来SPUId的
            ItemExample example = new ItemExample();
            ItemExample.Criteria criteria = example.createCriteria();
            criteria.andGoodsIdEqualTo(goodsId);
            criteria.andStatusEqualTo("1");
            //按照默认排序,保证第一个为默认SKU
            example.setOrderByClause("is_default desc");
            List<Item> itemList = itemMapper.selectByExample(example);
            map.put("itemList", itemList);


            //用io流将模板和数据输出成html
            FileWriter fileWriter = new FileWriter(pagedir + goodsId + ".html");
            template.process(map, fileWriter);
            fileWriter.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean deleteGenerateItemPageHtml(Long[] goodsIds) {
        try {
            for (Long goodsId : goodsIds) {
                new File("E:\\item\\" + goodsId + ".html").delete();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
