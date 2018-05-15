package com.oigbuy.droolzk.listener;

import com.oigbuy.droolzk.utils.JsonUtil;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.rule.FactHandle;

/**
 * RuleRuntimeEventListenerImpl.java
 *
 * @author qiang.zhou
 * on 2018年5月4日  新建
 */
public class RuleRuntimeEventListenerImpl implements RuleRuntimeEventListener {

    @Override
    public void objectInserted(ObjectInsertedEvent event) {
        Object o = event.getObject();
        if (o != null) {
            System.out.println("Insert Param ：" + JsonUtil.toJson(o));
        }
        if (event.getRule() != null) {
            System.out.println("NOT Null");
            System.out.println(event.getRule().getName());
            System.out.println(event.getRule().getPackageName());
            System.out.println(event.getRule().getId());
            System.out.println(event.getRule().getMetaData());
            System.out.println(event.getRule().getKnowledgeType().name());
            System.out.println(event.getRule().getNamespace());
        }
        FactHandle factHandle = event.getFactHandle();
        System.out.println(factHandle.toExternalForm());
    }

    @Override
    public void objectUpdated(ObjectUpdatedEvent event) {
        System.out.println("执行了Update");
        Object o = event.getObject();
        if (o != null) {
            System.out.println(JsonUtil.toJson(o));
        }
        Object o2 = event.getOldObject();
        if (o2 != null) {
            System.out.println(JsonUtil.toJson(o2));
        }
        String packageName = event.getRule().getPackageName();
        System.out.println("包名：" + packageName);
        System.out.println(event.getRule().getMetaData());

    }

    @Override
    public void objectDeleted(ObjectDeletedEvent event) {
        System.out.println("执行了Delete");
        Object o2 = event.getOldObject();
        if (o2 != null) {
            System.out.println(JsonUtil.toJson(o2));
        }
        String packageName = event.getRule().getPackageName();
        System.out.println("包名：" + packageName);
        System.out.println("aaaaa" + event.getFactHandle().toString());
    }

}
