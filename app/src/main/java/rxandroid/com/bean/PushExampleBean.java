package rxandroid.com.bean;

import java.io.Serializable;

/**
 * Created on 2017/7/10.
 * Author by Aaron.Wang
 */

public class PushExampleBean implements Serializable {
    //JPush推送默认字段

    private String keyId = "";      //用于查询的id值

    private String userId = "";     //  用于某些推送查询用户的信息

    private String command = "";     //   推送的文案类型


    private String value1= "" ;      //扩展字段    ，根据具体的需求代表不同的值
    private String value2= "" ;      //扩展字段
    private String value3= "" ;      //扩展字段
    private String value4= "" ;      //扩展字段
    private String value5= "" ;      //扩展字段



    public String getKeyId() {
        return keyId;
    }
    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getCommand() {
        return command;
    }
    public void setCommand(String command) {
        this.command = command;
    }
    public String getValue1() {
        return value1;
    }
    public void setValue1(String value1) {
        this.value1 = value1;
    }
    public String getValue2() {
        return value2;
    }
    public void setValue2(String value2) {
        this.value2 = value2;
    }
    public String getValue3() {
        return value3;
    }
    public void setValue3(String value3) {
        this.value3 = value3;
    }
    public String getValue4() {
        return value4;
    }
    public void setValue4(String value4) {
        this.value4 = value4;
    }
    public String getValue5() {
        return value5;
    }
    public void setValue5(String value5) {
        this.value5 = value5;
    }
}
