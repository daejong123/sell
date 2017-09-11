package com.daejong;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Daejong on 2017/9/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j //这里lomback会提供一个变量log. 可以直接使用.
public class LoggerTest {

    //这里传参数的时候, 会在打印的时候会输出当前类的名称.
    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test1() {
        String name = "china";
        String password = "123456";
        logger.debug("debug..."); //DEBUG com.daejong.LoggerTest - debug...
        logger.info("name ," + name + ", password," + password); //INFO com.daejong.LoggerTest - info...
        logger.info("name {}, password {}", name, password); //方式2
        logger.error("error..."); //ERROR com.daejong.LoggerTest - error...
    }

}
