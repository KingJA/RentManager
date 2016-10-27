package com.kingja.cardpackage.entiy;

import java.io.Serializable;
import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/9/3 11:04
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class GetUserMessage {

    /**
     * ResultCode : 0
     * ResultText : 操作成功
     * DataTypeCode : AlarmList
     * TaskID :  1
     * Content : [{"OTHERID":"00112233445566778899AABBCCDDEEFF","ROOMID":"00112233445566778899AABBCCDDEEEE","OTHERTYPE":2,"MESSAGETEXT":"门戒触发报警","DEVICEID":"XXX","DEVICETYPE":"1088","DEVICECODE":"1234","DEVICENAME":"门戒","PARAM1":"XXX","PARAM2":"XXX","PARAM3":"XXX","PARAM4":"XXX","PARAM5":"XXX","PARAM6":"XXX","PARAM7":"XXX","PARAM8":"XXX","PARAM9":"XXX","PARAM10":"XXX","DEVICETIME":"2016-5-4 7:02:23"},{"OTHERID":"00112233445566778899AABBCCDDEE00","ROOMID":"00112233445566778899AABBCCDDEE11","OTHERTYPE":2,"MESSAGETEXT":"门戒触发报警","DEVICEID":"XXX","DEVICETYPE":"1088","DEVICECODE":"1234","DEVICENAME":"门戒","PARAM1":"XXX","PARAM2":"XXX","PARAM3":"XXX","PARAM4":"XXX","PARAM5":"XXX","PARAM6":"XXX","PARAM7":"XXX","PARAM8":"XXX","PARAM9":"XXX","PARAM10":"XXX","DEVICETIME":"2016-5-4 6:02:23"}]
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    /**
     * OTHERID : 00112233445566778899AABBCCDDEEFF
     * ROOMID : 00112233445566778899AABBCCDDEEEE
     * OTHERTYPE : 2
     * MESSAGETEXT : 门戒触发报警
     * DEVICEID : XXX
     * DEVICETYPE : 1088
     * DEVICECODE : 1234
     * DEVICENAME : 门戒
     * PARAM1 : XXX
     * PARAM2 : XXX
     * PARAM3 : XXX
     * PARAM4 : XXX
     * PARAM5 : XXX
     * PARAM6 : XXX
     * PARAM7 : XXX
     * PARAM8 : XXX
     * PARAM9 : XXX
     * PARAM10 : XXX
     * DEVICETIME : 2016-5-4 7:02:23
     */

    private List<ContentBean> Content;

    public int getResultCode() {
        return ResultCode;
    }

    public void setResultCode(int ResultCode) {
        this.ResultCode = ResultCode;
    }

    public String getResultText() {
        return ResultText;
    }

    public void setResultText(String ResultText) {
        this.ResultText = ResultText;
    }

    public String getDataTypeCode() {
        return DataTypeCode;
    }

    public void setDataTypeCode(String DataTypeCode) {
        this.DataTypeCode = DataTypeCode;
    }

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String TaskID) {
        this.TaskID = TaskID;
    }

    public List<ContentBean> getContent() {
        return Content;
    }

    public void setContent(List<ContentBean> Content) {
        this.Content = Content;
    }

    public class ContentBean implements Serializable {

        private String messageId;
        private String cardCode;
        private String cityCode;
        private String cityName;
        private String cmd;
        private String message;
        private String userId;
        private String url;
        private String createTime;
        private String operateTime;
        private String state;
        private String messageType;//默认是1

        public String getIsRead() {
            return isRead;
        }

        public void setIsRead(String isRead) {
            this.isRead = isRead;
        }

        private String isRead;

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public String getCardCode() {
            return cardCode;
        }

        public void setCardCode(String cardCode) {
            this.cardCode = cardCode;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCmd() {
            return cmd;
        }

        public void setCmd(String cmd) {
            this.cmd = cmd;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getOperateTime() {
            return operateTime;
        }

        public void setOperateTime(String operateTime) {
            this.operateTime = operateTime;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getMessageType() {
            return messageType;
        }

        public void setMessageType(String messageType) {
            this.messageType = messageType;
        }
    }
}
