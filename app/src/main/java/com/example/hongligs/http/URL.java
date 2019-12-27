package com.example.hongligs.http;

public class URL {
    //服务器地址
    public static final String HTTP_DOMAIN = "http://192.168.1.107:8081/";
    //登陆
    public static final String REGISTER = HTTP_DOMAIN + "user/login";
    //获取验证码
    public static final String MESSAGE = HTTP_DOMAIN + "user/getSMSCode";
    // 首页Banner
    public static final String BANNER = HTTP_DOMAIN + "base/getBanner";
   //public static final String BANNER =  "http://honglilongchuang.cn:8888/base/getBanner";

    // 挑选年代
    public static final String XTime = HTTP_DOMAIN + "dicts/getList" ;

    // 下一步
    public static final String Next = HTTP_DOMAIN + "user/selectGender" ;

    //新用户选择圈
    public static final String NEWCHOOSE= HTTP_DOMAIN + "circledic/getCircleTagList" ;

    //新用户选择圈进入下一个页面
    public static final String NEWNEXT= HTTP_DOMAIN + "usertags/save" ;

    //二次筛选圈子
    public static final String SENDCHIOCED= HTTP_DOMAIN + "circledic/regetTagList" ;

    //动态标签Dynamic tbel
   public static final String DYNAMICLABLE= HTTP_DOMAIN + "usertags/getTagsByUid" ;

    //动态列表
    public static final String ListOF= HTTP_DOMAIN + "dynamic/getListBytags" ;

    // 首页推荐
    public static final String Recommend= HTTP_DOMAIN + "dynamic/getListBytags" ;

    //交友扩利
    public static final String DatingEnlargement= HTTP_DOMAIN + "circle/circleList" ;

    //群资料banner
    public static final String GroupBanner= HTTP_DOMAIN + "circle/getCircle" ;

    //树洞
    public static final String TreeHole= HTTP_DOMAIN + "hollow/getList" ;


}
