/**
 * FileName: SmsListener
 * Author:   coldwind
 * Date:     2018/8/25 19:42
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.coldwind.sms;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author coldwind
 * @create 2018/8/25
 * @since 1.0.0
 */
public class SmsListener {
    @Autowired
    private SmsUtil smsUtil;

    public void sendSms(Map<String,String> map){
        try {
            SendSmsResponse response = smsUtil.sendSms(
                    map.get("mobile"),
                    map.get("template_code"),
                    map.get("sign_name"),
                    map.get("param"));
            System.out.println("Code=" + response.getCode());
            System.out.println("Message=" + response.getMessage());
            System.out.println("RequestId=" + response.getRequestId());
            System.out.println("BizId=" + response.getBizId());
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }
}
