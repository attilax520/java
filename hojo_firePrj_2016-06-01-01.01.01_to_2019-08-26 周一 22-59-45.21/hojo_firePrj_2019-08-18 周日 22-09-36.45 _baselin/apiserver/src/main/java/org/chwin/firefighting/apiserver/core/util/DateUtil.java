package org.chwin.firefighting.apiserver.core.util;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liming on 2017/9/18.
 */
public class DateUtil {
    private static final String sdf0reg = "^\\d{2,4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,}$";
    private static final String sdf1reg = "^\\d{2,4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$";
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String sdf2reg = "^\\d{2,4}\\-\\d{1,2}\\-\\d{1,2}$";
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    private static final String sdf3reg = "^\\d{2,4}\\/\\d{1,2}\\/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$";
    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final String sdf4reg = "^\\d{2,4}\\/\\d{1,2}\\/\\d{1,2}$";
    private static final SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy/MM/dd");
    public static final String pattern1 = "yyyy-MM-dd";
    public static final String pattern2 = "yyyy-MM-dd HH:mm:ss";
    public static final String pattern3 = "yyyy/MM/dd";
    public static final String pattern4 = "yyyy/MM/dd HH:mm:ss";
    public static final String pattern5 = "yyMMddHHmmss";
    //public static final String pattern5 = "yyMMddHHmmss";
    public static final String pattern6 = "HH:mm";
    public static final String pattern7 = "yyyyMMddHHmmss";
    public static final String pattern8 = "yyyyMMdd";

    public static final String yMD = "2000-01-01 ";

//    public static void main(String[] args) {
//
//    }
    public  static String getDateStr4file()
    {
        Date date = new Date(); //获取当前的系统时间。
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss") ; //使用了默认的格式创建了一个日期格式化对象。
         String time = dateFormat.format(date); //可以把日期转换转指定格式的字符串
return time;
    }
    public static Date parse(String str) {
        Date date = null;
        Pattern p0 = Pattern.compile(sdf0reg);
        Matcher m0 = p0.matcher(str);
        Pattern p1 = Pattern.compile(sdf1reg);
        Matcher m1 = p1.matcher(str);
        Pattern p2 = Pattern.compile(sdf2reg);
        Matcher m2 = p2.matcher(str);
        Pattern p3 = Pattern.compile(sdf3reg);
        Matcher m3 = p3.matcher(str);
        Pattern p4 = Pattern.compile(sdf4reg);
        Matcher m4 = p4.matcher(str);
        try {
            if (m0.matches()) {
                date = sdf1.parse(str);
            }else if (m1.matches()) {
                date = sdf1.parse(str);
            } else if (m2.matches()) {
                date = sdf2.parse(str);
            } else if (m3.matches()) {
                date = sdf3.parse(str);
            } else if (m4.matches()){
                date = sdf4.parse(str);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date parseString(String str,String pattern) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(str);
    }

    public static String format(Date date) {
        return format(date, pattern2);
    }

    public static String format(Date date, String pattern) {
        if (date == null) return "";
        if (StringUtils.isBlank(pattern)) pattern = pattern2;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Long getTime(Date date) {
        if (date == null) date = new Date();
        return date.getTime();
    }


    /**
     * 日期加天数
     *
     * @param date 日期
     * @param day  天数
     * @return 日期
     */
    public static Date addDay(Date date, int day) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 日期加天数
     *
     * @param date 日期
     * @return 日期
     */
    public static String addMinutes(String date, int minutes) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parse(yMD + date));
        calendar.add(Calendar.MINUTE, minutes);
        return format(calendar.getTime(), "HH:mm:ss");
    }

    /**
     * 日期加分钟数
     *
     * @param fullDate 日期
     * @param minutes  分钟
     * @return 日期
     */
    public static String addMinutesFullDate(String fullDate,int minutes) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parse(fullDate));
        calendar.add(Calendar.MINUTE, minutes);
        return format(calendar.getTime());
    }

    public static String calcParkTimes(Date begin, Date end) {
        if (begin == null || end == null) {
            return null;
        }
        long times = (end.getTime() - begin.getTime()) / 1000;
        StringBuilder sb = new StringBuilder();
        long days = times / (24 * 60 * 60);
        sb.append(days == 0 ? "" : days + "天");
        long hours = (times - (days * 24 * 60 * 60)) / (60 * 60);
        sb.append(hours == 0 ? "" : hours + "小时");
        long minutes = (times - (days * 24 * 60 * 60 + hours * 60 * 60)) / 60;
        sb.append(minutes == 0 ? "" : minutes + "分钟");
        return sb.toString();
    }

    public static String getFirstDayOfNextMonth(Date date){
        //获取前月的第一天
        Calendar cal = Calendar.getInstance();//获取当前日期
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        String firstDay = sdf2.format(cal.getTime());
        return firstDay;
    }



    /**
     * 得到两个日期之间相差的天数
     *
     * @param newDate 大的日期
     * @param oldDate 小的日期
     * @return newDate-oldDate相差的天数
     */
    public static int daysBetweenDates(Date newDate, Date oldDate) {
        int days = 0;
        Calendar calo = Calendar.getInstance();
        Calendar caln = Calendar.getInstance();
        calo.setTime(oldDate);
        caln.setTime(newDate);
        int oday = calo.get(Calendar.DAY_OF_YEAR);
        int nyear = caln.get(Calendar.YEAR);
        int oyear = calo.get(Calendar.YEAR);
        while (nyear > oyear) {
            calo.set(Calendar.MONTH, 11);
            calo.set(Calendar.DATE, 31);
            days = days + calo.get(Calendar.DAY_OF_YEAR);
            oyear = oyear + 1;
            calo.set(Calendar.YEAR, oyear);
        }
        int nday = caln.get(Calendar.DAY_OF_YEAR);
        days = days + nday - oday;

        return days;
    }

    public static boolean compareDate(Date start, Date end) {
        return start.before(end);
    }



    /**
     * Returns a Date set to the first possible millisecond of the day, just
     * after midnight. If a null day is passed in, a new Date is created.
     * midnight (00m 00h 00s)
     */
    public static Date getStartOfDay(Date day) {
        return getStartOfDay(day, Calendar.getInstance());
    }

    /**
     * Returns a Date set to the first possible millisecond of the day, just
     * after midnight. If a null day is passed in, a new Date is created.
     * midnight (00m 00h 00s)
     */
    public static Date getStartOfDay(Date day, Calendar cal) {
        if (day == null)
            day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    /**
     * Returns a Date set to the last possible millisecond of the day, just
     * before midnight. If a null day is passed in, a new Date is created.
     * midnight (00m 00h 00s)
     */
    public static Date getEndOfDay(Date day) {
        return getEndOfDay(day, Calendar.getInstance());
    }

    public static Date getEndOfDay(Date day, Calendar cal) {
        if (day == null)
            day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    /**
     * 当前星期几
     * 周一 - 周五 返回 0
     * 周六周日 返回 1
     *
     * @param date
     * @return
     */
    public static int getDayType(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK);//1:周日,2：周一,3:周二,4:周三,5:周四,6:周五:7:周六
        if (week == 1 || week == 7) {
            week = 1;
        } else {
            week = 0;
        }
        return week;
    }

    /**
     * false: a after b
     * true：a before b
     *
     * @param a
     * @param b
     * @return
     */
    public static long getSecondOfDate(String a, String b,boolean isNextDay) {
        if (StringUtils.isEmpty(a) || StringUtils.isEmpty(b)) {
            return 0;
        }
        String yMD = "2000-01-01 ";
        String yMDNextDay = "2000-01-02 ";
        b = isNextDay ? yMDNextDay + b : yMD + b;
        return (parse(b).getTime() - parse(yMD + a).getTime()) / 1000;
    }

    /**
     * 是否在范围区间
     *
     * @param startTime 08:00
     * @param endTime   18:00
     * @param reqTime 请求时间
     * @return true or false
     */
    public static boolean isRange(String startTime, String endTime,Date reqTime) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern6);
        Date startDate = null;
        try {
            startDate = formatter.parse(startTime);
            Date endDate = formatter.parse(endTime);
            Date currentTime = new Date();
            if(reqTime!=null){
                currentTime = reqTime;
            }
            String dateString = formatter.format(currentTime);
            Date currDate = formatter.parse(dateString);
            if (startDate.getTime() > endDate.getTime()) {
                endDate = addDay(endDate, 1);
            }
            if (startDate.getTime() > currDate.getTime()) {
                currDate = addDay(currDate, 1);
            }
            return isContain(currDate.getTime(), startDate.getTime(), endDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 时间是否包含
     * @param currDate 当前时间
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return true or false
     */
    private static boolean isContain(long currDate, long startDate, long endDate) {
        if (currDate >= startDate && currDate < endDate) {
            return true;
        }
        return false;
    }

    /**
     * 获取两个时间相差秒数
     * (time1 - time2)/1000
     * @param time1
     * @param time2
     * @return
     */
    public static long getTwoTimeDiffSecond(Date time1, Date time2) {
        long microsecond= time1.getTime()-time2.getTime();
        return microsecond/1000;
    }
    /**
     * 获取当前系统时间
     *
     * @return
     */
    public static Timestamp getCurrentDateTime() {
        return new Timestamp(System.currentTimeMillis());
    }
    /**
     * timestamp 转 Date
     * @param time
     * @return
     */
    public static Date timestamp2Date(Timestamp time) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(time);
        return calendar.getTime();
    }
    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        System.out.println(getDateStr4file());
        try {
            System.out.println(Encryption.encryptPWD("admin3a92"));
//            Timestamp time=getCurrentDateTime();
//            Date date=timestamp2Date(time);
//            System.out.println(DateUtil.format(DateUtil.parse("2018-01-03 15:20:31.012")));
            Date expiredDate=DateUtil.parse("2018-01-22 9:00:00.012");
            System.out.println(addMinutesFullDate("2018-01-22 9:00:00",30));
//            long timeDiffSecond=getTwoTimeDiffSecond(expiredDate,new Date());
            System.out.println(format(expiredDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
