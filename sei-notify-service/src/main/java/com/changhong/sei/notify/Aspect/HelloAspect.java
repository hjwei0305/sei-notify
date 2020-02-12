package com.changhong.sei.notify.Aspect;

import com.changhong.sei.core.util.JsonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 实现功能: Hello 的切面
 *
 * @author 王锦光 wangjg
 * @version 2020-02-12 22:33
 */
@Component
@Aspect
public class HelloAspect {
    /**
     * 在说话前执行
     */
    @Before("execution(* com.changhong.sei.notify.api.HelloApi.sayHello(..))")
    public void beforeSay(JoinPoint point){
        Object[] args = point.getArgs();
        System.out.println("Aspect log: 在 Say 方法之前执行....输入参数："+ JsonUtils.toJson(args));
    }

    /**
     * 在说完话后执行
     */
    @AfterReturning(pointcut = "execution(* com.changhong.sei.notify.api.HelloApi.sayHello(..))",
            returning = "returnObj")
    public void afterSay(Object returnObj){
        System.out.println("Aspect log: 在 Say 方法之后执行....返回对象：" + JsonUtils.toJson(returnObj));
    }
}
