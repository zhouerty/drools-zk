package com.oigbuy.droolzk.listener;

import com.oigbuy.droolzk.entity.RuleLogMessage;
import com.oigbuy.droolzk.memory.LogData;
import com.oigbuy.droolzk.service.RuleLogService;
import com.oigbuy.droolzk.utils.JsonUtil;
import org.kie.api.event.rule.*;
import org.kie.api.runtime.rule.AgendaGroup;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 *
* AgendaEventListenerImpl.java
*
* @author qiang.zhou
* on 2018年5月9日  新建
 */
public class AgendaEventListenerImpl implements AgendaEventListener {

    private ThreadLocal<RuleLogMessage> objLocal = new ThreadLocal<RuleLogMessage>(){{
        set(new RuleLogMessage());
    }};
    // matchCreated在执行fireAllRules时调用
    @Override
    public void matchCreated(MatchCreatedEvent event) {
        System.out.println("activation匹配之后的创建--Agenda管理调配规则变化的监听");
        //Agenda:规则的调用 创建容器 将编译的规则放到知识库  规则中可能用到的到Fact对象插入知识库
        //最后调用fierAllRule方法来触发执行规则  在没有调用之前 所有的的规则和插入的Fact对象都存放着在
        //Aagenda表中 这个Aagenda表中每一个规则和其匹配相关的数据叫Activation 在调用fireAllRules
        //之后这些Activation会依次执行
    }

    @Override//activation匹配之后又被取消 例：activation-group （具有相同activation-group属性的规则，只有一个会被执行(两个或者多个都匹配 但只执行一个)）
    public void matchCancelled(MatchCancelledEvent event) {
        System.out.println("activation匹配之后又被取消 --Agenda管理调配规则变化的监听");
    }

    //beforeMatchFired 在...之前，并未执行RHS部分的监听
    @Override
    public void beforeMatchFired(BeforeMatchFiredEvent event) {
        System.out.println("activation匹配规则执行之前--before");
//        objLocal.get().setRuleName(event.getMatch().getRule().getName());
    }

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        System.out.println("activation匹配规则执行之后--after");
        objLocal.get().setAction(event.getMatch().getRule().getName());
        LogData.memoryData.put(UUID.randomUUID().toString(),objLocal.get());
        System.out.println(JsonUtil.toJson(objLocal.get()));
    }
    /**
     * 监听规则议程分组执行前后
     * 当规则中用到agenda-group 属性是可用此方法监听
     * @param event 议程分组调用事件
     */
    @Override
    public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
        System.out.println("agendaGroupPopped议程分组1--agenda-group--取出");
//        System.out.println(event.getAgendaGroup().getName());
    }

    @Override
    public void agendaGroupPushed(AgendaGroupPushedEvent event) {
        System.out.println("agendaGroupPushed议程分组2--agenda-group--放入");
//        objLocal.get().setGroupName(event.getAgendaGroup().getName());
//        System.out.println(event.getAgendaGroup().getName());
    }

    @Override
    public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
        System.out.println("beforeRuleFlowGroupActivated 1--Agenda管理调配规则变化的监听");
    }

    @Override
    public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
        System.out.println("afterRuleFlowGroupActivated 2--Agenda管理调配规则变化的监听");
    }

    @Override
    public void beforeRuleFlowGroupDeactivated(
            RuleFlowGroupDeactivatedEvent event) {

    }

    @Override
    public void afterRuleFlowGroupDeactivated(
            RuleFlowGroupDeactivatedEvent event) {

    }
}
