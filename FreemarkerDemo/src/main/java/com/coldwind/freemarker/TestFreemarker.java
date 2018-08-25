/**
 * FileName: TestFreemarker
 * Author:   coldwind
 * Date:     2018/8/19 15:46
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.coldwind.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈Freemarker入门小Demo〉
 *
 * @author coldwind
 * @create 2018/8/19
 * @since 1.0.0
 */
public class TestFreemarker {
    public static void main(String[] args) throws IOException, TemplateException {
        //1.创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //2.设置模板所在目录
        configuration.setDirectoryForTemplateLoading(new File
                ("C:\\Users\\coldwind\\IdeaProjects\\happyShopping\\FreemarkerDemo\\src\\main\\resources"));
        //3.设置字符集
        configuration.setDefaultEncoding("utf-8");
        //4.加载模板
        Template template = configuration.getTemplate("test.ftl");
        //创建数据模型
        Map map = new HashMap();
        map.put("name", "coldwind");
        map.put("message", "欢迎来到Freemarker世界");
        map.put("success", true);


        List goodsList=new ArrayList();
        Map goods1=new HashMap();
        goods1.put("name", "苹果");
        goods1.put("price", 5.8);
        Map goods2=new HashMap();
        goods2.put("name", "香蕉");
        goods2.put("price", 2.5);
        Map goods3=new HashMap();
        goods3.put("name", "橘子");
        goods3.put("price", 3.2);
        goodsList.add(goods1);
        goodsList.add(goods2);
        goodsList.add(goods3);
        map.put("goodsList", goodsList);
        map.put("today",new Date());
        map.put("point", 1029201222);

        //6.创建writer对象
        FileWriter fileWriter = new FileWriter(new File("E:\\test.html"));
        //7.输出
        template.process(map, fileWriter);
        //8.关闭writer对象
        fileWriter.close();
    }
}
