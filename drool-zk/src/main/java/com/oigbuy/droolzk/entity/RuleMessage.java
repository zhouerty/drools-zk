package com.oigbuy.droolzk.entity;
/**
* RuleMessage.java
*
* @author qiang.zhou
* on 2018年5月10日  新建
*/
public class RuleMessage {

	/**
	 * 规则ID
	 */
	private Long ruleId;
	/**
	 * 规则名
	 */
	private String ruleName;
	/**
	 * 规则详情
	 */
	private String ruleDesc;
	/**
	 * 规则版本
	 */
	private String ruleVersion;
	/**
	 * 规则启用状态
	 */
	private String flag;
    /**
     * 规则所在包名
     */
	private String packageName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Long getRuleId() {
		return ruleId;
	}
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleDesc() {
		return ruleDesc;
	}
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	public String getRuleVersion() {
		return ruleVersion;
	}
	public void setRuleVersion(String ruleVersion) {
		this.ruleVersion = ruleVersion;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
