/**
 * FileName: LoginNameController
 * Author:   coldwind
 * Date:     2018/8/12 15:16
 * Description: 登录控制
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.happyShopping.shop.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈登录控制〉
 *
 * @author coldwind
 * @create 2018/8/12
 * @since 1.0.0
 */
@RestController
@RequestMapping("/login")
public class LoginNameController {

    @RequestMapping("/name")
    public Map showLoginName(){
        HashMap<String, Object> map = new HashMap<>();
        //获取登录后的名字
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        map.put("loginName",name);
        return map;
    }

}
