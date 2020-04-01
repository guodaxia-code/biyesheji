package xyz.worldzhile.controller;

import com.sun.org.apache.regexp.internal.RE;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import xyz.worldzhile.dao.CategoryDao;
import xyz.worldzhile.dao.OrderDao;
import xyz.worldzhile.dao.OrderItemDao;
import xyz.worldzhile.domain.Category;
import xyz.worldzhile.domain.Order;
import xyz.worldzhile.domain.ResultList;
import xyz.worldzhile.domain.User;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class TestController {

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    OrderDao orderDao;

    @GetMapping("test1")
    public ModelAndView test1(ModelAndView model){
        Subject currentUser = SecurityUtils.getSubject();
        boolean permitted=false;
        if (currentUser!=null) {
             permitted = SecurityUtils.getSubject().isPermitted("category:addcategoty");
            System.out.println(permitted);
        }
//        currentUser.getPreviousPrincipals()
        PrincipalCollection previousPrincipals = SecurityUtils.getSubject().getPreviousPrincipals();
        System.out.println(previousPrincipals+"-----");

        model.setViewName("test1");
    model.addObject("one","onezhi");
    model.addObject("permitted",permitted);


    List<User> list=new ArrayList<>();

        User user = new User();
         user.setName("王五二");

        User user1 = new User();
        user.setName("王五留");
        list.add(user);
                list.add(user1);
                model.addObject("list",list);




        List<Category> all = categoryDao.findAll();
        model.addObject("all",all);

        System.out.println(list);
        System.out.println("---------------------------");
        System.out.println(all);

        return model;
    }


    @GetMapping("test2")
    public ModelAndView test2(ModelAndView model){
        Subject currentUser = SecurityUtils.getSubject();
        boolean permitted=false;
        if (currentUser!=null) {
            permitted = SecurityUtils.getSubject().isPermitted("category:addcategoty");
            System.out.println(permitted);
        }
        model.setViewName("redirect:/templates/pages/test2.html");
        model.addObject("two","twozhi");
        model.addObject("permitted",permitted);
        return model;
    }



    @GetMapping("test3")
    @ResponseBody
    public ResultList test3(){
        ResultList resultList = new ResultList();

        ArrayList<String> names = new ArrayList<>();

        names.add("星期一");
        names.add("星期二");
        names.add("星期三");
        names.add("星期四");
        names.add("星期五");
        names.add("星期六");
        names.add("星期日");

        ArrayList<Integer> values = new ArrayList<>();

        values.add(5);
        values.add(15);
        values.add(35);
        values.add(55);
        values.add(25);
        values.add(8);
        values.add(3);

        resultList.setNames(names);
        resultList.setValues(values);


        return  resultList;
    }

    @RequestMapping("test4")
    public ModelAndView test4(ModelAndView modelAndView){
        modelAndView.setViewName("  indexpage");
        return  modelAndView;
    }


    public static void main(String[] args) {




        HashMap<Integer,String> source=new HashMap<>();
//        source.put(0,"星期日");
//        source.put(1,"星期一");
//        source.put(2,"星期二");
//        source.put(3,"星期三");
//        source.put(4,"星期四");
//        source.put(5,"星期五");
//        source.put(6,"星期六");

        source.put(0,"星期日");
        source.put(1,"星期一");
        source.put(2,"星期二");
        source.put(3,"星期三");
        source.put(4,"星期四");
        source.put(5,"星期五");
        source.put(6,"星期六");



//        WEDNESDAY  一 2 二 3  五 6 六 7   日 1


/*
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int ri = cal.get(Calendar.DATE);
        int shi = cal.get(Calendar.HOUR_OF_DAY);
        int fen = cal.get(Calendar.MINUTE);
        int miao = cal.get(Calendar.SECOND);
        int flag=cal.get(Calendar.DAY_OF_WEEK);
        System.out.println(source.get(flag));
*/


        Date cal = new Date();
        int flag=cal.getDay();
        System.out.println(source.get(flag));
        cal.setHours(0);
        cal.setMinutes(0);
        cal.setSeconds(0);
        System.out.println(cal);
         flag=cal.getDay();
        System.out.println(source.get(flag));

        for (int i = 0; i <50 ; i++) {
            cal.setDate(cal.getDate()-1);
            flag=cal.getDay();
            System.out.println(source.get(flag));
        }


//        int year1 = cal.get(Calendar.YEAR);
//        int month1 = cal.get(Calendar.MONTH);
//        int ri1 = cal.get(Calendar.DATE);
//        int shi1 = cal.get(Calendar.HOUR_OF_DAY);
//        int fen1 = cal.get(Calendar.MINUTE);
//        int miao1 = cal.get(Calendar.SECOND);


//         flag=cal.WEDNESDAY-1;
//        System.out.println(source.get(flag));



        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date(String.valueOf(cal.getTime()));
        System.out.println(date);
        String format1 = format.format(date);
        System.out.println(format1);


//        星期
//         flag=cal.WEDNESDAY-1;
//        System.out.println(source.get(flag));
    }

}
