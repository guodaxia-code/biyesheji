package xyz.worldzhile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import xyz.worldzhile.util.UuidUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileController {

    @PostMapping("uploadFiles")
    @ResponseBody
    public List<String> uploadFiles(HttpServletRequest request, @RequestParam(value="file",required=true) MultipartFile[] uploadFiles){

        System.out.println("文件上传");
        //传统文件上传使用fileupload组件
        //上传的位置
        String path=request.getSession().getServletContext().getRealPath("/upload/");
        System.out.println(path);

        //判断路径是否存在
        File file=new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
        List<String> listImagePath=new ArrayList<String>();
        for (MultipartFile multipartFile:uploadFiles){
            if (multipartFile!=null){

                //获得文件类型（可以判断如果不是图片，禁止上传）
                String contentType=multipartFile.getContentType();
                String fieldName = multipartFile.getOriginalFilename();
                if (fieldName==null||"".equals(fieldName)){
                    listImagePath.add("");
                    System.out.println("上传的文件为空");
                    continue;
                }

                System.out.println(fieldName);
                String uuid = UuidUtil.getUuid();
                fieldName=uuid+"_"+fieldName;
                try {
                    multipartFile.transferTo(new File(path,fieldName));
                    System.out.println(multipartFile+"：文件上传成功");
                    listImagePath.add("/store/upload/"+fieldName);
                } catch (IOException e) {
                    System.out.println(multipartFile+"：文件上传失败");
                    e.printStackTrace();
                }
            }



        }
                return listImagePath;

    }



}
