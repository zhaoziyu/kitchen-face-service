package com.restaurant.dinner.portal.controller.demo.file;

import com.kitchen.market.common.file.KitFileByte;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/9
 */
@RestController
@RequestMapping(value = "/demo/file/stream")
public class DemoFileStreamDownloadController {
    private static final byte[] FILE_BYTE = {97,97,32,-27,-107,-118,-27,-107,-118,32,49,49,13,10,98,98,32,-26,-118,-91,-24,-95,-88,32,50,50,13,10,99,99,32,-27,-121,-70,-27,-73,-82,32,51,51};

    @RequestMapping(value="/download_mode1_byte")
    public ResponseEntity<byte[]> downloadMode1ForByte(HttpServletRequest request, @RequestParam("filename") String filename, Model model)throws Exception {
        //下载显示的文件名，解决中文名称乱码问题
        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");

        HttpHeaders headers = new HttpHeaders();
        //通知浏览器以attachment（下载方式）打开图片
        headers.setContentDispositionFormData("attachment", downloadFileName);
        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<>(FILE_BYTE, headers, HttpStatus.CREATED); //TODO 指定文件字节流（FILE_BYTE）
    }
    @RequestMapping(value="/download_mode1_local")
    public ResponseEntity<byte[]> downloadMode1ForLocal(HttpServletRequest request, @RequestParam("filename") String filename, Model model)throws Exception {
        //下载文件路径
        String path = request.getServletContext().getRealPath("/<文件目录>/"); //TODO 指定下载文件所在目录
        File file = new File(path + File.separator + filename);

        //下载显示的文件名，解决中文名称乱码问题
        String downloadFielName = new String(filename.getBytes("UTF-8"),"iso-8859-1");

        HttpHeaders headers = new HttpHeaders();
        //通知浏览器以attachment（下载方式）打开图片
        headers.setContentDispositionFormData("attachment", downloadFielName);
        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<>(KitFileByte.fileToByte(new FileInputStream(file)), headers, HttpStatus.CREATED);
    }


    /**
     * 不推荐
     * 使用response返回下文件的byte流
     * @param fileName
     * @param response
     */
    @RequestMapping(value="/download_mode2_byte")
    public void downloadMode2ForByte(@RequestParam(value = "filename", required = true) String fileName, HttpServletResponse response) {
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType("application/octet-stream; charset=utf-8");
            outputStream.write(FILE_BYTE);//TODO 指定文件字节流（FILE_BYTE）
            outputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 不推荐
     * 使用response返回下载存在的物理文件
     * @param fileName
     * @param response
     */
    @RequestMapping(value="/download_mode2_local")
    public void downloadMode2ForLocal(@RequestParam(value = "filename", required = true) String fileName, HttpServletResponse response) {
        String dataDirectory = "/<文件目录>/"; //TODO 指定下载文件所在目录
        Path file = Paths.get(dataDirectory, fileName);
        if (Files.exists(file)) {
            response.setContentType("application/x-gzip");
            try {
                response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
                Files.copy(file, response.getOutputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
