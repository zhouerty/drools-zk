package com.oigbuy.droolzk.dao;

import com.oigbuy.droolzk.annotation.MysqlRepository;
import com.oigbuy.droolzk.entity.RuleLogMessage;

/**
 * @author qiang.zhou
 * on 2018-05-16.
 */
@MysqlRepository
public interface RuleLogMapper {

    /**
     * 保存规则执行日志
     * @param ruleLogMessage 日志信息
     */
    void saveRuleExecuteLog(RuleLogMessage ruleLogMessage);
}
