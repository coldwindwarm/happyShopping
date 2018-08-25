/**
 * FileName: ItemSearchServiceImpl
 * Author:   coldwind
 * Date:     2018/8/16 21:32
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.happyShopping.search.service.impl;

import com.happyShopping.search.service.ItemSearchService;
import com.happyShopping.model.Item;
import com.happyShopping.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author coldwind
 * @create 2018/8/16
 * @since 1.0.0
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService {
    @Autowired
    private SolrTemplate solrTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 搜索
     *
     * @param searchMap
     * @return
     */
    @Override
    public Map<String, Object> search(Map searchMap) {
        //关键字空格处理  去除空格
        String keywords = (String) searchMap.get("keywords");
        if (keywords != null) {
            searchMap.put("keywords", keywords.replace(" ", ""));
        }

        Map<String, Object> map = new HashMap<>();
        //1.查询列表  将高亮的列表添加到map中
        //putAll可以合并两个MAP，只不过如果有相同的key那么用后面的覆盖前面的
        map.putAll(searchList(searchMap));

        //2.根据关键字查询商品分类
        List categoryList = searchCategoryList(searchMap);
        map.put("categoryList", categoryList);

        //得到传过来的categoryName
        String categoryName = (String) searchMap.get("category");
        //说明选择了分类
        if (!"".equals(categoryName)) {

            map.putAll(searchBrandAndSpecList(categoryName));
        } else {
            //3.查询品牌和规格列表
            if (categoryList != null && categoryList.size() > 0) {
                //暂时先查出第一个分类的品牌和对应的规格列表
                map.putAll(searchBrandAndSpecList((String) categoryList.get(0)));
            }
        }

        return map;
    }

    /**
     * solr保存SKU 的集合
     *
     * @param list
     * @return
     */
    @Override
    public void saveSKUList(List list) {
        solrTemplate.saveBeans(list);
        solrTemplate.commit();
    }

    /**
     * 根据商品id删除对应的SKU列表
     *
     * @param ids
     * @return
     */
    @Override
    public void deleteByGoodsIds(List ids) {
        System.out.println("删除的商品id:" + ids.toString());
        SimpleQuery query = new SimpleQuery();
        Criteria criteria = new Criteria("item_goodsid").in(ids);
        query.addCriteria(criteria);
        solrTemplate.delete(query);
        solrTemplate.commit();
    }

    /**
     * 根据关键字搜索列表
     *
     * @param searchMap
     * @return
     */
    private Map searchList(Map searchMap) {
        HashMap<String, Object> map = new HashMap<>();
        //显示高亮
        SimpleHighlightQuery query = new SimpleHighlightQuery();
        //设置高亮的域
        HighlightOptions options = new HighlightOptions().addField("item_title");
        //设置高亮前缀
        options.setSimplePrefix("<em style='color:red'>");
        //设置高亮后缀
        options.setSimplePostfix("</em>");
        //设置高亮选项
        query.setHighlightOptions(options);

        //添加查询条件  表示按关键字的Field域查询 --1.1关键字查询
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);

        //1.2按商品分类过滤
        if (!"".equals(searchMap.get("category"))) {
            SimpleFilterQuery filterQuery = new SimpleFilterQuery();
            Criteria filterCriteria = new Criteria("item_category").is(searchMap.get("category"));
            filterQuery.addCriteria(filterCriteria);
            query.addCriteria(filterCriteria);
        }
        //1.3按商品品牌过滤
        if (!"".equals(searchMap.get("brand"))) {
            SimpleFilterQuery filterQuery = new SimpleFilterQuery();
            Criteria filterCriteria = new Criteria("item_brand").is(searchMap.get("brand"));
            filterQuery.addCriteria(filterCriteria);
            query.addCriteria(filterCriteria);
        }
        //1.4按商品规格过滤
        if (searchMap.get("spec") != null && !searchMap.get("spec").equals("")) {
            Map<String, String> specMap = (Map<String, String>) searchMap.get("spec");
            for (String key : specMap.keySet()) {
                SimpleFilterQuery filterQuery = new SimpleFilterQuery();
                Criteria filterCriteria = new Criteria("item_spec_" + key).is(specMap.get(key));
                filterQuery.addCriteria(filterCriteria);
                query.addCriteria(filterCriteria);
            }
        }


        //1.5按照价格过滤
        if (!"".equals(searchMap.get("price"))) {
            String[] prices = ((String) searchMap.get("price")).split("-");
            //大于最低价格,如果最低价格不为0
            Criteria filterCriteria = new Criteria("item_price").greaterThanEqual(prices[0]);
            FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
            query.addFilterQuery(filterQuery);
            //如果最高价格不为*,小于最高价格
            if (!"*".equals(prices[1])) {
                Criteria criteria2 = new Criteria("item_price").lessThanEqual(prices[1]);
                SimpleFilterQuery filterQuery2 = new SimpleFilterQuery(criteria2);
                query.addFilterQuery(filterQuery2);
            }
        }

        //1.6分页查询
        Integer pageNum = (Integer) searchMap.get("pageNum");
        if (pageNum == null || "".equals(pageNum)) {
            pageNum = 1;
        }
        Integer pageSize = (Integer) searchMap.get("pageSize");
        if (pageSize == null || "".equals(pageSize)) {
            pageSize = 20;
        }
        query.setOffset((pageNum - 1) * pageSize);
        query.setRows(pageSize);

        //1.7按价格排序
        String sortValue = (String) searchMap.get("sort");
        String sortField = (String) searchMap.get("sortField");

        if (!"".equals(sortValue) && sortValue != null) {
            if (sortValue.equals("ASC")) {
                Sort sort = new Sort(Sort.Direction.ASC, "item_" + sortField);
                query.addSort(sort);
            }
            if (sortValue.equals("DESC")) {
                Sort sort = new Sort(Sort.Direction.DESC, "item_" + sortField);
                query.addSort(sort);
            }
        }


        /**********  获取高亮集合  ************/
        HighlightPage<Item> page = solrTemplate.queryForHighlightPage(query, Item.class);

        //循环高亮入口集合
        for (HighlightEntry<Item> itemHighlightEntry : page.getHighlighted()) {
            //获取原实体类
            Item entity = itemHighlightEntry.getEntity();
            if (itemHighlightEntry.getHighlights().size() > 0 && itemHighlightEntry.getHighlights().get(0).getSnipplets().size() > 0) {
                //设置高亮的结果
                entity.setTitle(itemHighlightEntry.getHighlights().get(0).getSnipplets().get(0));
            }
        }
        //page.getContent())为查询到的item,返回map和前端分页控件参数对应
        map.put("rows", page.getContent());
        //返回总页数
        map.put("totalPage", page.getTotalPages());
        //返回总记录数
        map.put("total", page.getTotalElements());
        return map;

    }

    /**
     * 查询分组列表
     *
     * @param searchMap
     * @return
     */
    private List searchCategoryList(Map searchMap) {
        List<String> list = new ArrayList<>();
        SimpleQuery query = new SimpleQuery();
        //添加查询条件  表示按关键字的Field域查询
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);
        //设置分组选项
        GroupOptions options = new GroupOptions().addGroupByField("item_category");
        query.setGroupOptions(options);
        //得到分组页
        GroupPage<Item> page = solrTemplate.queryForGroupPage(query, Item.class);
        //根据列获得分组结果集
        GroupResult<Item> groupResult = page.getGroupResult("item_category");
        //得到分组入口页
        Page<GroupEntry<Item>> entries = groupResult.getGroupEntries();
        //得到分组入口集合
        List<GroupEntry<Item>> content = entries.getContent();
        for (GroupEntry<Item> entry : content) {
            //将分组结果的名称封装到返回值中
            list.add(entry.getGroupValue());
        }
        return list;
    }

    /**
     * 查询品牌和规格列表
     *
     * @param categoryName
     * @return
     */
    private Map searchBrandAndSpecList(String categoryName) {


        Map<String, Object> map = new HashMap<>();
        Long typeId = (Long) redisTemplate.boundHashOps("itemCat").get(categoryName);
        if (typeId != null) {
            List<Map> brandList = (List<Map>) redisTemplate.boundHashOps("brandList").get(typeId);
            map.put("brandList", brandList);
            List<Map> specList = (List<Map>) redisTemplate.boundHashOps("specList").get(typeId);
            map.put("specList", specList);
        }
        return map;
    }
}
