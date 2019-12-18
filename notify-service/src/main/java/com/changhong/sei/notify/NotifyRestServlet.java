package com.changhong.sei.notify;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * <strong>实现功能:</strong>
 * <p>SpringBoot War Servlet</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-18 10:43
 */
public class NotifyRestServlet extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(NotifyRestServlet.class);
    }
}
