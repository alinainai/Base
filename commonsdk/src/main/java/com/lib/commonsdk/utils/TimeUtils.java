
package com.lib.commonsdk.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("SpellCheckingInspection")
public class TimeUtils {


    private static final String YYYYMMDD_HHMMSS="yyyy-MM-dd HH:mm:ss";
    private static final String MMDD_HHMMSS="MM-dd HH:mm:ss";
    private static final String YYYYMMDD="yyyy-MM-dd";
    private static final String MMDD="MM-dd";
    private static final String HHMM="HH:mm";
    private static final String MMSS="mm:ss";

    private static final Map<String, SimpleDateFormat> cachedSimpleDateFormat
            = new ConcurrentHashMap<>();

    private static final ThreadLocal<Calendar> cachedCalendar = new ThreadLocal<>();

    private static Calendar Calendar1 = Calendar.getInstance();
    private static Calendar Calendar2 = Calendar.getInstance();

    private static NowProvider sNowProvider = System::currentTimeMillis;


    private TimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 根据输入的年、月、日，转换成毫秒表示的时间
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    private static long getMillis(int year, int month, int day) {
        GregorianCalendar calendar = new GregorianCalendar(year, month, day);
        return calendar.getTimeInMillis();

    }

    /**
     * 根据输入的年、月、日，转换成毫秒表示的时间
     *
     * @param yearString
     * @param monthString
     * @param dayString
     * @return
     */
    private static long getMillis(String yearString, String monthString,
                                  String dayString) {
        int year = Integer.parseInt(yearString);
        int month = Integer.parseInt(monthString) - 1;
        int day = Integer.parseInt(dayString);
        return getMillis(year, month, day);
    }

    /**
     * 获得当前时间的毫秒表示
     */
    public static long getNow() {
        return sNowProvider.now();
    }


    /**
     * 传入毫秒
     *
     * @param millis 毫秒
     * @return "MM-dd"
     */
    public static String getDateWithoutTime(long millis) {
        SimpleDateFormat formatter = getCachedSimpleDateFormat(YYYYMMDD);
        return formatter.format(new Date(millis));
    }

    /**
     *
     *
     * @param millis
     * @return
     */
    public static String getDateAndTime(long millis) {
        SimpleDateFormat formatter = getCachedSimpleDateFormat(YYYYMMDD_HHMMSS);
        return formatter.format(new Date(millis));
    }

    /**
     * 传入毫秒
     *
     * @param millis 毫秒
     * @return "MM-dd HH:mm:ss"
     */
    public static String getDateAndTimeWithoutYear(long millis) {
        SimpleDateFormat formatter = getCachedSimpleDateFormat(MMDD_HHMMSS);
        return formatter.format(new Date(millis));
    }

    /**
     * 传入毫秒
     *
     * @param millis 毫秒
     * @return "MM-dd"
     */
    public static String getMonthAndDay(long millis) {
        SimpleDateFormat formatter = getCachedSimpleDateFormat(MMDD);
        return formatter.format(new Date(millis));
    }

    /**
     * 传入毫秒
     *
     * @param millis 毫秒
     * @return "hh:mm"
     */
    public static String getHourAndMinute(long millis) {
        SimpleDateFormat formatter = getCachedSimpleDateFormat(HHMM);
        return formatter.format(new Date(millis));
    }

    /**
     * 传入毫秒
     *
     * @param millis 毫秒
     * @return "mm:ss"
     */
    public static String getMinuteAdnSecond(long millis) {
        SimpleDateFormat formatter = getCachedSimpleDateFormat(MMSS);
        return formatter.format(new Date(millis));
    }


    private static synchronized SimpleDateFormat getCachedSimpleDateFormat(String pattern) {
        SimpleDateFormat sdf = cachedSimpleDateFormat.get(pattern);
        if (sdf == null) {
            sdf = new SimpleDateFormat(pattern, Locale.getDefault());
            cachedSimpleDateFormat.put(pattern, sdf);
        }
        return sdf;
    }

    private static Calendar getCachedCalendar() {
        Calendar calendar = cachedCalendar.get();
        if (calendar == null) {
            calendar = Calendar.getInstance();
            cachedCalendar.set(calendar);
        }
        return calendar;
    }

    // 根据输入的毫秒数，获得年份
    public static int getYear(long millis) {
        Calendar calendar = getCachedCalendar();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.YEAR);

    }

    // 根据输入的毫秒数，获得月份
    public static int getMonth(long millis) {
        Calendar calendar = getCachedCalendar();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.MONTH) + 1;

    }

    // 根据输入的毫秒数，获得日期
    public static int getDay(long millis) {
        Calendar calendar = getCachedCalendar();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.DATE);

    }

    // 根据输入的毫秒数，获得小时
    public static int getHour(long millis) {
        Calendar calendar = getCachedCalendar();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.HOUR_OF_DAY);

    }

    // 获得今天日期
    private static long getTodayTimestamp() {
        return getNow();

    }

    // 获得明天日期
    private static long getTomorrowTimestamp() {
        // 86400000为一天的毫秒数
        return getNow() + 86400000L;

    }

    // 获得后天日期
    private static long getAfterTomorrowTimestamp() {
        return getNow() + 86400000L + 86400000L;
    }

    // 获得昨天日期
    private static long getYesterdayTimestamp() {
        return getNow() - 86400000L;
    }

    // 获得前天日期
    private static long getBeforeYesterdayTimestamp() {
        return getNow() - 86400000L - 86400000L;
    }


    /**
     * 将long型的时间值,按指定模式(例如：yyyy-MM-dd HH:mm:ss)显示
     *
     * @param longTime 时间值
     * @param pattern  模式
     */
    public static String convertLongToDataTime(long longTime, String pattern) {
        String ret = "";
        try {
            // 外部传入的pattern类型不固定可能会撑爆做缓存的map
            SimpleDateFormat format = getCachedSimpleDateFormat(pattern);
            Date date = new Date(longTime);
            ret = format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return ret;
    }

    /**
     * 字符串格式转化为时间戳
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static long convertDateTimeToLong(String dateStr, String pattern) {
        if (dateStr == null) {
            return 0;
        }
        // 外部传入的pattern类型不固定可能会撑爆做缓存的map
        SimpleDateFormat format = getCachedSimpleDateFormat(pattern);
        try {
            Date date = format.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return days + " days " + hours + " hours " + minutes + " minutes "
                + seconds + " seconds ";
    }


    public static boolean isSameDay(Date date1, Date date2) {
        Calendar1.setTime(date1);
        Calendar2.setTime(date2);
        return Calendar1.get(Calendar.YEAR) == Calendar2.get(Calendar.YEAR) &&
                Calendar1.get(Calendar.DAY_OF_YEAR) == Calendar2.get(Calendar.DAY_OF_YEAR);
    }

    public static boolean isSameDay(long timestamp1, long timestamp2) {
        Calendar1.setTimeInMillis(timestamp1);
        Calendar2.setTimeInMillis(timestamp2);
        return Calendar1.get(Calendar.YEAR) == Calendar2.get(Calendar.YEAR) &&
                Calendar1.get(Calendar.DAY_OF_YEAR) == Calendar2.get(Calendar.DAY_OF_YEAR);
    }

    interface NowProvider {
        long now();
    }
}
