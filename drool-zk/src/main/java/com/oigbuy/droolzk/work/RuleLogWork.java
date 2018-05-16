package com.oigbuy.droolzk.work;

import com.oigbuy.droolzk.memory.LogData;
import com.oigbuy.droolzk.service.RuleLogService;
import com.oigbuy.droolzk.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * 规则执行日志保存
 * @author qiang.zhou
 * on 2018-05-10.
 */
@Component
public class RuleLogWork {

    private static final Logger LOG = LoggerFactory.getLogger(RuleLogWork.class);

    @Autowired
    private RuleLogService ruleLogService;

    @Scheduled(cron = "* * * * * ? ")
    public void ruleLogSave() throws Exception {
       if(LogData.memoryData.size()>0){
           for (Iterator i = LogData.memoryData.keySet().iterator(); i.hasNext();) {
               String key = (String)i.next();
               // 循环输出key
               LOG.info(key);
               LOG.info("key=" + key + " value=" + JsonUtil.toJson(LogData.memoryData.get(key)));
               ruleLogService.saveRuleLog(LogData.memoryData.get(key));
               i.remove();
           }
       }
    }
}
