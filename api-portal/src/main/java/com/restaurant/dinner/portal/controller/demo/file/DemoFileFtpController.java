package com.restaurant.dinner.portal.controller.demo.file;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/9
 */
@RestController
@RequestMapping(value = "/demo/file/ftp")
public class DemoFileFtpController {
    /**
     * 可在api-portal或api-provider中实现
     * 在配置文件或数据库中存储FTP相关信息（地址、用户、密码）
     * 应用工具类：org.apache.commons.net.ftp.FTPClient
     * 依赖：
     * <dependency>
     *     <groupId>org.apache.camel</groupId>
     *     <artifactId>camel-ftp</artifactId>
     *     <version>****</version>
     * </dependency>
     */
}
