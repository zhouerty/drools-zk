package com.oigbuy.droolzk.utils;

import com.oigbuy.droolzk.listener.AgendaEventListenerImpl;
import com.oigbuy.droolzk.listener.RuleRuntimeEventListenerImpl;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * KieUtils.java
 *
 * @author qiang.zhou
 * on 2018年5月10日  新建
 */
public class KieUtils {
    private static KieContainer kieContainer;

    private static KieSession kieSession;

    public static KieContainer getKieContainer() {
        return kieContainer;
    }

    public static void setKieContainer(KieContainer kieContainer) {
        KieUtils.kieContainer = kieContainer;
        kieSession = kieContainer.newKieSession();
    }

    public static KieSession getKieSession() {
        kieSession = KieUtils.kieContainer.newKieSession();
        kieSession.addEventListener(new RuleRuntimeEventListenerImpl());
        kieSession.addEventListener(new AgendaEventListenerImpl());
        return kieSession;
    }

    public static void setKieSession(KieSession kieSession) {
        KieUtils.kieSession = kieSession;
    }
}
