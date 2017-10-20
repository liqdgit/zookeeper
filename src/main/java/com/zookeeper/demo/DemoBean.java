package com.zookeeper.demo;

import java.io.Serializable;

/**
 * <p>Title:      DemoBean. </p>
 * <p>Description TODO </p>
 * <p>Company:    李清栋 </p>
 *
 * @Author         <a href="liqingdong"/>李清栋</a>
 * @CreateDate     2017/9/29 11:00
 */
public class DemoBean implements Serializable {

    private String id;

    private String key;

    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
