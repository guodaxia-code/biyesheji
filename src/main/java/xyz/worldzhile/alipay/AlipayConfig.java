package xyz.worldzhile.alipay;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
//    public static String app_id = "2021001145639121";
    public static String app_id = "2016101900721920";  //test
    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDNz5jEmD796+iNP8gATrPUyrqqr/uOFmnAai4M2+lm4M1vg6cM1HAnxa+Kj10EApIlAZCgaag9oV+gXjtwex7acMYg21fdvFwvJMhTjXP2CUdwwO4bdBrraBMphhgyrfj1vybf5bUQsuY4+tEfdURn46c2Z/u4SEDp0NOBBx5yreVO6c9fiZXFyCywwBqnbXHu3fVUHHR+QS99XWZMIVqfZxFSMYNx9CKdgHoqvB3lIxsnaWRLa5U/6OtNcXUDN3rAdE1rvrFrg/TFVbpI9+l0NHPV8K8WEi2ZfDCcfsTLwCqSx+jvAWWWrjbQia9RGz3GFBSSUsrlMq0oAV0pAcGZAgMBAAECggEBAII7QNH1/LY0lxz6JRZ8trKJ7gCgBy/r4tMM3ULpzRF6YsSTrBskOiwQnXmyKguljNC54sh+0yTcfmQ9/KK6ZdWX0udrpHf8/uAeCF1GfwBK8tfYXVMl7vrTCn0WCy3WhFkMsg6vjhmY+rFHlRiw3a5XkbGNruz3W4wYxh05YfzyBh5R+FgwFm5L1OaNR+f5oM637Pi6x2zP2JwUqZ0wImcFu0vxqPGQLinhQlfVDZypzrty9/WvJJX54Xr/2N6Q9yProXcjWl4Ru0LGI0EHrRZxsuGlRBsb3kE6XRwJLR/f8LMr4Fe2UNrXJMcFfgMlBeSu4tEnx1ASDjFQnAcAbzkCgYEA7gki+TA9Nhcao1T0B0OartqIPn7Qh9aNNCv1Qowk0YBgkR043qhNuf1SHe1DQC/qRt0ZRoVmSJ9nDT5Uc7FGyH+MnSrp+wH2wHJLCgqhG2aWMrcuVP7Fl+XTu+plrongL+ENNs9/PLcAsbURlFPnGzOjTXI0Sppzf6LakIwtHxMCgYEA3VfgNEMsVNPc/P3Nyf71BKLRku0aC2Zb1TfEoKIoz9iKKNWXI8L2uQ+yOsATItxzHqgPYEdj+AkmxIJYYRNCWztFNx6D+7pVKAyPgm5/BBeTpzDboFOfixbuPCKLzSYHChIjRZCYo4O13k7gnDnXl+NdlRxGmVTGnvlTNn8ltiMCgYBe3NlJQcZDNjXxfbu64WBV6tV9QoDMgd7IOfSGTt+ldmoFqwWtBpnjU2dUcefpPq85Cog2KJe4vX4OMxbo/G9fHCXnxOB2njO+Qk5RdCb8ioe1MneOe/I3NOqK60EWq+/iZOglPdBRXAU0zRpyaHWjy5J7HNZmHOOGrFo6XQLmbQKBgFv/FTHDpZ5QPG5ucuT88guOPLrrj0KjtZGGT1RQcdQJdUsJk3sKoo9CZdqjUJDRvYtFYLaslXFXJGTSpDcc0RyoItmI9mRfCtvNdKxKjsI9pmH8HZyihRJDYptd5GtM3eoipcRZGAgGS0Xt1M85/1Ci8Jd0HCfIo5EZoatp51uvAoGBALw14tdyJiQ0vO7a4YZKScl1QBU9Z36DuLuLuFf+jkbM4IqpXEeNbxuj4Jqi7P3SQbPs3XbZG7RyCJK6N3R0oWSdtYgyfsD5gd8ZXYsnkCIainavrFiVdPsbnfA2Q1v0tsxwHVveBEn6Fe+yViZFqEQ6jHfdci4aZ2YRLswbLZbb";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
//    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnbGGGEIenS/w7J+E1iVfrktBt/Sec9SwrO6cxiIZUy1y2PkpyFVrUhYHzCRosDovLDB/kFSQSAb/dpxfSDSXwgrrDRTn5waZGTkmUnivJBfADOKRzmk7NQ0uMc75PAy7LnPxcn33UIjaDyBN2rRoKf2Q5GP1BG7vzj+t3/VlRWJ0iUmYfqpFhaZ2AMXhcPQwvINsjPreyqnN5jU/h2pkCjD5snhZHSMVcRkeGQ6MdCA+jLYc6QHD78Il4d9yCszZPGC3/MYJ6NgJ1psaYJXWYcZiZ9N8go11EtkQj3qrR4oUGUNe/H7QsnkEBTS3Md5QAsF1d1aaRS4Ybjfj+gJypQIDAQAB";
    //TEST
    public static String alipay_public_key ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiokp8WSCuecxch53l7+58DXSaUpRbpE/EeR8BdqSLYnyE4pAqL4qV+vUprilxh8soozkkRoZwx6WJW7wRpqe0m3nD2wSosZdttD1pAdHAwJTTVMaQzD10xzC0JySDK9tc28SRTry+7fF7lQcwzfwvX9b+S0/coBGAb9xDGB1ChQya6+oENnqVnTt/Hye++ABAjhRSmAbFgfiaqGkZucoooI4rmL2CrR+LetyBafr+LVB3FZXVKvld3pHmKjfvUJ4is+MyqYCPcbuI14d778JpNzeOTKutSxf/8AxPWzxcEfGdoy301trsIFdoounlOT44XQoB+R3QCX+60Gmr1cPUQIDAQAB";




    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8080/store/order/callbackYi";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:8080/store/order/callbackTong";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

