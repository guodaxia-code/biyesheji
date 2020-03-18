package xyz.worldzhile.constant;

public interface Constant {
    String CHECKCODE_SESSION="checkcode_session"; //存在session里的验证码
    String USER_MESSAGEG_ERROR="user_login_error"; //用户操作的错误信息  thymeleaf里怎么写固定的字符串

    Integer USER_NO_ACTIVE=0; //用户未激活0 已激活为1

    String TITLE="titile"; //页面标题
    String USER_MESSAGEG_SUCCESS="user_messageg_success"; //用户操作的成功信息

    String USER_LOGIN_SESSION="user_login_session"; //登录在session里的用户

    String SAVE_USERNAME="yes"; //cookie记住用户名


    /*
       redis里存的分类数据
     */
    String STORE_CATEGORY_LIST="STORE_CATEGORY_LIST";



    /*
      是热门商品
     */
    Integer IS_HOT_PRODUCT=1;

    /*
     不是热门商品
     */
    Integer NOT_HOT_PRODUCT=0;

    /*
     商品已经上架
     */
    Integer IS_UP_TO_SELL=1;

    /*
     商品删除下架
     */
    Integer IS_DOWN_TO_DELETE=0;


    /**
     * 首页上的热门商品9个
     */
    Integer INDEX_HOT_PRODUCT_NUMBER=9;

    /**
     * 首页上的最新商品9个
     */
    Integer INDEX_New_PRODUCT_NUMBER=9;



   String USER_CART_SESSION="user_cart_list";

    /**
     * 未付款
     */
   Integer NO_PAY=0;

    /**
     * 已付款
     */
    Integer IS_PAYE=1;



}
