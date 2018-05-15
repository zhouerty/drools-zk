package com.oigbuy.droolzk.service;


import com.oigbuy.droolzk.dao.RuleMessageMapper;
import com.oigbuy.droolzk.entity.RuleMessage;
import com.oigbuy.droolzk.utils.JsonUtil;
import com.oigbuy.droolzk.utils.KieUtils;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * ReloadDroolsRules.java
 *
 * @author qiang.zhou
 * on 2018年5月10日  新建
 */
@Service("reloadDroolsRules")
public class ReloadDroolsRules {
    private static final Logger LOG = LoggerFactory.getLogger(ReloadDroolsRules.class);

    private static final String FORMAT = ".drl";

    @Autowired
    private RuleMessageMapper dao;

    /**
     * 规则加载方法
     * @throws UnsupportedEncodingException 不支持解密异常
     */
    public void load() throws UnsupportedEncodingException {
        KieServices kieServices = KieServices.Factory.get();
        //加载所有最新版本的规则
        List<RuleMessage> ruleList = dao.getEnableRuleByVersion(dao.getNewVersion());
        //如果取出的规则为空 则不执行规则加载
        if(ruleList.size() <= 0){
            LOG.info("规则加载失败，没有发现对应版本的规则");
            return;
        }
        //将取出的规则放入容器中
        loadRule(kieServices,ruleList);
        LOG.info("规则加载成功");
    }

    /**
     * 将所有规则加载到规则仓库
     * @param kieServices kieServices
     * @param ruleList 规则集合
     */
    private void loadRule(KieServices kieServices,List<RuleMessage> ruleList){
        KieFileSystem kfs = kieServices.newKieFileSystem();
        for (RuleMessage ruleMessage : ruleList) {
            kfs.write(ruleMessage.getPackageName() + ruleMessage.getRuleName() + FORMAT, ruleMessage.getRuleDesc());
            KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
            Results results = kieBuilder.getResults();
            if (results.hasMessages(Message.Level.ERROR)) {
                LOG.info(JsonUtil.toJson(results.getMessages()));
                throw new IllegalStateException("### errors ###");
            }
            KieUtils.setKieContainer(kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId()));
        }
    }
}
