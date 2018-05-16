package com.oigbuy.droolzk.listener;

import com.oigbuy.droolzk.utils.JsonUtil;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.rule.FactHandle;

import java.util.Map;

/**
 * RuleRuntimeEventListenerImpl.java
 *
 * @author qiang.zhou
 * on 2018年5月4日  新建
 */
public class RuleRuntimeEventListenerImpl implements RuleRuntimeEventListener {

    @Override
    public void objectInserted(ObjectInsertedEvent event) {
        if(event.getRule() != null && event.getRule().getName() != null){
            String ruleName = event.getRule().getName();
            String packageName = event.getRule().getPackageName();
            Map<String,Object> objMap = event.getRule().getMetaData();
            System.out.println("规则名：" + ruleName);
            System.out.println("规则所在包名：" + packageName);
            System.out.println("规则DATA：" + JsonUtil.toJson(objMap));
        }
        System.out.println("objectInserted--事实插入的监听");
    }

    @Override
    public void objectUpdated(ObjectUpdatedEvent event) {
        System.out.println("objectUpdated--事实修改监听");
    }

    @Override
    public void objectDeleted(ObjectDeletedEvent event) {
        System.out.println("objectDeleted--事实删除监听");
    }

}
