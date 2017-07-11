package com.kingja.cardpackage.util;

/**
 * Description：TODO
 * Create Time：2016/8/4 16:53
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Constants {
    /**
     * Webservice参数
     */
//    public static final String WEBSERVER_URL = "http://zafkapp.test.iotone.cn:12026/rentalestate.asmx";//
// WebServices访问地址，测试

//    public static final String WEBSERVER_URL = "http://122.228.188.210:20043/WebCardHolder.asmx";// WebServices访问地址
    public static final String WEBSERVER_URL = com.tdr.wisdome.util.Constants.WEBSERVER_URL;// WebServices访问地址
    public static final String WEBSERVER_NAMESPACE = "http://tempuri.org/";// 命名空间
    public static final String WEBSERVER_REREQUEST = "CardHolder";
    public static final String APPLICATION_NAME = "CardPackage";
    public static final int SOFTTYPE = 5;
    /*  ============================================  卡类型  ============================================*/

    /*我的住房*/
    public static final String CARD_TYPE_HOUSE = "1001";
    /*我家出租屋*/
    public static final String CARD_TYPE_RENT = "1002";
    /*我的店*/
    public static final String CARD_TYPE_SHOP = "1004";
    /*出租房代管*/
    public static final String CARD_TYPE_AGENT = "1007";
    /*出租房中介*/
    public static final String CARD_TYPE_INTERMEDIARY = "1008";
    /*民警查询*/
    public static final String CARD_TYPE_POLICE_SEARCH = "3001";

    public static final String CARD_TYPE_EMPTY = "";
    public static final int ROLE_INTERMEDIARY = 4;
    public static final int ROLE_RENT = 2;

    /*  ============================================  接口方法  ============================================*/

    /*我家出租屋列表*/
    public static final String ChuZuWu_List = "ChuZuWu_List";
    /*我的店列表*/
    public static final String ShangPu_List = "ShangPu_List";
    /*出租房代管列表*/
    public static final String ChuZuWu_ListByManager = "ChuZuWu_ListByManager";
    /*我的住房列表*/
    public static final String ChuZuWu_ListByRenter = "ChuZuWu_ListByRenter";
    /*人员管理列表*/
    public static final String ChuZuWu_MenPaiAuthorizationList = "ChuZuWu_MenPaiAuthorizationList";
    /*查询房间信息*/
    public static final String ChuZuWu_RoomInfo = "ChuZuWu_RoomInfo";
    /*修改房间信息*/
    public static final String ChuZuWu_ModifyRoom = "ChuZuWu_ModifyRoom";
    /*设备信息*/
    public static final String ChuZuWu_DeviceLists = "ChuZuWu_DeviceLists";
    /*申报列表*/
    public static final String ChuZuWu_LKSelfReportingList = "ChuZuWu_LKSelfReportingList";
    /*申报离开*/
    public static final String ChuZuWu_LKSelfReportingOut = "ChuZuWu_LKSelfReportingOut";
    /*自主申报*/
    public static final String ChuZuWu_LKSelfReportingIn = "ChuZuWu_LKSelfReportingIn";
    /*手动撤布防*/
    public static final String ChuZuWu_SetDeployStatus = "ChuZuWu_SetDeployStatus";
    /*卡包登录*/
    public static final String User_LogInForKaBao = "User_LogInForKaBao";
    /*店铺撤布防*/
    public static final String ShangPu_SetDeployStatus = "ShangPu_SetDeployStatus";
    /*店铺员工管理*/
    public static final String ShangPu_EmployeeList = "ShangPu_EmployeeList";
    /*店铺移除员工*/
    public static final String ShangPu_DismissEmployee = "ShangPu_DismissEmployee";
    /*店铺信息*/
    public static final String ShangPu_ViewInfo = "ShangPu_ViewInfo";
    /*修改店铺信息*/
    public static final String ShangPu_Modify = "ShangPu_Modify";
    /*店铺二维码*/
    public static final String ShangPu_AddEmployee = "ShangPu_AddEmployee";
    /*店铺设备列表*/
    public static final String ShangPu_DeviceLists = "ShangPu_DeviceLists";
    /*店铺设备移除*/
    public static final String Common_RemoveDevice = "Common_RemoveDevice";
    /*店铺设备绑定*/
    public static final String Common_AddDevice = "Common_AddDevice";
    /*加入店铺*/
    public static final String ShangPu_JoinShangPu = "ShangPu_JoinShangPu";
    /*添加店铺*/
    public static final String ShangPu_Add = "ShangPu_Add";
    /*标准地址关键字检索*/
    public static final String Basic_StandardAddressCodeByKey = "Basic_StandardAddressCodeByKey";
    /*查看三实有出租房信息*/
    public static final String ChuZuWu_GetSSYByStandAddressCode = "ChuZuWu_GetSSYByStandAddressCode";
    /*修改上下班时间*/
    public static final String ShangPu_WorkTimeModify = "ShangPu_WorkTimeModify";
    /*出租房预警信息*/
    public static final String ChuZuWu_MessageList = "ChuZuWu_MessageList";
    /*商铺预警信息*/
    public static final String ShangPu_MessageList = "ShangPu_MessageList";
    /*预警详细信息*/
    public static final String Message_InquireDetailOfMessage = "Message_InquireDetailOfMessage";
    /*出租屋信息*/
    public static final String ChuZuWu_Info = "ChuZuWu_Info";
    /*管理员列表*/
    public static final String ChuZuWu_AdminList = "ChuZuWu_AdminList";
    /*出租房二维码*/
    public static final String ChuZuWu_AddAdmin = "ChuZuWu_AddAdmin";
    /*加入管理员*/
    public static final String ChuZuWu_JoinManage = "ChuZuWu_JoinManage";
    /*删除管理员*/
    public static final String ChuZuWu_RemoveAdmin = "ChuZuWu_RemoveAdmin";
    /*查看派出所*/
    public static final String Basic_PaiChuSuoOfStandardAddress = "Basic_PaiChuSuoOfStandardAddress";
    /*员工商铺列表*/
    public static final String ShangPu_ListByEmp = "ShangPu_ListByEmp";
    /*员工删除店铺*/
    public static final String ShangPu_UserOut = "ShangPu_UserOut";
    /*转让店铺*/
    public static final String ShangPu_Transfer = "ShangPu_Transfer";
    /*接收店铺*/
    public static final String ShangPu_TakeOver = "ShangPu_TakeOver";
    /*我的消息*/
    public static final String GetUserMessage = "GetUserMessage";
    /*我的消息-全读*/
    public static final String SetUserMessageAll = "SetUserMessageAll";
    /*全部应用*/
    public static final String Application_List = "Application_List";
    /*民警查询*/
    public static final String Police_Policemeninfo = "Police_Policemeninfo";
    /*中介查询*/
    public static final String Agency_List = "Agency_List";
    /*首页卡片*/
    public static final String User_HomePageApplication = "User_HomePageApplication";

}
