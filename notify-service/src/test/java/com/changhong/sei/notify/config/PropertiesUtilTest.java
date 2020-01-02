package com.changhong.sei.notify.config;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.notify.BaseUnitTest;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <strong>实现功能:</strong>
 * <p></p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-31 20:32
 */
public class PropertiesUtilTest extends BaseUnitTest {

//    @Test
//    public void getProperty() {
//        String value = PropertiesUtil.getProperty("notify.test-key");
//        Assert.assertNotNull(value);
//        System.out.println("notify.test-key="+value);
//        value = PropertiesUtil.getProperty("00001");
//        Assert.assertNotNull(value);
//        System.out.println("00001="+value);
//        value = PropertiesUtil.getProperty("core_service_00001");
//        Assert.assertNotNull(value);
//        System.out.println("core_service_00001="+value);
//    }

    @Test
    public void getMessage(){
        String message = ContextUtil.getMessage("core_service_00003", "tes-001");
        Assert.assertNotNull(message);
        System.out.println("core_service_00003="+message);
        message = ContextUtil.getMessage("00001");
        Assert.assertNotNull(message);
        System.out.println("00001="+message);
    }
}