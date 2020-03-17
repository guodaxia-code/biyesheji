package xyz.worldzhile.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.worldzhile.dao.OrderDao;
import xyz.worldzhile.dao.OrderItemDao;
import xyz.worldzhile.domain.Order;
import xyz.worldzhile.domain.OrderItem;
import xyz.worldzhile.util.UuidUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Controller
public class TestController {

    @Autowired
    OrderItemDao orderItemDao;


    @Autowired
    OrderDao orderDao;

    @GetMapping("test1")
    public String test1(){


        return "test1";
    }

    @GetMapping("test2")
    @ResponseBody
    public Order test2(){

        Order cee174de368149c0b404f9362bfb8b3d = orderDao.findOneByOid("cee174de368149c0b404f9362bfb8b3d");

        return cee174de368149c0b404f9362bfb8b3d;
    }


    @GetMapping("test3")
    @ResponseBody
    public List<Order> test3(){

        List<Order> allOrdersByUid = orderDao.findAllOrdersByUid("12345");
        System.out.println(allOrdersByUid);
        return allOrdersByUid;
    }


}
