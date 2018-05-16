package com.oigbuy.droolzk.entity;

import java.util.Date;

/**
 * 规则日志信息
 * @author qiang.zhou
 * on 2018-05-16.
 */
public class RuleLogMessage {
    /**
     * 规则名
     */
    private String ruleName;
    /**
     * 操作
     */
    private String action;
    /**
     * 分组名
     */
    private String groupName;

    /**
     * 创建时间
     */
    private Date createTime;

    public RuleLogMessage() {}

    public RuleLogMessage(String ruleName, String action, String groupName) {
        this.ruleName = ruleName;
        this.action = action;
        this.groupName = groupName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
