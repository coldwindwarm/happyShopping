/**
 * FileName: Test
 * Author:   coldwind
 * Date:     2018/8/13 19:46
 * Description: 测试FastDFS
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.coldwind.demo;

import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
import org.csource.fastdfs.*;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 〈一句话功能简述〉<br>
 * 〈测试FastDFS〉
 *
 * @author coldwind
 * @create 2018/8/13
 * @since 1.0.0
 */
public class Test {
    public  static void main(String[] args) throws FileNotFoundException, IOException, Exception {
        // 1.加载配置文件
        ClientGlobal.init("C:\\Users\\coldwind\\IdeaProjects\\happyShopping\\FastDFSDemo\\src\\main\\resources\\fdfs_client.conf");
        // 2.构建一个管理者客户端
        TrackerClient client=new TrackerClient();
        // 3.连接管理者服务端
        TrackerServer trackerServer = client.getConnection();
        //4. 声明存储服务端
        StorageServer storageServer=null;
        //5. 获取存储服务器的客户端对象
        StorageClient storageClient=new StorageClient(trackerServer, storageServer);
        //6.上传文件
        String[] strings = storageClient.upload_file("E:\\pic\\48b4f6db-4806-4817-b1aa-f27a090063d0.JPEG", "JPEG", null);
        //7.显示上传结果 file_id
        for(String str:strings){
            System.out.println(str);
        }

    }
}
