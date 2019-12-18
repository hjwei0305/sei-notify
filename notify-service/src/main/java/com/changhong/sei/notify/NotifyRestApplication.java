package com.changhong.sei.notify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * <strong>实现功能:</strong>
 * <p>REST服务主程序</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-18 10:41
 */
@SpringBootApplication
@ComponentScan(value = {"com.changhong.sei.notify.service"})
public class NotifyRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotifyRestApplication.class, args);
    }
}
