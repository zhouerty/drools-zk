package com.oigbuy.droolzk.memory;

import com.oigbuy.droolzk.entity.RuleLogMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author qiang.zhou
 * on 2018-05-16.
 */
public class LogData {

    public static Map<String,RuleLogMessage> memoryData = new ConcurrentHashMap<>();
}
