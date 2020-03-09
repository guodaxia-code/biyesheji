package xyz.worldzhile.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @GetMapping("test1")
    public String test1(){
        return "test1";
    }


    @PostMapping("test2")
    @ResponseBody
    public void test2(HttpServletRequest request, HttpServletResponse response){

        System.out.println("文件上传");
        //传统文件上传使用fileupload组件
        //上传的位置
        String path=request.getSession().getServletContext().getRealPath("/uploads/");
        System.out.println(path);
        //判断路径是否存在
        File file=new File(path);
        if (!file.exists()){
            file.mkdirs();
        }

        //解析request对象,获取上传文件项
        DiskFileItemFactory fileItemFactory=new DiskFileItemFactory();
        ServletFileUpload upload=new ServletFileUpload(fileItemFactory);
        //解析request
        List<FileItem> fileItems=null;
        try {
             fileItems = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        //遍历
        for (FileItem fileItem : fileItems) {
            if (fileItem.isFormField()){
                //普通表单项
            }else{
                //上传文件项
                //获取上传文件的名称
                String fieldName = fileItem.getFieldName();
                String uuid = UuidUtil.getUuid();
                String name = fileItem.getName();
                fieldName=uuid+"_"+fieldName+name.substring(name.indexOf("."),name.length());


                try {
                    fileItem.write(new File(path,fieldName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(fieldName+"-------"+fileItem);
                //删除临时文件
                fileItem.delete();

            }
        }




    }
}
