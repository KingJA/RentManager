package com.kingja.cardpackage.entiy;

/**
 * Description：TODO
 * Create Time：2016/8/5 10:31
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class User_Login {


    /**
     * ResultCode : 0
     * ResultText : 登录成功
     * DataTypeCode : USER_LOGIN
     * TaskID : 1
     * Content : {"UserID":"1C7DAA93DBA24DC98BBB586B29392243","Name":"金友发","Phone":"13456058817","IdentityCard":"33032719840220097X","Token":"B6B5347CF62A414AA7D223018F426660"}
     * StableVersion : 1.26
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private int TaskID;
    /**
     * UserID : 1C7DAA93DBA24DC98BBB586B29392243
     * Name : 金友发
     * Phone : 13456058817
     * IdentityCard : 33032719840220097X
     * Token : B6B5347CF62A414AA7D223018F426660
     */

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
        private String UserID;
        private String Name;
        private String Phone;
        private String IdentityCard;
        private String Token;

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String UserID) {
            this.UserID = UserID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public String getIdentityCard() {
            return IdentityCard;
        }

        public void setIdentityCard(String IdentityCard) {
            this.IdentityCard = IdentityCard;
        }

        public String getToken() {
            return Token;
        }

        public void setToken(String Token) {
            this.Token = Token;
        }
    }
}
