package com.tsutsuku.artcollection.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by tsutsuku on 2016/5/29.
 * Description: time relate utils
 */
public class TimeUtils {
    /**
     * Calendar calendar = Calendar.getInstance();//获取当前日历对象 long unixTime =
     * calendar.getTimeInMillis();//获取当前时区下日期时间对应的时间戳 long unixTimeGMT =
     * unixTime - TimeZone.getDefault().getRawOffset();//获取标准格林尼治时间下日期时间对应的时间戳
     * <p/>
     * Date date = new Date();//获取当前日期对象 unixTimeGMT = unixTime =
     * date.getTimeInMillis();//获取当前时区下日期时间对应的时间戳
     * <p/>
     * SimpleDateFormat format = new
     * SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置格式 String dateString =
     * "2010-12-26 03:36:25";//设定具有指定格式的日期字符串 unixTimeGMT = unixTime =
     * format.format(date);//获取当前时区下日期时间对应的时间戳
     */
    //获取时间戳
    public static long getTime() {
        Calendar calendar = Calendar.getInstance();// 获取当前日历对象
        long unixTime = calendar.getTimeInMillis();// 获取当前时区下日期时间对应的时间戳
        return unixTime;
    }

    public static String getTimeInSecond() {
        return Long.toString(getTime() / 1000);
    }

    /**
     * 获取当前日期
     *
     * @return yyyy-MM-dd
     */
    public static String getDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(Calendar.getInstance().getTime());
    }

    // 获取与现在时间的时间差（秒）
    public static int getDurationSecond(String time) {
        int durationSecond = 0;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = df.parse(time);
            durationSecond = (int) (new Date().getTime() - date.getTime()) / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return durationSecond;
    }

    // 获取时间差，
    public static String getDuration(String one, String two) {
        String duration = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1;
        Date date2;
        try {
            date1 = df.parse(one);
            date2 = df.parse(two);
            int l = (int) (date2.getTime() - date1.getTime()) / 1000 / 60;
            if (l > 60) {
                int hr = l / 60;
                int min = l % 60;
                duration = hr + "小时" + min + "分钟";
            } else {
                duration = l + "分钟";
            }
        } catch (Exception e) {
            Log.e("TimeUtils", "getDuration error=" + e);
        }

        return duration;
    }

    // 获取与当前时间差
    public static String getcurDuration(String one) {
        String duration = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1;
        Date date2;
        try {
            date1 = df.parse(one);
            date2 = new Date();
            int l = (int) (date2.getTime() - date1.getTime()) / 1000 / 60;
            if (l > 60) {
                int hr = l / 60;
                int min = l % 60;
                duration = hr + "小时" + min + "分钟";
            } else {
                duration = l + "分钟";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return duration;
    }

    public static String parsePostTime(long time) {
        String timeTemp = "";
        if (time < 60) {
            timeTemp = time + "秒前";
        } else if (time < (60 * 60)) {
            timeTemp = time / 60 + "分钟前";
        } else if (time < (3600 * 24)) {
            timeTemp = time / 3600 + "小时前";
        } else if (time < (60 * 60 * 24 * 30)) {
            timeTemp = time / (3600 * 24) + "天前";
        } else {
            timeTemp = time / (3600 * 24 * 30) + "月前";
        }
        return timeTemp;
    }


    /**
     * 计算相隔天数 输入格式yyyy-MM-dd
     *
     * @param earlyDate 早的日期
     * @param laterDate 迟的日期
     * @return 相隔天数
     */
    public static long betweenDate(String earlyDate, String laterDate) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date beginDate = df.parse(earlyDate);
            Date endDate = df.parse(laterDate);
            return (endDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24);
        } catch (Exception e) {
            Log.e("TimeUtils", "error=" + e);
        }
        return 0;
    }

    /**
     * 获取距今相隔天数
     *
     * @param date 对比的日期
     * @return 相隔天数
     */
    public static long getBetween(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = df.format(new Date());
        return betweenDate(date, curDate);
    }


    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT_DATE_PART = "yyyy-MM-dd";
    private static final String DATE_FORMAT_TIME_PART = "HH:mm:ss";

    public static String getDateString(String time) {
        Date date = getDate(time);
        String dateString = new SimpleDateFormat(DATE_FORMAT_DATE_PART).format(date);

        return dateString;
    }

    private static Date getDate(String time) {
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getCurrentDate();
    }

    private static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * 秒转时分秒
     *
     * @param seconds 输入秒数
     * @return HH:mm:ss 格式数据
     */
    public static String secondsFormat(String seconds) {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(Long.valueOf(seconds) * 1000);
    }

    public static String getCrowTime(String time) {
        long date = Long.parseLong(time);
        long day = date / (1000 * 60 * 60 * 24);
        long hour = (date / (1000 * 60 * 60) - day * 24);
        long min = ((date / (60 * 1000)) - day * 24 * 60 - hour * 60);
        if (day > 0) {
            return day + "天" + hour + "小时";
        } else if (hour > 0) {
            return hour + "小时" + min + "分";
        } else {
            return min + "分";
        }
    }
}
