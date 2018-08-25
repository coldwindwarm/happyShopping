/**
 * FileName: ItemPageService
 * Author:   coldwind
 * Date:     2018/8/19 16:39
 * Description: 生成静态页面
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.happyShopping.page.service;

/**
 * 〈一句话功能简述〉<br> 
 * 〈生成静态页面〉
 *
 * @author coldwind
 * @create 2018/8/19
 * @since 1.0.0
 */
public interface ItemPageService {

    /**
     * 根据商品id生成商品详细页静态页面
     * @param goodsId
     * @return
     */
    boolean generateItemPageHtml(Long goodsId);
    /**
     * 根据商品id删除商品详细页静态页面
     * @param goodsId
     * @return
     */
    boolean deleteGenerateItemPageHtml(Long[] goodsId);
}
