/**
 * FileName: HelloWorldController
 * Author:   coldwind
 * Date:     2018/8/25 16:45
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.coldwind.demo.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author coldwind
 * @create 2018/8/25
 * @since 1.0.0
 */
@RestController
public class HelloWorldController {
    @Autowired
    private Environment env;

    @RequestMapping("/info")
    public String info() {
//        env.getProperty获取配置文件application.properties的信息
        return "HelloWorld--SpringBoot"+env.getProperty("url");
    }
}
