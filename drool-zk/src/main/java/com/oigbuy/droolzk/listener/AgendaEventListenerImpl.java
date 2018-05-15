package com.oigbuy.droolzk.listener;

import org.kie.api.event.rule.*;
import org.kie.api.runtime.rule.FactHandle;

import java.util.List;

/**
 *
* AgendaEventListenerImpl.java
*
* @author qiang.zhou
* on 2018年5月9日  新建
 */
public class AgendaEventListenerImpl implements AgendaEventListener {
    @Override
    public void matchCreated(MatchCreatedEvent event) {
        List<Object> objects = event.getMatch().getObjects();
        List<? extends FactHandle> factHandles = event.getMatch().getFactHandles();
        List<String> declarationIds = event.getMatch().getDeclarationIds();

        if(null!=event.getMatch().getRule().getName()){
            System.out.println(event.getMatch().getRule().getName()+" matchCreated方法的执行哦");
        }else{
            System.out.println("matchCreated  MatchCreatedEvent 为什么是空的呢");
        }
    }

    @Override
    public void matchCancelled(MatchCancelledEvent event) {
        System.out.println(" matchCancelled方法的执行哦");
    }

    @Override
    public void beforeMatchFired(BeforeMatchFiredEvent event) {
        List<Object> objects = event.getMatch().getObjects();
        System.out.println("beforeMatchFired BeforeMatchFiredEvent "+event.getMatch().getRule().getName());
    }

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        List<Object> objects = event.getMatch().getObjects();
        System.out.println("afterMatchFired AfterMatchFiredEvent "+event.getMatch().getRule().getName());
    }

    @Override
    public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
        String name = event.getAgendaGroup().getName();
        event.getAgendaGroup().clear();
    }

    @Override
    public void agendaGroupPushed(AgendaGroupPushedEvent event) {
        String name = event.getAgendaGroup().getName();
    }

    @Override
    public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
    }

    @Override
    public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {

    }

    @Override
    public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {

    }

    @Override
    public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {

    }
}
