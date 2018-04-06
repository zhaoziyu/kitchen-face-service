package com.restaurant.dinner.portal.controller.demo.file;

import com.kitchen.common.api.pojo.vo.JsonObjectVo;
import com.kitchen.market.common.file.KitFileUpload;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/9
 */
@RestController
@RequestMapping(value = "/demo/file/local")
public class DemoFileUploadController {
    //TODO 可在配置文件或数据库中读取
    private static final String fileRootPath = "/Users/zhaoziyu/Work/product/upload";

    @RequestMapping(value = "/upload_single", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectVo<Map<String, String>> uploadSingle(HttpServletRequest request,
                                                          @RequestParam(value = "file", required = false) MultipartFile file) {

        //要上传到的子文件夹，若不传，则上传至指定文件系统根目录中，以日期分类的文件夹中
        String folder = request.getParameter("folder");
        //上传控件的id，将原样返回给前端
        String control = request.getParameter("control");

        boolean success;
        String msg;
        String uploadedUri = "";
        if (file != null) {
            try {
                uploadedUri = KitFileUpload.uploadToLocal(file.getInputStream(), file.getOriginalFilename(), fileRootPath, folder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            success = true;
            msg = "上传成功";
        } else {
            success = false;
            msg = "未找到要上传的文件";
        }
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("control", control);
        resultMap.put("image_uri", uploadedUri);

        JsonObjectVo<Map<String, String>> result = new JsonObjectVo<>();
        result.setSuccess(success);
        result.setMsg(msg);
        result.setData(resultMap);

        return result;
    }

    @RequestMapping(value = "/upload_multi", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectVo<Map<String, Object>> uploadMulti(HttpServletRequest request,
                                                         @RequestParam(value = "files", required = false) MultipartFile[] files) {

        //要上传到的子文件夹，若不传，则上传至upload根目录
        String folder = request.getParameter("folder");
        //上传的控件（id或name），将原样返回给前端
        String control = request.getParameter("control");

        boolean success;
        String msg;
        List<String> uploadedUri = new ArrayList<>();
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                try {
                    String uri = KitFileUpload.uploadToLocal(file.getInputStream(), file.getOriginalFilename(), fileRootPath, folder);
                    uploadedUri.add(uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (files.length == uploadedUri.size()) {
                success = true;
                msg = "上传成功";
            } else {
                success = false;
                msg = "上传成功：" + uploadedUri.size() + "个，上传失败：" + (files.length - uploadedUri.size()) + "个";
            }
        } else {
            success = false;
            msg = "未找到要上传的文件";
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("control", control);
        resultMap.put("image_uri_list", uploadedUri);

        JsonObjectVo<Map<String, Object>> result = new JsonObjectVo<>();
        result.setSuccess(success);
        result.setMsg(msg);
        result.setData(resultMap);

        return result;
    }
}
