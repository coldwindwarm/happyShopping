/**
 * FileName: ItemSearchService
 * Author:   coldwind
 * Date:     2018/8/16 21:14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.happyShopping.search.service;

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
public interface ItemSearchService {
    /**
     * 搜索
     * @param searchMap
     * @return
     */
    public Map<String,Object> search(Map searchMap);
    /**
     * solr保存SKU 的集合
     * @param list
     * @return
     */
    public void saveSKUList(List list);
    /**
     * 根据商品id删除对应的SKU列表
     * @param ids
     * @return
     */
    public void deleteByGoodsIds(List ids);
}
