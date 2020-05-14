package xyz.worldzhile.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 发邮件工具类
 */
public final class MailUtils {
    private static final String USER = "1154424892@qq.com"; // 发件人称号，同邮箱地址
    //auth gnszextzyltffich
    private static final String PASSWORD = "gnszextzyltffich"; // 如果是qq邮箱可以使户端授权码，或者登录密码
    private static  final String SENDERNICKNAME="至乐购购物商城";// 发件人昵称

    /**
     *
     * @param to 收件人邮箱
     * @param text 邮件正文
     * @param title 标题
     */
    /* 发送验证信息的邮件 */
    public static boolean sendMail(String to, String text, String title){
        try {
            final Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.qq.com");

            // 发件人的账号
            props.put("mail.user", USER);
            //发件人的密码
            props.put("mail.password", PASSWORD);

            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            String username = props.getProperty("mail.user");
            InternetAddress form = new InternetAddress(username,SENDERNICKNAME);
            message.setFrom(form);

            // 设置收件人
            InternetAddress toAddress = new InternetAddress(to);
            message.setRecipient(Message.RecipientType.TO, toAddress);

            // 设置邮件标题
            message.setSubject(title);

            // 设置邮件的内容体
            message.setContent(text, "text/html;charset=UTF-8");
            // 发送邮件
            Transport.send(message);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) throws Exception { // 做测试用
        String code="8064d5b715e14081a8421af58037e154";
        //"<h1>来自XXX网站的激活邮件,激活请点击以下链接：</h1><br/><h3><a href='http://www/JavaMail/ActiveServlet?code="+code+"'>http://localhost:8080/JavaMail/ActiveServlet?code="+code+"</a></h3>"

//        MailUtils.sendMail("2561587813@qq.com","<h1>来自XXX网站的激活邮件,激活请点击以下链接：</h1><br/><h3><a href='http://localhost:8080/store/user/active?code="+code+"'>激活</a></h3>","测试邮件");
        MailUtils.sendMail("2561587813@qq.com","<h1>欢迎您成为至乐购商城的一员</h1><h2>来自至乐购商城网站的激活邮件,激活请点击以下链接：</h2><br/><h3><a href='http://localhost:8080/store/user/active?code="+code+"'>激活</a></h3>","至乐购用户注册");
        System.out.println("发送成功");
    }



}
