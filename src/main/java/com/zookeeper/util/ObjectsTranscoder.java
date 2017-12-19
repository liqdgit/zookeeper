package com.zookeeper.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * <p>Title:      ObjectsTranscoder. </p>
 * <p>Description 对象转码器 </p>
 * <p>Company:    李清栋 </p>
 *
 * @author <a href="liqingdong"/>李清栋</a>
 * @CreateDate 2017/9/29 10:56
 */
public class ObjectsTranscoder {

    /**
     * <p>Title:      对象序列化. </p>
     * <p>Description </p>
     *
     * @param
     * @return
     * @Author <a href="liqingdong"/>李清栋</a>
     * @CreateDate 2017/9/29 10:57
     */
    public static <T extends Serializable> byte[] serialize(T value) throws IOException {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }

        byte[] result;
        ByteArrayOutputStream byteOutPut = new ByteArrayOutputStream();
        ObjectOutputStream objOutPut = new ObjectOutputStream(byteOutPut);

        objOutPut.writeObject(value);
        byteOutPut.close();
        objOutPut.close();

        result = byteOutPut.toByteArray();
        return result;
    }

    /**
     * <p>Title:      反序列化. </p>
     * <p>Description </p>
     *
     * @param
     * @return
     * @Author <a href="liqingdong"/>李清栋</a>
     * @CreateDate 2017/9/29 10:58
     */
    public static <T extends Serializable> T deserialize(byte[] values) throws IOException, ClassNotFoundException {
        if (values == null) {
            throw new NullPointerException("Can't serialize null");
        }

        ByteArrayInputStream byteInput = new ByteArrayInputStream(values);
        ObjectInputStream objInput = new ObjectInputStream(byteInput);

        T t = (T) objInput.readObject();

        byteInput.close();
        objInput.close();
        return t;
    }

}
