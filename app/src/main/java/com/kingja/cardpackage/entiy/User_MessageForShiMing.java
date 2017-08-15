package com.kingja.cardpackage.entiy;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/7/25 10:19
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class User_MessageForShiMing {

    /**
     * ResultCode : 0
     * ResultText : 处理成功
     * DataTypeCode : User_MessageForShiMing
     * TaskID : 1
     * Content : {"UnReadCount":2,"MessageList":[{"MessageID":"5a2024fb49c1472285cc8b7ef26684e5",
     * "Message":"九点半酒吧（道南路分店）i,智慧栅栏(社区版)（602）,触发报警,请确认安全","MessageType":2,"IsRead":0,"CreateTime":"2016/12/2
     * 13:21:48","MessageTitle":""},{"MessageID":"ce36d62bdc3e4ae0ac677bb5ebb69fff","Message":"","MessageType":2,
     * "IsRead":1,"CreateTime":"2016/12/2 9:15:40","MessageTitle":""},
     * {"MessageID":"3102a1f2398a4d2d81b974245a737e8a","Message":"","MessageType":2,"IsRead":1,
     * "CreateTime":"2016/12/2 9:15:40","MessageTitle":""},{"MessageID":"46ddcf44d7d54d6b8365c48ffa442efd",
     * "Message":"","MessageType":2,"IsRead":1,"CreateTime":"2016/12/2 9:15:39","MessageTitle":""},
     * {"MessageID":"d66bcf6030374b119e41bd6a2fa97bed","Message":"","MessageType":2,"IsRead":1,
     * "CreateTime":"2016/12/2 9:15:39","MessageTitle":""},{"MessageID":"fbdca522d3574720831a5a9a5a516237",
     * "Message":"","MessageType":2,"IsRead":1,"CreateTime":"2016/12/2 9:06:56","MessageTitle":""},
     * {"MessageID":"2f8bfc5698594c66ad6dfc382d9ea873","Message":"","MessageType":2,"IsRead":0,
     * "CreateTime":"2016/12/2 9:06:44","MessageTitle":""}]}
     * StableVersion : 2.5.9
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private int TaskID;
    private ContentBean Content;
    private String StableVersion;

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

    public int getTaskID() {
        return TaskID;
    }

    public void setTaskID(int TaskID) {
        this.TaskID = TaskID;
    }

    public ContentBean getContent() {
        return Content;
    }

    public void setContent(ContentBean Content) {
        this.Content = Content;
    }

    public String getStableVersion() {
        return StableVersion;
    }

    public void setStableVersion(String StableVersion) {
        this.StableVersion = StableVersion;
    }

    public static class ContentBean {
        /**
         * UnReadCount : 2
         * MessageList : [{"MessageID":"5a2024fb49c1472285cc8b7ef26684e5","Message":"九点半酒吧（道南路分店）i,智慧栅栏(社区版)（602）,
         * 触发报警,请确认安全","MessageType":2,"IsRead":0,"CreateTime":"2016/12/2 13:21:48","MessageTitle":""},
         * {"MessageID":"ce36d62bdc3e4ae0ac677bb5ebb69fff","Message":"","MessageType":2,"IsRead":1,
         * "CreateTime":"2016/12/2 9:15:40","MessageTitle":""},{"MessageID":"3102a1f2398a4d2d81b974245a737e8a",
         * "Message":"","MessageType":2,"IsRead":1,"CreateTime":"2016/12/2 9:15:40","MessageTitle":""},
         * {"MessageID":"46ddcf44d7d54d6b8365c48ffa442efd","Message":"","MessageType":2,"IsRead":1,
         * "CreateTime":"2016/12/2 9:15:39","MessageTitle":""},{"MessageID":"d66bcf6030374b119e41bd6a2fa97bed",
         * "Message":"","MessageType":2,"IsRead":1,"CreateTime":"2016/12/2 9:15:39","MessageTitle":""},
         * {"MessageID":"fbdca522d3574720831a5a9a5a516237","Message":"","MessageType":2,"IsRead":1,
         * "CreateTime":"2016/12/2 9:06:56","MessageTitle":""},{"MessageID":"2f8bfc5698594c66ad6dfc382d9ea873",
         * "Message":"","MessageType":2,"IsRead":0,"CreateTime":"2016/12/2 9:06:44","MessageTitle":""}]
         */

        private int UnReadCount;
        private List<MessageListBean> MessageList;

        public int getUnReadCount() {
            return UnReadCount;
        }

        public void setUnReadCount(int UnReadCount) {
            this.UnReadCount = UnReadCount;
        }

        public List<MessageListBean> getMessageList() {
            return MessageList;
        }

        public void setMessageList(List<MessageListBean> MessageList) {
            this.MessageList = MessageList;
        }

        public static class MessageListBean {
            /**
             * MessageID : 5a2024fb49c1472285cc8b7ef26684e5
             * Message : 九点半酒吧（道南路分店）i,智慧栅栏(社区版)（602）,触发报警,请确认安全
             * MessageType : 2
             * IsRead : 0
             * CreateTime : 2016/12/2 13:21:48
             * MessageTitle :
             */

            private String MessageID;
            private String Message;
            private int MessageType;
            private int IsRead;
            private String CreateTime;
            private String MessageTitle;

            public String getMessageID() {
                return MessageID;
            }

            public void setMessageID(String MessageID) {
                this.MessageID = MessageID;
            }

            public String getMessage() {
                return Message;
            }

            public void setMessage(String Message) {
                this.Message = Message;
            }

            public int getMessageType() {
                return MessageType;
            }

            public void setMessageType(int MessageType) {
                this.MessageType = MessageType;
            }

            public int getIsRead() {
                return IsRead;
            }

            public void setIsRead(int IsRead) {
                this.IsRead = IsRead;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }

            public String getMessageTitle() {
                return MessageTitle;
            }

            public void setMessageTitle(String MessageTitle) {
                this.MessageTitle = MessageTitle;
            }
        }
    }
}
