package com.zookeeper.node;

import com.zookeeper.client.CuratorFactory;
import com.zookeeper.util.ObjectsTranscoder;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title:      ZooKeeperNodeOperation. </p>
 * <p>Description ZK节点类
 * <p>
 * Zookeeper的节点创建模式：
 * PERSISTENT：持久化
 * PERSISTENT_SEQUENTIAL：持久化并且带序列号
 * EPHEMERAL：临时
 * EPHEMERAL_SEQUENTIAL：临时并且带序列号
 * <p>
 * 详细请查看 http://www.jianshu.com/p/70151fc0ef5d
 * 注意： 临时节点不能创建子节点
 * </p>
 * <p>Company:    李清栋 </p>
 *
 * @author <a href="liqingdong"/>李清栋</a>
 * @CreateDate 2017/9/29 10:27
 */
public class ZooKeeperNodeOperation {

    private CuratorFramework client = CuratorFactory.getCurator();

    /**
     * <p>Title:      创建一个节点，自动递归创建父节点. </p>
     * <p>Description </p>
     *
     * @param
     * @return
     * @Author <a href="liqingdong"/>李清栋</a>
     * @CreateDate 2017/9/29 15:21
     */
    public <T extends Serializable> String create(String node, CreateMode mode, T value) throws Exception {
        byte[] bytes = null;
        if (value != null) {
            bytes = ObjectsTranscoder.serialize(value);
        }
        String result = client.create().creatingParentsIfNeeded().withMode(mode).forPath(node, bytes);
        return result;
    }

    /**
     * <p>Title:      删除一个节点，并且递归删除其所有的子节点. </p>
     * <p>Description </p>
     *
     * @param
     * @return
     * @Author <a href="liqingdong"/>李清栋</a>
     * @CreateDate 2017/9/29 15:22
     */
    public void delete(String node) throws Exception {
        client.delete().deletingChildrenIfNeeded().forPath(node);
    }

    /**
     * <p>Title:      更新一个节点的数据内容. </p>
     * <p>Description </p>
     *
     * @param
     * @return
     * @Author <a href="liqingdong"/>李清栋</a>
     * @CreateDate 2017/9/29 15:23
     */
    public <T extends Serializable> Stat update(String node, T value) throws Exception {
        byte[] bytes = null;
        if (value != null) {
            bytes = ObjectsTranscoder.serialize(value);
        }
        Stat result = client.setData().forPath(node, bytes);
        return result;
    }

    /**
     * <p>Title:      检查节点是否存在. </p>
     * <p>Description </p>
     *
     * @param
     * @return
     * @Author <a href="liqingdong"/>李清栋</a>
     * @CreateDate 2017/9/29 15:23
     */
    public Stat checkExists(String node) throws Exception {
        Stat result = client.checkExists().forPath(node);
        return result;
    }

    /**
     * <p>Title:      读取一个节点的数据内容. </p>
     * <p>Description </p>
     *
     * @param
     * @return
     * @Author <a href="liqingdong"/>李清栋</a>
     * @CreateDate 2017/9/29 15:23
     */
    public <T extends Serializable> T get(String node) throws Exception {
        byte[] bytes = client.getData().forPath(node);
        T result = ObjectsTranscoder.deserialize(bytes);
        return result;
    }

    /**
     * <p>Title:      获取某个节点的所有子节点路径. </p>
     * <p>Description </p>
     *
     * @param
     * @return
     * @Author <a href="liqingdong"/>李清栋</a>
     * @CreateDate 2017/9/29 15:23
     */
    public List<String> getChildren(String node) throws Exception {
        List<String> result = client.getChildren().forPath(node);
        return result;
    }
}
