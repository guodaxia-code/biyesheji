package xyz.worldzhile.test;

import org.springframework.core.io.Resource;
import xyz.worldzhile.constant.Constant;
import xyz.worldzhile.domain.Product;
import xyz.worldzhile.util.PageBean;
import xyz.worldzhile.util.UuidUtil;
import xyz.worldzhile.yeepay.PaymentForOnlineService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class testSql {
    public static void main(String[] args) throws IOException {

//        URL url = testSql.class.getClassLoader().getResource("classpath:merchantInfo.properties");
//        System.out.println(url);
//        Properties pro = new Properties();
//        FileInputStream in = new FileInputStream(url.getPath());
//        pro.load(in);
//        String name = pro.get("p1_MerId").toString();
//        System.out.println(name);


        String reqMd5HmacForOnlinePayment = PaymentForOnlineService.getReqMd5HmacForOnlinePayment("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
        System.out.println(reqMd5HmacForOnlinePayment);


//        String pd_FrpId = "yinghang";
//        String p0_Cmd = "Buy";
//        String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");//我的商店id
//        String p2_Order = "oid";
//        String p3_Amt = String.valueOf(0.1);
//
//        String p4_Cur = "CNY";
//        String p5_Pid = "";
//        String p6_Pcat = "";
//        String p7_Pdes = "";
//
//        //用户和第三方都会访问的回调接口地址
//        String p8_Url = ResourceBundle.getBundle("merchantInfo.properties").getString("responseURL");
//        String P9_SAF = "";
//        String pa_MP = "";
//        String pr_NeedResponse = "1"; //需要应答
//        //加密hmac 需要密钥
//        String keyValue = ResourceBundle.getBundle("merchantInfo.properties").getString("keyValue");
//        StringBuilder url1 = new StringBuilder("https://www.yeepay.com/app-merchant-proxy/node?");
//
//        url1.append("p0_Cmd=").append(p0_Cmd).append("&");
//        url1.append("p1_MerId=").append(p1_MerId).append("&");
//        url1.append("p2_Order=").append(p2_Order).append("&");
//        url1.append("p3_Amt=").append(p3_Amt).append("&");
//        url1.append("p4_Cur=").append(p4_Cur).append("&");
//        url1.append("p5_Pid=").append(p5_Pid).append("&");
//        url1.append("p6_Pcat=").append(p6_Pcat).append("&");
//        url1.append("p7_Pdes=").append(p7_Pdes).append("&");
//        url1.append("p8_Url=").append(p8_Url).append("&");
//        url1.append("P9_SAF=").append(P9_SAF).append("&");
//        url1.append("pa_MP=").append(pa_MP).append("&");
//        url1.append("pd_FrpId=").append(pd_FrpId).append("&");
//        url1.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
//
//        System.out.println(url1);
//
//        //获取加密数据
//        String hmac = PaymentForOnlineService.getReqMd5HmacForOnlinePayment(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur,
//                p5_Pid, p6_Pcat, p7_Pdes, p8_Url, P9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
//
//        //url
//        StringBuilder url = new StringBuilder("https://www.yeepay.com/app-merchant-proxy/node?");
//
//        url.append("p0_Cmd=").append(p0_Cmd).append("&");
//        url.append("p1_MerId=").append(p1_MerId).append("&");
//        url.append("p2_Order=").append(p2_Order).append("&");
//        url.append("p3_Amt=").append(p3_Amt).append("&");
//        url.append("p4_Cur=").append(p4_Cur).append("&");
//        url.append("p5_Pid=").append(p5_Pid).append("&");
//        url.append("p6_Pcat=").append(p6_Pcat).append("&");
//        url.append("p7_Pdes=").append(p7_Pdes).append("&");
//        url.append("p8_Url=").append(p8_Url).append("&");
//        url.append("P9_SAF=").append(P9_SAF).append("&");
//        url.append("pa_MP=").append(pa_MP).append("&");
//        url.append("pd_FrpId=").append(pd_FrpId).append("&");
//        url.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
//        url.append("hmac=").append(hmac);
//
//        System.out.println(url);


    }
}
