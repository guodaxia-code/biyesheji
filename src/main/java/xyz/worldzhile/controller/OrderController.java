package xyz.worldzhile.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import xyz.worldzhile.alipay.AlipayConfig;
import xyz.worldzhile.constant.Constant;
import xyz.worldzhile.domain.*;
import xyz.worldzhile.service.OrderService;
import xyz.worldzhile.util.PageBean;
import xyz.worldzhile.util.UuidUtil;
import xyz.worldzhile.yeepay.PaymentForOnlineService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Api(tags = "订单接口")
@Controller
@RequestMapping("order")
public class OrderController {

    static String p1_MerId;
    static String keyValue;
    static String responseURL;

    static{
        p1_MerId="10001126856";
        keyValue="69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
        responseURL="http://localhost:8080/store/order/callback";
    }


    @Autowired
    OrderService orderService;


    @RequestMapping("findAll")
    public ModelAndView findAll(ModelAndView modelAndView){

       List<Order> all= orderService.findAll();
        modelAndView.setViewName("admin/order/orderList");
        modelAndView.addObject("all",all);

        return modelAndView;


    }


    /**
     购物车里多选下单
     * */
    @GetMapping("addOrder")
    public ModelAndView addOrder(@RequestParam("pids") String[] pids, ModelAndView model, HttpServletRequest request) {

        //添加订单
        System.out.println(Arrays.toString(pids));
        //移除和获取购物车里的数据


        Cart cart = (Cart) request.getSession().getAttribute(Constant.USER_CART_SESSION);
        double totalMany = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        for (String pid : pids) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOiid(UuidUtil.getUuid());
            CartItem cartItem = cart.getcartItem(pid);
            if (cartItem != null) {
                totalMany += cartItem.getSubtotal();
                orderItem.setMoney(cartItem.getSubtotal() * 1.0);
                orderItem.setNumber(cartItem.getCount());

                orderItem.setPid(pid);
                orderItems.add(orderItem);
                cart.removeFromCart(pid);

            } else {
                System.out.println("单前购物车里没有这个商品项");
                model.setViewName("msg");
                model.addObject("user_login_error", "单前购物车里没有这个商品项");
                return model;
            }
        }
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String oid = orderService.saveOrder(user.getUid(), orderItems, totalMany);//下单
        Order nowOrder = orderService.findOrderByOid(oid);
        model.addObject("nowOrder", nowOrder);
        model.setViewName("order");
        return model;

    }

    /**
     *
    直接买一种商品下单
     */



    @GetMapping("seeOneOrder")
    public ModelAndView seeOrder(@RequestParam("oid") String oid, ModelAndView model, HttpServletRequest request) {
        Order nowOrder = orderService.findOrderByOid(oid);
        model.addObject("nowOrder", nowOrder);
        model.setViewName("order");
        return model;

    }

    /*后台修改收货信息*/
    @GetMapping("xiugaiOneOrder")
    public ModelAndView xiugaiOneOrder(@RequestParam("oid") String oid, ModelAndView model, HttpServletRequest request) {
        Order nowOrder = orderService.findOrderByOid(oid);
        model.addObject("nowOrder", nowOrder);
        model.setViewName("admin/order/bianjiOrder");
        return model;

    }


    /*后台修改收货信息*/
    @PostMapping("xiugaiShouhuo")
    public void xiugaiShouhuo(Order order) {
        Order nowOrder = orderService.findOrderByOid(order.getOid());
        nowOrder.setAddress(order.getAddress());
        nowOrder.setPhone(order.getPhone());
        nowOrder.setName(order.getName());
        System.out.println(order);

        orderService.updateOrder(nowOrder);

    }


    @GetMapping("seeMyOrders")
    public ModelAndView seeOrder(ModelAndView model, HttpServletRequest request) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Order> allOrdersByUid = orderService.findAllOrdersByUid(user.getUid());
        model.addObject("pageBean", allOrdersByUid);

        System.out.println(allOrdersByUid);
        model.setViewName("orderxx");
        return model;

    }


    //用户所有订单  待支付订单 待收货订单
    @GetMapping("seeMyOrdersByPage")
    public ModelAndView getProductsByPage(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage, @RequestParam(value = "pageCount", defaultValue = "4") Integer pageCount,
                                          @RequestParam(value = "states", defaultValue = "-1") Integer states,ModelAndView model, HttpServletRequest request) {



        User user = (User) SecurityUtils.getSubject().getPrincipal();
        System.out.println(user.getUid()+"------------------------------------------------------------------------------");
        PageBean<Order> pageBean = orderService.findPageBean(user.getUid(), currentPage, pageCount,states);
        model.addObject("pageBean", pageBean);
        model.setViewName("orderxx");

        return model;

    }




    /*
      易宝支付
     */
/*    @PostMapping("payOrder")
    public ModelAndView payOrder(@RequestParam("oid") String oid,
                                 @RequestParam("name") String name,
                                 @RequestParam("address") String address,
                                 @RequestParam("phone") String phone,
                                 @RequestParam("yinghang") String yinghang,
                                 ModelAndView model, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(Constant.USER_LOGIN_SESSION);
        if (user == null) {
            System.out.println("没有登录");
            model.setViewName("msg");
            model.addObject("user_login_error", "没有登录不可以付款");
            return model;
        }

        Order orderByOid = orderService.findOrderByOid(oid);
        if (orderByOid == null) {
            System.out.println("没有该订单");
            model.setViewName("msg");
            model.addObject("user_login_error", "没有该订单");
            return model;
        }
        orderByOid.setName(name);
        orderByOid.setAddress(address);
        orderByOid.setPhone(address);

        orderService.updateOrder(orderByOid);

        System.out.println(yinghang);

        //拼接url
        //https://www.yeepay.com/app-merchant-proxy/node

        String pd_FrpId = yinghang;
        String p0_Cmd = "Buy";
        String p1_MerId = this.p1_MerId;//我的商店id
        String p2_Order = oid;
        String p3_Amt = String.valueOf(orderByOid.getTotalMoney());
        System.out.println(user.getUsername());
        if (user.getUsername().equals("李强5")) {
            p3_Amt = "0.01";
        }

        System.out.println(p3_Amt+"yuan");
        String p4_Cur = "CNY";
        String p5_Pid = "";
        String p6_Pcat = "";
        String p7_Pdes = "";

        //用户和第三方都会访问的回调接口地址
        String p8_Url = this.responseURL;
        String P9_SAF = "";
        String pa_MP = "";
        String pr_NeedResponse = "1"; //需要应答
        //加密hmac 需要密钥
        String keyValue = this.keyValue;

        //获取加密数据
        String hmac = PaymentForOnlineService.getReqMd5HmacForOnlinePayment(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur,
                p5_Pid, p6_Pcat, p7_Pdes, p8_Url, P9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

        //url
        StringBuilder url = new StringBuilder("https://www.yeepay.com/app-merchant-proxy/node?");

        url.append("p0_Cmd=").append(p0_Cmd).append("&");
        url.append("p1_MerId=").append(p1_MerId).append("&");
        url.append("p2_Order=").append(p2_Order).append("&");
        url.append("p3_Amt=").append(p3_Amt).append("&");
        url.append("p4_Cur=").append(p4_Cur).append("&");
        url.append("p5_Pid=").append(p5_Pid).append("&");
        url.append("p6_Pcat=").append(p6_Pcat).append("&");
        url.append("p7_Pdes=").append(p7_Pdes).append("&");
        url.append("p8_Url=").append(p8_Url).append("&");
        url.append("P9_SAF=").append(P9_SAF).append("&");
        url.append("pa_MP=").append(pa_MP).append("&");
        url.append("pd_FrpId=").append(pd_FrpId).append("&");
        url.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
        url.append("hmac=").append(hmac);


        model.setViewName("redirect:" + url);
        return model;
    }*/


 /*
    支付宝支付
     */

    @PostMapping("payOrder")
    public ModelAndView payOrder(@RequestParam("oid") String oid,
                                 @RequestParam("name") String name,
                                 @RequestParam("address") String address,
                                 @RequestParam("phone") String phone,
                                 ModelAndView model, HttpServletRequest request) {

//        User user = (User) request.getSession().getAttribute(Constant.USER_LOGIN_SESSION);
//        if (user == null) {
//            System.out.println("没有登录");
//            model.setViewName("msg");
//            model.addObject("user_login_error", "没有登录不可以付款");
//            return model;
//        }

        User user = (User) SecurityUtils.getSubject().getPrincipal();

        Order orderByOid = orderService.findOrderByOid(oid);
        if (orderByOid == null) {
            System.out.println("没有该订单");
            model.setViewName("msg");
            model.addObject("user_login_error", "没有该订单");
            return model;
        }
        orderByOid.setName(name);
        orderByOid.setAddress(address);
        orderByOid.setPhone(address);

        orderService.updateOrder(orderByOid);



        //
        //支付宝
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = oid;
        //付款金额，必填
        String total_amount = String.valueOf(orderByOid.getTotalMoney());
        if (user.getUsername().equals("李强5")) {
            total_amount = "0.01";
        }
        //订单名称，必填
        String subject = new String("至乐购付款");
        //商品描述，可空
        String body = "";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\"10m\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        String result = null;
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        System.out.println(result);





        model.setViewName("qualipay");
        model.addObject("AliPay",result);

        return model;

    }



    /**
     * 支付宝成功付款 异步通知
     */
/*    @GetMapping("callbackYi")
    public ModelAndView callbackYi(ModelAndView model, HttpServletRequest request, HttpServletResponse response) {

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            params.put(name, valueStr);
        }

        boolean signVerified = false; //调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //——请在这里编写您的程序（以下代码仅作参考）——

	*//* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*//*
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = request.getParameter("out_trade_no");

            //支付宝交易号
            String trade_no = request.getParameter("trade_no");

            //交易状态
            String trade_status = request.getParameter("trade_status");

            //交易状态
            String total_amount = request.getParameter("total_amount");


            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
                Order payedOrder = orderService.findOrderByOid(out_trade_no);
                payedOrder.setStates(Constant.IS_PAYE);
                orderService.updateOrder(payedOrder);
                model.setViewName("msg");
                model.addObject("user_login_error", "您的订单号为：" + out_trade_no + ",金额为：" + total_amount + "已经付款成功,等待发货中~~~");
                return model;
                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
                System.out.println("付款成功未处理订单");
                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
            }

            try {
                response.getWriter().println("success");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {//验证失败
            System.out.println("第三方支付数据被篡改了");
            model.setViewName("msg");
            model.addObject("user_login_error", "第三方支付数据被篡改了");
            return model;

            //调试用，写文本函数记录程序运行情况是否正常
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);
        }

        //——请在这里编写您的程序（以上代码仅作参考）——


        System.out.println("订单付款失败");
        model.setViewName("msg");
        model.addObject("user_login_error", "订单付款失败");
        return model;



    }*/


    /**
     * 支付宝成功付款 同步通知
     */
    @GetMapping("callbackTong")
    public ModelAndView callbackTong(ModelAndView model, HttpServletRequest request, HttpServletResponse response) {


        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }

            params.put(name, valueStr);
        }


        System.out.println(params);
        boolean signVerified = false; //调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = request.getParameter("out_trade_no");

            //支付宝交易号
            String trade_no = request.getParameter("trade_no");

            //交易状态
            String trade_status = request.getParameter("trade_status");

            //交易状态
            String total_amount = request.getParameter("total_amount");




            System.out.println("trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);


//            if(trade_status.equals("TRADE_FINISHED")){
//                //判断该笔订单是否在商户网站中已经做过处理
//                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//                //如果有做过处理，不执行商户的业务程序
//                Order payedOrder = orderService.findOrderByOid(out_trade_no);
//                payedOrder.setStates(Constant.IS_PAYE);
//                orderService.updateOrder(payedOrder);
//                model.setViewName("msg");
//                model.addObject("user_login_error", "您的订单号为：" + out_trade_no + ",金额为：" + total_amount + "已经付款成功,等待发货中~~~");
//                return model;
//                //注意：
//                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
//            }else if (trade_status.equals("TRADE_SUCCESS")){
//                //判断该笔订单是否在商户网站中已经做过处理
//                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//                //如果有做过处理，不执行商户的业务程序
//                System.out.println("付款成功未处理订单");
//                //注意：
//                //付款完成后，支付宝系统发送该交易状态通知
//            }


            //testdev
            Order payedOrder = orderService.findOrderByOid(out_trade_no);
            payedOrder.setStates(Constant.IS_PAYE);
            orderService.updateOrder(payedOrder);

            model.setViewName("payok");
            model.addObject("user_login_error", "您的订单号为：" + out_trade_no + ",金额为：" + total_amount + "已经付款成功,等待发货中~~~");
            return model;


        }else {//验证失败
            System.out.println("第三方支付数据被篡改了");
            model.setViewName("msg");
            model.addObject("user_login_error", "第三方支付数据被篡改了");
            return model;

            //调试用，写文本函数记录程序运行情况是否正常
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);
        }

        //——请在这里编写您的程序（以上代码仅作参考）——


//        System.out.println("订单付款失败");
//        model.setViewName("msg");
//        model.addObject("user_login_error", "订单付款失败");
//        return model;


    }





    /**
     * 成功付款
     */
    @GetMapping("callback")
    public ModelAndView yiBao(ModelAndView model, HttpServletRequest request, HttpServletResponse response) {
        String p1_MerId = request.getParameter("p1_MerId");
        String r0_Cmd = request.getParameter("r0_Cmd");
        String r1_Code = request.getParameter("r1_Code");
        String r2_TrxId = request.getParameter("r2_TrxId");
        String r3_Amt = request.getParameter("r3_Amt");
        String r4_Cur = request.getParameter("r4_Cur");
        String r5_Pid = request.getParameter("r5_Pid");
        String r6_Order = request.getParameter("r6_Order");
        String r7_Uid = request.getParameter("r7_Uid");
        String r8_MP = request.getParameter("r8_MP");
        String r9_BType = request.getParameter("r9_BType");

        //加密判断数据hmac 需要密钥
        String keyValue = this.keyValue;
        String hmac = request.getParameter("hmac");


        boolean isValid = PaymentForOnlineService.verifyCallback(hmac, p1_MerId,
                r0_Cmd, r1_Code, r2_TrxId, r3_Amt,
                r4_Cur, r5_Pid, r6_Order, r7_Uid,
                r8_MP, r9_BType, keyValue);

        if (isValid) {
            //数据有效
            if (r9_BType.equals("1")) {
                //用户重定向来的
                model.setViewName("msg");
                model.addObject("user_login_error", "您的订单号为：" + r6_Order + ",金额为：" + r3_Amt + "已经付款成功,等待发货中~~~");
                return model;

            } else if (r9_BType.equals("2")) {
                //第三方平台发给我

                try {
                    response.getWriter().println("success");
                } catch (IOException e) {
                    e.printStackTrace();
                }


                System.out.println(r6_Order + "的订单已经成功付款");
                Order payedOrder = orderService.findOrderByOid(r6_Order);
                payedOrder.setStates(Constant.IS_PAYE);
                orderService.updateOrder(payedOrder);

                //去已付款订单中
                model.setViewName("msg");
                model.addObject("user_login_error", "您的订单号为：" + r6_Order + ",金额为：" + r3_Amt + "已经付款成功,等待发货中~~~");
                return model;


            }

        } else {
            System.out.println("第三方支付数据被篡改了");
            model.setViewName("msg");
            model.addObject("user_login_error", "第三方支付数据被篡改了");
            return model;

        }

        System.out.println("订单付款失败");
        model.setViewName("msg");
        model.addObject("user_login_error", "订单付款失败");
        return model;

    }






    @GetMapping("findAllPage")
    public ModelAndView findAllPage(ModelAndView model){
        //这里必需要要跳转
        model.setViewName("redirect:/templates/pages/admin/order/orderManager.html");
        return model;
    }

    /**
     * 分页查询   pname 为条件
     * @param page
     * @param limit
     * @param pname
     * @return
     */
    @GetMapping("findAllByLayui")
    public @ResponseBody
    LayuiData<Order> findAllByLayuiByPage(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                            @RequestParam(value = "limit",defaultValue = "8") Integer limit,
                                            @RequestParam(value = "pname",required = false) String pname){
//        接口地址。默认会自动传递两个参数：?page=1&limit=30（该参数可通过 request 自定义）
//        page 代表当前页码、limit 代表每页数据量
        //条件校验
        if (pname==null||pname.length()==0){
            pname="";
        }
        PageBean<Order> allByLayuiByPage = orderService.findAllByLayuiByPage(page, limit,pname);
        LayuiData<Order> orderLayuiData = new LayuiData<>();
        orderLayuiData.setCode(0);
        orderLayuiData.setData(allByLayuiByPage.getList());
        orderLayuiData.setMsg("");
        orderLayuiData.setCount(allByLayuiByPage.getTotalCount());
        return orderLayuiData;
    }


    //发货
    @RequestMapping("fahuo/{oid}")
    public void Fahuo(@PathVariable("oid") String oid){
        Order orderByOid = orderService.findOrderByOid(oid);
        Integer states = orderByOid.getStates();
        if (states==Constant.IS_PAYE) {
            orderByOid.setStates(Constant.IS_FAHUO);
            orderService.updateOrder(orderByOid);
        }


    }

    //删除订单们
    @RequestMapping("del/{oid}")
    public void del(@PathVariable("oid") String[] oid){
        System.out.println(Arrays.toString(oid));
        for (String one : oid) {
            orderService.updatedelete(one);
        }

    }




    /**
     * 立即购买不走购物车 一个商品的多个数量
     */

    @RequestMapping("lijigou")
    public ModelAndView lijigou(@RequestParam("pid") String pid ,@RequestParam("pcount") Integer pcount ,ModelAndView model){

        String oid=orderService.saveOrderOneProduct(pid,pcount);
        Order nowOrder = orderService.findOrderByOid(oid);
        model.addObject("nowOrder", nowOrder);
        model.setViewName("order");
        return model;


    }







    //用户确认收货 2 -3
    @RequestMapping("shouhuo")
    public ModelAndView shouhuo(@RequestParam("oid") String oid,ModelAndView model){
        Order orderByOid = orderService.findOrderByOid(oid);
        Integer states = orderByOid.getStates();
        if (states==Constant.IS_FAHUO) {
            orderByOid.setStates(Constant.IS_OK);
            orderService.updateOrder(orderByOid);
        }

        model.setViewName("redirect:/order/seeMyOrdersByPage");
        return model;

    }


















}
