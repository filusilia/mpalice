package com.alice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

/**
 * 时间处理类
 */
public class DateUtil {

    /**
     * 日志文件
     */
    static Logger logger = Logger.getLogger(DateUtil.class);

    /**
     * 获取下月日期时间点
     *
     * @param datetime 格式：“dd hh-mm-ss”
     * @return 格式：“yyyy-MM-dd hh-mm-ss”
     */
    public static long getNextMonthTime(String datetime) {
        Calendar c = Calendar.getInstance();
        String month = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-";
        long initialDelay = 0;
        try {
            initialDelay = SimpleDateFormat.getDateTimeInstance().parse(month + datetime).getTime()
                    - System.currentTimeMillis();
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        if (initialDelay < 0) {
            c.add(Calendar.MONTH, 1);
            month = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-";
            try {
                initialDelay = SimpleDateFormat.getDateTimeInstance().parse(month + datetime)
                        .getTime()
                        - System.currentTimeMillis();
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }
        }
        return initialDelay;
    }

    /**
     * 获取下个日期时间点
     *
     * @param datetime 格式：“hh-mm-ss”
     * @return 格式：“yyyy-MM-dd hh-mm-ss”
     */
    public static long getNextDayTime(String datetime) {
        Calendar c = Calendar.getInstance();
        String today = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
                + c.get(Calendar.DAY_OF_MONTH) + " ";
        long initialDelay = 0;
        try {
            initialDelay = SimpleDateFormat.getDateTimeInstance().parse(today + datetime).getTime()
                    - System.currentTimeMillis();
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        if (initialDelay < 0)
            initialDelay += 86400000;

        return initialDelay;
    }

    /**
     * 通过 String 获取日期
     *
     * @param strs 第一个参数 String 类型日期，默认 yyyy-MM-dd 第二个参数 dateformat
     * @return
     */
    public static Date getDateByString(String... strs) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (null == strs)
            return new Date();
        try {
            if (strs.length > 1)
                sdf = new SimpleDateFormat(strs[1]);
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(strs[0]));
            return c.getTime();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 通过 date 返回 string
     *
     * @param date
     * @param strs
     * @return
     */
    public static String getStrDate(Date date, String... strs) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (null != strs)
            sdf = new SimpleDateFormat(strs[0]);
        return sdf.format(date);
    }

    /**
     * 通过时间 long 返回 string
     *
     * @param dateLong
     * @param strs
     * @return
     */
    public static String getStrDate(long dateLong, String... strs) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (null != strs)
            sdf = new SimpleDateFormat(strs[0]);
        return sdf.format(new Date(dateLong));
    }

    /**
     * 两个日期函数比较，计算出月份的差值
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int getSubDate(Date d1, Date d2) {
        Calendar c = Calendar.getInstance();
        c.setTime(d1);
        int year1 = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH);
        c.setTime(d2);
        int year2 = c.get(Calendar.YEAR);
        int month2 = c.get(Calendar.MONTH);
        return 12 * (year1 - year2) + month1 - month2;
    }

    /**
     * 获取当前时间的 yyyy-MM-dd格式的Date
     *
     * @return
     * @throws Exception
     */
    public static Date getCurrentDate() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(new Date());
        return sdf.parse(dateStr);
    }

    /**
     * 获取明天时间，格式yyyy-MM-dd
     *
     * @return Date
     * @throws ParseException
     */
    @SuppressWarnings("static-access")
    public static Date getTomorrowDate() {
        try {
            Date date = new Date();// 取时间
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(calendar.getTime());
            return sdf.parse(dateStr);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取昨天时间，格式yyyy-MM-dd
     *
     * @return Date
     */
    @SuppressWarnings("static-access")
    public static Date getYesterday() {
        try {
            Date date = new Date();// 取时间
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, -1);// 把日期往后增加一天.整数往后推,负数往前移动
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(calendar.getTime());
            return sdf.parse(dateStr);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            System.err.println(getYesterday());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
