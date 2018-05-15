package com.oigbuy.droolzk.controller;

import com.oigbuy.droolzk.config.DroolsConfig;
import com.oigbuy.droolzk.service.ReloadDroolsRules;
import com.oigbuy.droolzk.utils.KieUtils;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author qiang.zhou
 * on 2018-05-10.
 */
@RestController
@RequestMapping("/drools")
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Autowired private DroolsConfig droolsConfig;

    @RequestMapping("hello")
    public String hello(){
        return "Hello World " + droolsConfig.getRuleVersion();
    }

    @Autowired
    private ReloadDroolsRules rules;

    @ResponseBody
    @RequestMapping("/reload")
    public String reload() throws IOException {
        rules.load();
        return "ok,规则重载成功";
    }

    @ResponseBody
    @RequestMapping("/execute")
    public String execute() throws IOException {
        KieSession kSession = KieUtils.getKieSession();
        kSession.insert("111");
        int count = kSession.fireAllRules();
        kSession.dispose();
        return "ok,执行了" + count + "条规则";
    }
}
