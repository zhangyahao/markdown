package util.response;

import util.DateUtil;

/**
 * @program: markdown
 * @description:
 * @author: Zhang
 * @create: 2021-01-18 10:28
 **/
public class JsonMessage {
    private int status;
    private Object result;
    private String serverTime;

    public JsonMessage(){
    }

    public JsonMessage(int status, Object result, String serverTime){
        this.status = status;
        this.result = result;
        this.serverTime = serverTime;
    }

    public JsonMessage(int status, Object result){
        this(status, result, DateUtil.getSysDateTimeString());
    }


    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public Object getresult() {
        return result;
    }
    public void setresult(Object result) {
        this.result = result;
    }
    public String getServerTime() {
        return serverTime;
    }
    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }
}
