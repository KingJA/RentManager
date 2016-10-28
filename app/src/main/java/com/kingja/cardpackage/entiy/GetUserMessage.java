package com.kingja.cardpackage.entiy;

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
     * ResultText : 获取用户消息列表成功
     * DataTypeCode : null
     * TaskID : null
     * Content : [{"MessageID":"0fd89a4aad794ef28c0e07f5c0b6f60d","CardCode":"1004","CityCode":"3303","CMD":null,"Message":"九点半酒吧（道南路分店）,智慧栅栏（社区版）（602）,触发报警,请确认安全","UserID":"dea3b307-907e-4dcb-859b-c7b603f793b3","URL":"122.228.89.67","CreateTime":"2016-10-09 00:00:00","OperateTime":"2016-10-10 00:00:00","IsRead":1,"MessageType":2,"CityName":"温州市"}]
     */

    private String ResultCode;
    private String ResultText;
    private Object DataTypeCode;
    private Object TaskID;
    /**
     * MessageID : 0fd89a4aad794ef28c0e07f5c0b6f60d
     * CardCode : 1004
     * CityCode : 3303
     * CMD : null
     * Message : 九点半酒吧（道南路分店）,智慧栅栏（社区版）（602）,触发报警,请确认安全
     * UserID : dea3b307-907e-4dcb-859b-c7b603f793b3
     * URL : 122.228.89.67
     * CreateTime : 2016-10-09 00:00:00
     * OperateTime : 2016-10-10 00:00:00
     * IsRead : 1
     * MessageType : 2
     * CityName : 温州市
     */

    private List<ContentBean> Content;

    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String ResultCode) {
        this.ResultCode = ResultCode;
    }

    public String getResultText() {
        return ResultText;
    }

    public void setResultText(String ResultText) {
        this.ResultText = ResultText;
    }

    public Object getDataTypeCode() {
        return DataTypeCode;
    }

    public void setDataTypeCode(Object DataTypeCode) {
        this.DataTypeCode = DataTypeCode;
    }

    public Object getTaskID() {
        return TaskID;
    }

    public void setTaskID(Object TaskID) {
        this.TaskID = TaskID;
    }

    public List<ContentBean> getContent() {
        return Content;
    }

    public void setContent(List<ContentBean> Content) {
        this.Content = Content;
    }

    public static class ContentBean {
        private String MessageID;
        private String CardCode;
        private String CityCode;
        private Object CMD;
        private String Message;
        private String UserID;
        private String URL;
        private String CreateTime;
        private String OperateTime;
        private int IsRead;
        private int MessageType;
        private String CityName;

        public String getMessageID() {
            return MessageID;
        }

        public void setMessageID(String MessageID) {
            this.MessageID = MessageID;
        }

        public String getCardCode() {
            return CardCode;
        }

        public void setCardCode(String CardCode) {
            this.CardCode = CardCode;
        }

        public String getCityCode() {
            return CityCode;
        }

        public void setCityCode(String CityCode) {
            this.CityCode = CityCode;
        }

        public Object getCMD() {
            return CMD;
        }

        public void setCMD(Object CMD) {
            this.CMD = CMD;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String UserID) {
            this.UserID = UserID;
        }

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getOperateTime() {
            return OperateTime;
        }

        public void setOperateTime(String OperateTime) {
            this.OperateTime = OperateTime;
        }

        public int getIsRead() {
            return IsRead;
        }

        public void setIsRead(int IsRead) {
            this.IsRead = IsRead;
        }

        public int getMessageType() {
            return MessageType;
        }

        public void setMessageType(int MessageType) {
            this.MessageType = MessageType;
        }

        public String getCityName() {
            return CityName;
        }

        public void setCityName(String CityName) {
            this.CityName = CityName;
        }
    }
}
