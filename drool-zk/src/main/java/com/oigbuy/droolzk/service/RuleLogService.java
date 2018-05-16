package com.oigbuy.droolzk.service;


import com.oigbuy.droolzk.dao.RuleLogMapper;
import com.oigbuy.droolzk.entity.RuleLogMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author qiang.zhou
 * on 2018-05-16.
 */
@Transactional(rollbackFor = Exception.class,readOnly = false)
@Service("ruleLogService")
public class RuleLogService {

    @Autowired private RuleLogMapper ruleLogMapper;

    public void saveRuleLog(RuleLogMessage ruleLogMessage){
        ruleLogMessage.setCreateTime(new Date());
        ruleLogMapper.saveRuleExecuteLog(ruleLogMessage);
    }
}
