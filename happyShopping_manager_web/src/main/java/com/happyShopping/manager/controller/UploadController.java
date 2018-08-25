/**
 * FileName: UploadController
 * Author:   coldwind
 * Date:     2018/8/13 20:12
 * Description: 上传图片的控制层
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.happyShopping.manager.controller;

import com.happyShopping.util.FastDFSClient;
import entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 〈一句话功能简述〉<br> 
 * 〈上传图片的控制层〉
 *
 * @author coldwind
 * @create 2018/8/13
 * @since 1.0.0
 */
@RestController
public class UploadController {
    @Value("${FILE_SERVER_URL}")
    //文件服务器地址
    private String FILE_SERVER_URL;

    @RequestMapping("/upload")
    public Result upload(MultipartFile file)  {
        try {
            //取文件的扩展名,不需要.
            String filename = file.getOriginalFilename();
            String name = filename.substring(filename.lastIndexOf(".") + 1);
            //创建一个FastFDS客户端
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fdfs_client.conf");
            //执行上传处理
            String path = fastDFSClient.uploadFile(file.getBytes(), name);
            //拼接返回的 url 和 ip 地址，拼装成完整的 url
            String url = FILE_SERVER_URL + path;
            System.out.println(url);
            return new Result(true,""+url);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传失败");
        }
    }
}
