package com.kingja.cardpackage.util;

import com.tdr.wisdome.R;

/**
 * Description:TODO
 * Create Time:2017/7/6 10:52
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ResUtil {
    public static final String[] cardNames={"房东申报","中介申报","物业申报","企业申报","委托申报","我的住房",
            "手环申领","电动车预登记","防盗芯片更新","房源登记",
            "民警查询","预约办证","在线咨询","线索举报","网上通报","防范宣传",};

    public static int getCardRes(String cardCode) {
        int result = R.drawable.card_1001;
        switch (cardCode) {
            case "1001"://房东申报
                result = R.drawable.card_1001;
                break;
            case "1002"://中介申报
                result = R.drawable.card_1002;
                break;
            case "1003"://物业申报
                result = R.drawable.card_1003;
                break;
            case "1004"://企业申报
                result = R.drawable.card_1004;
                break;
            case "1005"://委托申报
                result = R.drawable.card_1005;
                break;
            case "1006"://我的住房
                result = R.drawable.card_1006;
                break;
            case "2001"://手环申领
                result = R.drawable.card_2001;
                break;
            case "2002"://电动车预登记
                result = R.drawable.card_2002;
                break;
            case "2003"://防盗芯片更新
                result = R.drawable.card_2003;
                break;
            case "2004"://房源登记
                result = R.drawable.card_2004;
                break;
            case "3001"://民警查询
                result = R.drawable.card_3001;
                break;
            case "3002"://预约办证
                result = R.drawable.card_3002;
                break;
            case "3003"://在线咨询
                result = R.drawable.card_3003;
                break;
            case "3004"://线索举报
                result = R.drawable.card_3004;
                break;
            case "3005"://网上通报
                result = R.drawable.card_3005;
                break;
            case "3006"://防范宣传
                result = R.drawable.card_3006;
                break;

        }
        return result;
    }
}
