package xyz.worldzhile.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import xyz.worldzhile.constant.Constant;
import xyz.worldzhile.domain.Cart;
import xyz.worldzhile.domain.User;
import xyz.worldzhile.util.UuidUtil;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

@Api(tags = "Common接口")
@Controller
public class CommonController {

    @ApiOperation(value = "获取验证码")
    @GetMapping("/checkCode")
    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //在内存中创建一个长80，宽30的图片，默认黑色背景
        //参数一：长
        //参数二：宽
        //参数三：颜色
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        //获取画笔
        Graphics g = image.getGraphics();
        //设置画笔颜色为灰色
        g.setColor(Color.GRAY);
        //填充图片
        g.fillRect(0,0, width,height);

        //产生4个随机验证码，12Ey
        String checkCode = UuidUtil.getCheckCode();
        //将验证码放入HttpSession中
        request.getSession().setAttribute(Constant.CHECKCODE_SESSION,checkCode);

        //设置画笔颜色为黄色
        g.setColor(Color.YELLOW);
        //设置字体的小大
        g.setFont(new Font("黑体",Font.BOLD,24));
        //向图片上写入验证码
        g.drawString(checkCode,15,25);

        //将内存中的图片输出到浏览器
        //参数一：图片对象
        //参数二：图片的格式，如PNG,JPG,GIF
        //参数三：图片输出到哪里去
        ImageIO.write(image,"PNG",response.getOutputStream());
    }


    @GetMapping("/msg")
    public ModelAndView msg(ModelAndView model){
        model.setViewName("msg");
        return model;
    }

    @ApiOperation(value = "获取头部页面")
    /*测试head页面*/
    @GetMapping("/head")
    public ModelAndView head(ModelAndView model,HttpServletRequest request){

        int cartSubCount=0;
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user!=null){
            Cart cart = (Cart) SecurityUtils.getSubject().getSession().getAttribute(Constant.USER_CART_SESSION);
             cartSubCount = cart.getCartSubCount();
        }
        request.setAttribute("user",user);
        request.setAttribute("cartSubCount",cartSubCount);
        System.out.println(cartSubCount);
        model.setViewName("head");
        return model;
    }


    @ApiOperation(value = "获取尾部页面")
    /*测试footer页面*/
    @GetMapping("/footer")
    public ModelAndView footer(ModelAndView model){
        model.setViewName("footer");
        return model;
    }

}
