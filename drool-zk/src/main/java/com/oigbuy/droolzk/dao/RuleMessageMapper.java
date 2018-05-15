package com.oigbuy.droolzk.dao;

import com.oigbuy.droolzk.annotation.MysqlRepository;
import com.oigbuy.droolzk.entity.RuleMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * RuleMessageMapper.java
 *
 * @author qiang.zhou
 * on 2018年5月10日  新建
 */
@MysqlRepository
public interface RuleMessageMapper {

    /**
     * 获取最新版本号
     *
     * @return 版本号
     */
    String getNewVersion();

    /**
     * 按照版本获取当前版本下的所有规则
     *
     * @param version 版本号
     * @return 版本下的锁有规则
     */
    List<RuleMessage> getEnableRuleByVersion(String version);

    /**
     * 按照版本号 和 规则名获取 规则
     *
     * @param ruleName    规则名
     * @param ruleVersion 规则版本
     * @return 规则
     */
    RuleMessage getEnableRuleByName(@Param("ruleName") String ruleName, @Param("ruleVersion") String ruleVersion);

    /**
     * 修改版本
     *
     * @param version 版本
     */
    void updateVersion(String version);
}
