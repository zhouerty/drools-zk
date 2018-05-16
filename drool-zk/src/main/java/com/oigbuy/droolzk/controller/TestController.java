package com.oigbuy.droolzk.controller;

import com.oigbuy.droolzk.config.DroolsConfig;
import com.oigbuy.droolzk.service.ReloadDroolsRules;
import com.oigbuy.droolzk.utils.KieUtils;
import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Random;

/**
 * @author qiang.zhou
 * on 2018-05-10.
 */
@RestController
@RequestMapping("/drools")
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    private volatile long serverCount = 0;

    @Autowired private DroolsConfig droolsConfig;

    @Autowired private ReloadDroolsRules rules;

    @RequestMapping("/hello")
    public String hello(){
        return "Hello World，当前版本号为：" + droolsConfig.getRuleVersion();
    }

    @RequestMapping("/reload")
    public String reload() throws IOException {
        rules.load();
        serverCount = 0;
        return "ok,规则重载成功";
    }

    @RequestMapping("/execute")
    public String execute() throws IOException {
        String [] ruleArr = {"test01","test02","test03","test08"};
        KieSession kSession = KieUtils.getKieSession();
        kSession.insert("111");
//        int count = kSession.fireAllRules(new RuleNameStartsWithAgendaFilter(ruleArr[new Random().nextInt(4)]));
        int count = kSession.fireAllRules();
        kSession.dispose();
        serverCount++;
        return "ok,执行了" + count + "条规则,调用服务端次数"+serverCount+"次" ;
    }
}
