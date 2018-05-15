package com.oigbuy.droolzk.listener;

import org.kie.api.event.process.*;

/**
 *
* ProcessEventListenerImpl.java
*
* @author qiang.zhou
* on 2018年5月9日  新建
 */
public class ProcessEventListenerImpl implements ProcessEventListener {
    @Override
    public void beforeProcessStarted(ProcessStartedEvent event) {
        final String processId = event.getProcessInstance().getProcessId();
    }

    @Override
    public void afterProcessStarted(ProcessStartedEvent event) {
        String processId = event.getProcessInstance().getProcessId();
    }

    @Override
    public void beforeProcessCompleted(ProcessCompletedEvent event) {
        String processId = event.getProcessInstance().getProcessId();
    }

    @Override
    public void afterProcessCompleted(ProcessCompletedEvent event) {
        String processId = event.getProcessInstance().getProcessId();
    }

    @Override
    public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
          String processId = event.getProcessInstance().getProcessId();
    }

    @Override
    public void afterNodeTriggered(ProcessNodeTriggeredEvent event) {
        String processId = event.getProcessInstance().getProcessId();
    }

    @Override
    public void beforeNodeLeft(ProcessNodeLeftEvent event) {
        String processId = event.getProcessInstance().getProcessId();
    }

    @Override
    public void afterNodeLeft(ProcessNodeLeftEvent event) {
        String processId = event.getProcessInstance().getProcessId();
    }

    @Override
    public void beforeVariableChanged(ProcessVariableChangedEvent event) {
        String processId = event.getProcessInstance().getProcessId();
    }

    @Override
    public void afterVariableChanged(ProcessVariableChangedEvent event) {
        String processId = event.getProcessInstance().getProcessId();
    }
}
