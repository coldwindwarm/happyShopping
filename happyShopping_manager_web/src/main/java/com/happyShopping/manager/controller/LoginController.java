/**
 * FileName: LoginController
 * Author:   coldwind
 * Date:     2018/8/12 8:31
 * Description: 登录的控制层-显示当前用户的名字
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.happyShopping.manager.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈登录的控制层-显示当前用户的名字〉
 *
 * @author coldwind
 * @create 2018/8/12
 * @since 1.0.0
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    //获取登录后的名字
    @RequestMapping("/name")
    public Map name(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        HashMap<String, Object> map = new HashMap<>();
        map.put("loginName",name);
        return map;
    }
}
