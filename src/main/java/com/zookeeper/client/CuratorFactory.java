package com.zookeeper.client;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * <p>Title:      CuratorFactory. </p>
 * <p>Description CuratorFramework 工厂类 </p>
 * <p>Company:    李清栋 </p>
 *
 * @Author <a href="liqingdong"/>李清栋</a>
 * @CreateDate 2017/9/29 14:36
 */
public class CuratorFactory {

    private static CuratorFramework client;

    /**
     * 连接地址
     */
    private static String connectString = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

    /**
     * 重连睡眠时间
     */
    private static int baseSleepTimeMs = 1000;

    /**
     * 重连最大次数
     */
    private static int maxRetries = 3;

    /**
     * 命名空间
     */
    private static String namespace = "";

    /**
     * 会话超时时间，单位毫秒，默认60000ms
     */
    private static int sessionTimeoutMs = 6000;

    /**
     * 连接创建超时时间
     */
    private static int connectionTimeoutMs = 6000;

    static {
        init();
    }

    public static void init() {
        if (client == null) {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);
            client = CuratorFrameworkFactory.builder()
                    .connectString(connectString)
                    .sessionTimeoutMs(sessionTimeoutMs)
                    .connectionTimeoutMs(connectionTimeoutMs)
                    .retryPolicy(retryPolicy)
                    .namespace(namespace)
                    .build();
            client.start();
        }
    }

    public static void close() {
        if (client != null){
            client.close();
        }
    }


    public static CuratorFramework getCurator() {
        return client;
    }
}
