package com.oigbuy.droolzk;

import com.oigbuy.droolzk.dao.RuleMessageMapper;
import org.apache.zookeeper.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author qiang.zhou
 * on 2018-05-10.
 */
@RunWith(SpringRunner.class)
@SpringBootApplication
public class TestZkWatcher {

    @Autowired
    private RuleMessageMapper ruleMessageMapper;
    static ZooKeeper zk = null;

    @Test
    public void zkListenTest() throws Exception {
        zk = new ZooKeeper("116.62.192.40:2181", 1000 * 10, new Watcher() {
            /*
             * 监听器的回调方法
             * 如果想要对某个znode进行持续监听，则需要在回调中重新注册监听器
             */
            @Override
            public void process(WatchedEvent event) {
                String path = event.getPath();
                System.out.println("----节点:" + path + " 发生了事件：" + event.getType());
                try {
                    if(path != null&&"NodeDataChanged".equals(event.getType().toString())) {
                        zk.getData("/drools.rules/ruleVersion",this,null);
                        ruleMessageMapper.updateVersion("");
                    }
                    //重新注册监听器
                  zk.exists("/drools.rules/ruleVersion", true);
                    //getChildren方法注册的监听器能监听的事件跟exists方法不一样
//                    zk.getChildren("/pass", true);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
//        zk.create("/pass", "8080".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //判断/java节点是否存在，并且给它注册一个监听器，这个监听器只能监听一次，监听到一次事件后就会失效
      zk.exists("/drools.rules/ruleVersion", true);
//        zk.getChildren("/pass", true);
        Thread.sleep(Long.MAX_VALUE);
    }
}
