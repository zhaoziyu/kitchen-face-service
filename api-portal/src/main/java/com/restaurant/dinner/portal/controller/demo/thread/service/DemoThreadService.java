package com.restaurant.dinner.portal.controller.demo.thread.service;

import org.springframework.stereotype.Service;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/3/12
 */
@Service
public class DemoThreadService {
    public void process(long processTime) {
        try {
            Thread.sleep(processTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
