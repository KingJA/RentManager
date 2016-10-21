package com.kingja.cardpackage.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/5/6 10:50
 * 修改备注：
 */
public class TimeUtil {

    public static boolean isOneDay(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date lastDate = format.parse(time);
            Date currentDate = new Date();
            long between = currentDate.getTime() - lastDate.getTime();
            if (between < (24 * 60 * 60 * 1000)) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取标准时间格式
     *
     * @return
     */
    public static String getFormatTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }

    public static String getFormatDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date());
    }

    public static boolean compareDate(String startDate, String endDate) {
        if (startDate.compareTo(endDate) > 0) {
            ToastUtil.showToast("结束日期不能小于起始日期");
            return false;
        }
        return true;
    }

    public static boolean compareTime(String startTime, String endTime, String tip) {
        if (startTime.compareTo(endTime) > 0) {
            ToastUtil.showToast(tip);
            return false;
        }
        return true;
    }

    /**
     * Date=>时间字符串
     *
     * @param date
     * @param timeFormat yyyy-MM-dd  yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String Date2String(Date date, String timeFormat) {

        SimpleDateFormat df = new SimpleDateFormat(timeFormat);

        return df.format(date);
    }

    /**
     * 时间字符串=>Date
     *
     * @param strDate
     * @param timeFormat yyyy-MM-dd  yyyy-MM-dd HH:mm:ss
     * @return
     * @throws Exception
     */
    public static Date String2Date(String strDate, String timeFormat) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat(timeFormat);
        return df.parse(strDate);
    }

    public static String get2015Date(long minutes) {
        String result = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long baseSeconds = formatter.parse("2015-01-01 00:00:00").getTime();
            Date newDate = new Date(baseSeconds + minutes * 60 * 1000);
            result = formatter.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 0s-30s刚刚
     * 30s-60s 1分钟前
     * 60s-60m X分钟前
     * 60m-24h X小时前
     * 24h-48h 昨天
     * 48h-30d m月d日
     * 不是今年 显示y年m月d日
     * 2016-8-1 12:15:40
     */

    public static String getTimeTip(String time) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d hh:mm:ss");
        long postTime = 0;
        try {
            postTime = sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long distanceTime = System.currentTimeMillis() - postTime;
        if (distanceTime < 30 * 1000) {
            result = "刚刚";
        } else if (distanceTime >= 30 * 1000 && distanceTime < 60 * 1000) {
            result = "1分钟前";
        } else if (distanceTime >= 60 * 1000 && distanceTime < 60 * 60 * 1000) {
            result = distanceTime / (60 * 1000) + "分钟前";
        } else if (distanceTime >= 60 * 60 * 1000 && distanceTime < 24 * 60 * 60 * 1000) {
            result = distanceTime / (60 * 60 * 1000) + "小时前";
        } else if (distanceTime >= 24 * 60 * 60 * 1000 && distanceTime < 48 * 60 * 60 * 1000) {
            result = "昨天";
        } else if (distanceTime >= 48 * 60 * 60 * 1000) {
            result = Date2String(new Date(postTime), "M月d日");
        }
        if (!Date2String(new Date(postTime), "yyyy").equals(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))) {
            result = Date2String(new Date(postTime), "yyyy年M月d日");
        }
        return result;
    }
}
