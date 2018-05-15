package com.oigbuy.droolzk.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author qiang.zhou
 * on 2018-05-15.
 * 可以动态修改值,不需要重启
 */
@Component
@ConfigurationProperties(prefix = "drools")
public class DroolsConfig {
    private String ruleVersion;

    public String getRuleVersion() {
        return ruleVersion;
    }

    public void setRuleVersion(String ruleVersion) {
        this.ruleVersion = ruleVersion;
    }
}
