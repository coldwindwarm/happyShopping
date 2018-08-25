/**
 * FileName: ItemSearchController
 * Author:   coldwind
 * Date:     2018/8/17 8:19
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.happyShopping.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.happyShopping.search.service.ItemSearchService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author coldwind
 * @create 2018/8/17
 * @since 1.0.0
 */
@RestController
@RequestMapping("/itemsearch")
public class ItemSearchController {
    @Reference
    private ItemSearchService itemSearchService;

    @RequestMapping("/search")
    public Map<String, Object> search(@RequestBody Map searchMap) {
        return itemSearchService.search(searchMap);
    }
}
