package com.oigbuy.droolzk.utils;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * @author qiang.zhou
 * on 2018-05-15.
 */
public class ZookeeperUtil {
    private ZooKeeper zookeeper;

    public ZookeeperUtil(String url,Watcher watcher) {
        try {
            zookeeper = new ZooKeeper(url, 5000, watcher);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ZooKeeper getZK() {
        return zookeeper;
    }


    /**
     * 创建znode结点
     *
     * @param path 结点路径
     * @param data 结点数据
     * @return true 创建结点成功 false表示结点存在
     * @throws Exception
     */
    public boolean addZnodeData(String path, String data, CreateMode mode) {
        try {
            if (zookeeper.exists(path, true) == null) {
                zookeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, mode);
                return true;
            }
        } catch (KeeperException | InterruptedException e) {
            throw new RuntimeException("创建znode：" + path + "出现问题！！", e);
        }
        System.out.println("znode" + path + "结点已存在");
        return false;
    }

    /**
     * 创建永久znode结点
     *
     * @param path 结点路径
     * @param data 结点数据
     * @return true 创建结点成功 false表示结点存在
     * @throws Exception
     */
    public boolean addPZnode(String path, String data) {
        return addZnodeData(path, data, CreateMode.PERSISTENT);
    }

    /**
     * 创建临时znode结点
     *
     * @param path 结点路径
     * @param data 结点数据
     * @return true 创建结点成功 false表示结点存在
     * @throws Exception
     */
    public boolean addZEnode(String path, String data) {
        return addZnodeData(path, data, CreateMode.EPHEMERAL);
    }

    /**
     * 修改znode
     *
     * @param path 结点路径
     * @param data 结点数据
     * @return 修改结点成功   false表示结点不存在
     */
    public boolean updateZnode(String path, String data) {
        try {
            Stat stat = null;
            if ((stat = zookeeper.exists(path, true)) != null) {
                zookeeper.setData(path, data.getBytes(), stat.getVersion());
                return true;
            }
        } catch (KeeperException | InterruptedException e) {
            throw new RuntimeException("修改znode：" + path + "出现问题！！", e);
        }
        return false;
    }

    /**
     * 删除结点
     *
     * @param path 结点
     * @return true 删除键结点成功  false表示结点不存在
     */
    public boolean deleteZnode(String path) {
        try {
            Stat stat = null;
            if ((stat = zookeeper.exists(path, true)) != null) {
                List<String> subPaths = zookeeper.getChildren(path, false);
                if (subPaths.isEmpty()) {
                    zookeeper.delete(path, stat.getVersion());
                    return true;
                } else {
                    for (String subPath : subPaths) {
                        deleteZnode(path + "/" + subPath);
                    }
                }
            }
        } catch (InterruptedException | KeeperException e) {
            throw new RuntimeException("删除znode：" + path + "出现问题！！", e);
        }
        return false;
    }

    /**
     * 取到结点数据
     *
     * @param path 结点路径
     * @return null表示结点不存在 否则返回结点数据
     */
    public String getZnodeData(String path) {
        String data = null;
        try {
            Stat stat = null;
            if ((stat = zookeeper.exists(path, true)) != null) {
                data = new String(zookeeper.getData(path, true, stat));
            } else {
                System.out.println("znode:" + path + ",不存在");
            }
        } catch (KeeperException | InterruptedException e) {
            throw new RuntimeException("取到znode：" + path + "出现问题！！", e);
        }
        return data;
    }
}
