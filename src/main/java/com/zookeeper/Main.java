package com.zookeeper;

import com.zookeeper.client.CuratorFactory;
import com.zookeeper.node.ZooKeeperNodeOperation;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;


public class Main {

    public static void main(String[] args) throws Exception {
//        String str = null;
//        byte[] bytes = null;
//        GetDataBuilder getDataBuilder = null;
//
//        String connectString = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
//        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
//        CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
//        client.start();
//        String value = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/demo/path2", "demo".getBytes());
//        System.out.println(value);
//        getDataBuilder = client.getData();
//        bytes = getDataBuilder.forPath("/demo/path");
//        str = new String(bytes);
//        System.out.println(str);


//        connectString = "127.0.0.1:2182";
//        client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
//        client.start();
//        client.setData().forPath("/demo/path", "other data".getBytes());
//        getDataBuilder = client.getData();
//        bytes = getDataBuilder.forPath("/demo/path");
//        str = new String(bytes);
//        System.out.println(str);
//
//        connectString = "127.0.0.1:2183";
//        client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
//        client.start();
//        getDataBuilder = client.getData();
//        bytes = getDataBuilder.forPath("/demo/path");
//        str = new String(bytes);
//        System.out.println(str);

//        byte[] value = ObjectsTranscoder.serialize(new DemoBean());
//        DemoBean demoBean = ObjectsTranscoder.deserialize(value);
//        System.out.println(demoBean);

        /*ZooKeeperNodeOperation zooKeeper = new ZooKeeperNodeOperation();
        String result = zooKeeper.create("/node1", CreateMode.EPHEMERAL, "node1_init");
        System.out.println("创建节点node1， result="+result);

        result = zooKeeper.create("/node1/node1", CreateMode.EPHEMERAL, "node1/node1_init");
        System.out.println("创建节点/node1/node1， result="+result);
        result = zooKeeper.create("/node1/node2", CreateMode.EPHEMERAL, "node1/node2_init");
        System.out.println("创建节点/node1/node2， result="+result);

        result = zooKeeper.create("/node1/node1/node1", CreateMode.EPHEMERAL, "node1/node1/node1_init");
        System.out.println("创建节点/node1/node1/node1， result="+result);
        result = zooKeeper.create("/node1/node1/node2", CreateMode.EPHEMERAL, "node1/node1/node2_init");
        System.out.println("创建节点/node1/node1/node2， result="+result);

        result = zooKeeper.create("/node1/node2/node1", CreateMode.EPHEMERAL, "node1/node2/node1_init");
        System.out.println("创建节点/node1/node2/node1， result="+result);
        result = zooKeeper.create("/node1/node2/node2", CreateMode.EPHEMERAL, "node1/node2/node2_init");
        System.out.println("创建节点/node1/node2/node2， result="+result);

        List<String> nodes = zooKeeper.getChildren("/node1");
        for (String str: nodes){
            System.out.print("子节点："+str+";");
        }*/

        String path = "/example/pathCache";
        CuratorFramework client = CuratorFactory.getCurator();
        PathChildrenCache cache = new PathChildrenCache(client, path, true);
        cache.start();
        PathChildrenCacheListener cacheListener = (client1, event) -> {
            System.out.println("事件类型：" + event.getType());
            if (null != event.getData()) {
                System.out.println("节点数据：" + event.getData().getPath() + " = " + new String(event.getData().getData()));
            }
        };
        cache.getListenable().addListener(cacheListener);
        client.create().creatingParentsIfNeeded().forPath("/example/pathCache/test01", "01".getBytes());
        Thread.sleep(10);
        client.create().creatingParentsIfNeeded().forPath("/example/pathCache/test02", "02".getBytes());
        Thread.sleep(10);
        client.setData().forPath("/example/pathCache/test01", "01_V2".getBytes());
        Thread.sleep(10);
        for (ChildData data : cache.getCurrentData()) {
            System.out.println("getCurrentData:" + data.getPath() + " = " + new String(data.getData()));
        }
        client.delete().forPath("/example/pathCache/test01");
        Thread.sleep(10);
        client.delete().forPath("/example/pathCache/test02");
        Thread.sleep(1000 * 5);
        cache.close();
        client.close();
        System.out.println("OK!");
    }
}
