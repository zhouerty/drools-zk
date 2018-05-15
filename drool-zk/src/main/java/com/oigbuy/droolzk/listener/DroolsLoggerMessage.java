package com.oigbuy.droolzk.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 规则执行LOG记录类
 * @author qiang.zhou
 * on 2018-05-14.
 */
public class DroolsLoggerMessage {

    private static final Logger LOG = LoggerFactory.getLogger(DroolsLoggerMessage.class);

    public static void printMessage(String droolsName){
        LOG.info("执行的规则：" + droolsName);
    }
}
