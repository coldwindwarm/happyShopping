/**
 * FileName: contentController
 * Author:   coldwind
 * Date:     2018/8/16 11:18
 * Description: 广告控制
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.happyShopping.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.happyShopping.content.service.ContentService;
import com.happyShopping.model.Content;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈广告控制〉
 *
 * @author coldwind
 * @create 2018/8/16
 * @since 1.0.0
 */
@RestController
@RequestMapping("/content")
public class contentController {
    @Reference
    private ContentService contentService;

    /**
     * 通过广告分类的id得到广告
     * @param categoryId
     * @return
     */
    @RequestMapping("/findByCatergoryId")
    public List<Content> findByCatergoryId(Long categoryId) {
        return contentService.findByCatergoryId(categoryId);
    }
}
