package xyz.worldzhile.util;

import org.springframework.context.i18n.LocaleContextHolder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Date Utility Class This is used to convert Strings to Dates and Timestamps
 * 格式约定
 * 字母  日期或时间元素 表示  示例  
 * G  Era 标志符  Text  AD  
 * y  年  Year  1996; 96 
 * M  年中的月份  Month  July; Jul; 07 
 * w  年中的周数  Number  27 
 * W  月份中的周数  Number  2 
 * D  年中的天数  Number  189 
 * d  月份中的天数  Number  10 
 * F  月份中的星期  Number  2 
 * a  Am/pm 标记  Text  PM  
 * H  一天中的小时数（0-23）  Number  0 
 * k  一天中的小时数（1-24）  Number  24 
 * K  am/pm 中的小时数（0-11）  Number  0 
 * h  am/pm 中的小时数（1-12）  Number  12 
 * m  小时中的分钟数  Number  30 
 * s  分钟中的秒数  Number  55 
 * S  毫秒数  Number  978 
 * z  时区  General time zone  Pacific Standard Time; PST; GMT-08:00 
 * Z  时区  RFC 822 time zone  -0800 

 * StandardDate : [String][yyyy-MM-dd] 2015-01-05
 * StandardTime : [String][HH:mm:ss]   20:39:26
 * Standard     : [String][yyyy-MM-dd HH:mm:ss] 2015-01-05 20:39:26
 * <p>
 * <a href="DateUtil.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:xia_chaojun@newautovideo.com">chaojun xia</a> Minutes
 *         should be mm not MM (MM is month).
 * @version $Revision: 1.0.0.1 $ $Date: 2006/08/30 13:59:59 $
 */
public class DateUtil {

    private static String defaultDatePattern = null;

    private static String timePattern = "HH:mm";

    /**
     * Return default datePattern (MM/dd/yyyy)
     * 
     * @return a string representing the date pattern on the UI
     */
    public static synchronized String getDatePattern() {
        Locale locale = LocaleContextHolder.getLocale();
        try {
            /* extract default date pattern from application context */
            defaultDatePattern = ResourceBundle.getBundle("ApplicationResources", locale).getString("date.format");
        } catch (MissingResourceException mse) {
            defaultDatePattern = "MM/dd/yyyy";
        }

        return defaultDatePattern;
    }

    /**
     * Return default datetimePattern (MM/dd/yyyy HH:mm:ss.S)
     * 
     * @return a string representing the datetime pattern on the UI
     */
    public static String getDateTimePattern() {
        return DateUtil.getDatePattern() + " HH:mm:ss.S";
    }

    /**
     * This method attempts to convert an Oracle-formatted date in the form
     * dd-MMM-yyyy to mm/dd/yyyy.
     * 
     * @param aDate
     *            date from database as a string
     * @return formatted string for the ui
     */
    public static final String getDate(Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";
        if (aDate != null) {
            df = new SimpleDateFormat(getDatePattern());
            returnValue = df.format(aDate);
        }
        return returnValue;
    }

    /**
     * This method generates a string representation of a date/time in the
     * format you specify on input
     * 
     * @param aMask
     *            the date pattern the string is in
     * @param strDate
     *            a string representation of a date
     * @return a converted Date object
     * @see java.text.SimpleDateFormat
     * @throws ParseException
     */
    public static final Date convertStringToDate(String aMask, String strDate) throws ParseException {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);
        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }
        return (date);
    }
    
    public static Date convertXMLGregorianCalendar(XMLGregorianCalendar xmlcal) {
        GregorianCalendar grecal = xmlcal.toGregorianCalendar();
        return grecal.getTime();
    }

    public static XMLGregorianCalendar getXMLGregorianCalendar() {

        try {
            DatatypeFactory dtf = DatatypeFactory.newInstance();
            GregorianCalendar gcal = (GregorianCalendar) GregorianCalendar.getInstance();
            return dtf.newXMLGregorianCalendar(gcal);
        } catch (DatatypeConfigurationException e) {
            return null;
        }
    }

    public static XMLGregorianCalendar getXMLGregorianCalendarDate(Date date) throws DatatypeConfigurationException {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        XMLGregorianCalendar xmlGdate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        return xmlGdate;
    }

    public static long getTimeAlong(Date before, Date after) {
        return after.getTime() - before.getTime();
    }

    /**
     * This method returns the current date time in the format: MM/dd/yyyy HH:MM
     * a
     * 
     * @param theTime
     *            the current time
     * @return the current date/time
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(timePattern, theTime);
    }

    /**
     * This method returns the current date in the format: MM/dd/yyyy
     * 
     * @return the current date
     * @throws ParseException
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));

        return cal;
    }

    /**
     * 最通用的时间方式
     * 
     * 
     * @param type
     *            :样式,如:"yyyy-MM-dd HH:mm:ss.SSS"
     * 
     */
    public static String getDateTime(String type) {
        Date aDate = DateUtil.currentSQLDate();

        SimpleDateFormat df = null;
        String returnValue = "";
        if (aDate != null) {
            df = new SimpleDateFormat(type);
            returnValue = df.format(aDate);
        }
        return (returnValue);

    }
    
    /**
     * This method generates a string representation of a date's date/time in
     * the format you specify on input
     * 
     * @param aMask
     *            the date pattern the string is in
     * @param aDate
     *            a date object
     * @return a formatted string representation of the date
     * 
     * @see java.text.SimpleDateFormat
     */
    public static final String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";
        if (aDate != null) {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }
        return (returnValue);
    }
    
    /**
     * This method generates a string representation of a date based on the
     * System Property 'dateFormat' in the format you specify on input
     * 
     * @param aDate
     *            A date to convert
     * @return a string representation of the date
     */
    public static final String convertDateToString(Date aDate) {
        return getDateTime(getDatePattern(), aDate);
    }

    /**
     * This method converts a String to a date using the datePattern
     * 
     * @param strDate
     *            the date to convert (in format MM/dd/yyyy)
     * @return a date object
     * 
     * @throws ParseException
     */
    public static Date convertStringToDate(String strDate) throws ParseException {
        Date aDate = null;
        try {
            aDate = convertStringToDate(getDatePattern(), strDate);
        } catch (ParseException pe) {
            pe.printStackTrace();
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }
        return aDate;
    }

    public static java.sql.Timestamp currentSQLTimestamp() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }

    public static synchronized java.sql.Date currentSQLDate() {
        return new java.sql.Date(System.currentTimeMillis());
    }

    public static java.sql.Date getSQLDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static java.sql.Timestamp getSQLTimestamp(java.util.Date date) {
        return new java.sql.Timestamp(date.getTime());
    }

    public static java.util.Date toDate(String strValue) {
        SimpleDateFormat fmt = null;
        if (strValue.indexOf('.') > 0) {
            fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA);// 2005-01-01
        } else if (strValue.indexOf(':') > 0) {
            fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// 2005-01-01
                                                                            // 10:10:10.100
        } else if (strValue.indexOf('-') > 0) {
            fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);// 2005-01-01
        }
        try {
            return fmt.parse(strValue);
        } catch (Exception ex) {
            return new Date();// 返回1970-01-01 00:00:00
        }
    }

    public static java.sql.Timestamp toSQLTimestamp(String strValue) {
        return (new java.sql.Timestamp(toDate(strValue).getTime()));
    }

    public static String getSQLDateTimeStr(java.util.Date date) {
        java.sql.Date sql_date = new java.sql.Date(date.getTime());
        java.sql.Time sql_time = new java.sql.Time(date.getTime());
        return sql_date + " " + sql_time;
    }

    public static boolean comparableBefore(String strValue01, String strValue02) {
        return toSQLTimestamp(strValue01).before(toSQLTimestamp(strValue02));
    }

    public static boolean comparableAfter(String strValue01, String strValue02) {
        return toSQLTimestamp(strValue01).after(toSQLTimestamp(strValue02));
    }

    // 根据字符串返回该字符串对应的时间类型"00-00-00 00:00:00"
    public static String getbegintime(String begintime) {
        return getBegintime(begintime);
    }
    public static String getBegintime(String begintime) {
        if (begintime != null && begintime.length() == 8) {
            String yearString = begintime.substring(0, 4);
            String monthString = begintime.substring(4, 6);
            String dateString = begintime.substring(6);
            begintime = yearString + "-" + monthString + "-" + dateString + " " + "00:00:00";
        }
        return begintime;
    }

    // 根据字符串返回该字符串对应的时间类型"00-00-00 24:00:00"
    public static String getEndtime(String endtime) {
        if (endtime != null && endtime.length() == 8) {
            String yearString = endtime.substring(0, 4);
            String monthString = endtime.substring(4, 6);
            String dateString = endtime.substring(6);
            endtime = yearString + "-" + monthString + "-" + dateString + " " + "24:00:00";
        }
        return endtime;
    }

    // 根据字符串返回该字符串对应的日期类型"00-00-00"
    public static String getdate(String stringtime) {
        if (stringtime != null && stringtime.length() == 8) {
            String yearString = stringtime.substring(0, 4);
            String monthString = stringtime.substring(4, 6);
            String dateString = stringtime.substring(6);
            stringtime = yearString + "-" + monthString + "-" + dateString;
        }
        return stringtime;
    }

    // 将2010-10-10类型转换成20101010格式
    public static String getString(String stringtime) {
        if (stringtime != null && stringtime.length() == 10) {
            String yearString = stringtime.substring(0, 4);
            String monthString = stringtime.substring(5, 7);
            String dateString = stringtime.substring(8);
            stringtime = yearString + monthString + dateString;
        }
        return stringtime;
    }

    // 将日期转化为HH24MMSS格式的字符串
    public static String convertTohh24mmssFormat(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        if (Integer.parseInt(hour) < 10) {
            hour = "0" + hour;
        }
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        if (Integer.parseInt(minute) < 10) {
            minute = "0" + minute;
        }
        String second = String.valueOf(calendar.get(Calendar.SECOND));
        if (Integer.parseInt(second) < 10) {
            second = "0" + second;
        }
        String result = hour + minute + second;
        return result;
    }

    // 获得两个日期的时间差，返回结果为_小时_分钟_秒
    public static String gettimedefferent(long milliseconds) {
        long hours = 0;
        long minutes = 0;
        long seconds = milliseconds / 1000;
        hours = seconds / 3600;
        seconds = seconds - hours * 3600;
        minutes = seconds / 60;
        seconds = seconds - minutes * 60;
        System.out.println(hours + "小时" + minutes + "分钟" + seconds + "秒");
        return hours + "小时" + minutes + "分钟" + seconds + "秒";
    }

    /**
     * 获得几秒前的时间
     * @param date1 时间 yyyy-MM-dd HH:mm:ss
     * @param seconds 秒 1
     * @throws ParseException 
     */
    public static String getTimeBefore(String date1, int seconds) throws ParseException{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = df.parse(date1);// 解析成日期格式
        date.setTime(date.getTime() - seconds * 1000);// 减去N秒的时间

        return df.format(date);// 再将该时间转换成字符串格式
    }
    
    
    /**
     * 得到几天后的时间 *
     * 
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 标准日期时间比较大小
     * @param String
     *            date1 "2014-3-14 18:47:08"
     * @param String
     *            date2 "2014-3-14 18:47:09"
     * @return 毫秒数 1000(ms)
     */
    public static long diffDateTime(String date1, String date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long diff = 0;
        try {
            Date d1 = df.parse(date1);
            Date d2 = df.parse(date2);
            diff = d1.getTime() - d2.getTime();
        } catch (Exception e) {
        }
        return diff;
    }

    public static String formatLongToTimeStr(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = l.intValue() / 1000;

        if (second > 60) {
            minute = second / 60;
            second = second % 60;
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        return (getTwoLength(hour) + getTwoLength(minute) + getTwoLength(second));
    }

    public static String getTwoLength(final int data) {
        if (data < 10) {
            return "0" + data;
        } else {
            return "" + data;
        }
    }

    /**
     * 将时长转换为分钟
     */
    public static String change2Minute(String duration) {
        if (duration.indexOf(":") >= 0) {
            String[] durationArr = duration.split(":");
            long hour = Long.parseLong(durationArr[0]);
            long minute = Long.parseLong(durationArr[1]);
            long second = Long.parseLong(durationArr[2]);
            if (second > 0) {
                duration = String.valueOf(hour * 60 + minute + 1);
            } else {
                duration = String.valueOf(hour * 60 + minute);
            }
        } else if (duration.matches("[0-9]+")) {

        } else {
            System.out.println("格式不正确：" + duration);
        }
        return duration;
    }
    
    /**
     * 获取当前标准日期时间 
     */
    public static String getStandardNow() {
        return DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取运行时间 
     * @param adddays为延期天数
     */
    public static String getStandardRound(String adddays) {
        return getStandardRound(adddays, true);
    }

    /**
     * 获取运行时间 当前时间标准返回,未来时间只保留日期,时间为00:00:00
     * @param adddays 延期天数
     * @param readspecial 是否读取特殊含义值 keep 永不失效 将原值返回
     * @return
     */
    public static String getStandardRound(String adddays, boolean readspecial) {
        String runDate = new String();
        if ("".equals(adddays)) {
            adddays = "0";
        }
        if ("keep".equals(adddays)) {
            if (readspecial) {
                return "keep";
            } else {
                adddays = "0";
            }
        }
        if ("永不失效".equals(adddays)) {
            if (readspecial) {
                return "永不失效";
            } else {
                adddays = "0";
            }
        }
        /*
         * Pattern
         * pattern=Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
         * Matcher matcher=pattern.matcher(runtime); if(matcher.find()){ runDate
         * = runtime; //已经是一个完成的时间格式yyyy-MM-dd HH:mm:ss }
         */
        if (adddays.indexOf(":") != -1) {
            runDate = adddays; // 已经是一个完成的时间格式yyyy-MM-dd HH:mm:ss
        } else {
            adddays = adddays.trim().replace("+", "").trim();
            runDate = DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss");
            try {
                runDate = DateUtil.convertDateToString(DateUtil.getDateAfter(DateUtil.convertStringToDate(DateUtil.getDateTime("MM/dd/yyyy")),
                        Integer.valueOf(adddays)));
                runDate = runDate.substring(6, 10) + "-" + runDate.substring(0, 2) + "-" + runDate.substring(3, 5) + " ";
                if (Integer.valueOf(adddays) == 0) {
                    runDate += DateUtil.getDateTime("HH:mm:ss");
                } else {
                    runDate += "00:00:00";
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return runDate;
            }
        }
        return runDate;
    }

    /**
     * 判断是否达到指定标准时间
     * @param date 2014-01-12 15:00:00
     * @return 达到true 未达到false
     */
    public static boolean isreachStandard(String date) {
        // 将仅含日期的date添加上时间
        if (date != null && date.length() == 10 && "-".equals(String.valueOf(date.charAt(4))) && "-".equals(String.valueOf(date.charAt(7)))) {
            date += " 00:00:00";
        }
        if (date == null || "null".equals(date) || date.length() != 19) {
            return false;
        }
        String nowdate = getStandardRound("0");
        if (nowdate.compareTo(date) < 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 标准时长转为秒数
     * @param type : 26:15:04 -> 94504
     */
    public static long standardtime2secondslong(String duration) {
        long sum = 0;
        try {
            if (duration != null && duration.indexOf(":") >= 0) {
                String[] durationArr = duration.split(":");
                Long hour = Long.parseLong(durationArr[0]);
                Long minute = Long.parseLong(durationArr[1]);
                Long second = Long.parseLong(durationArr[2]);
                sum = hour * 3600 + minute * 60 + second;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }

    /**
     * 秒数转为标准时长
     * @param type : 94504 -> 26:15:04
     * 
     */
    public static String seconds2standardtime(Long seconds) {
        Long second = seconds % 60;
        Long minute = (seconds % 3600 - second) / 60;
        Long hour = (seconds - minute * 60 - second) / 3600;
        return hour + ":" + (minute >= 10 ? minute : "0" + minute) + ":" + (second >= 10 ? second : "0" + second);
    }

    /**
     * 将yyyyMMdd格式的日期转换为标准格式
     * @param type : 20150105 -> 2015-01-05
     * @return
     */
    public static String yyyyMMdd2StandardDate(String stringdate) {
        if (stringdate != null && stringdate.length() == 8) {
            String yearString = stringdate.substring(0, 4);
            String monthString = stringdate.substring(4, 6);
            String dateString = stringdate.substring(6, 8);
            stringdate = yearString + "-" + monthString + "-" + dateString;
        }
        return stringdate;
    }

    /**
     * 将获取的时间转换为标准时间
     * @param type : 135000 -> 13:50:00
     */
    public static String HHmmss2StandardTime(String HH24mmss) {
        if (HH24mmss != null && HH24mmss.length() == 6) {
            String hourString = HH24mmss.substring(0, 2);
            String minuteString = HH24mmss.substring(2, 4);
            String secondsString = HH24mmss.substring(4, 6);
            HH24mmss = hourString + ":" + minuteString + ":" + secondsString;
        }
        return HH24mmss;
    }

    /**
     * 获取某标准日期时间节点经过某时长后的时间节点
     * 例初始时间节点: 2014-12-31 23:00:00
     * 经过时长：15:00:00
     * @return 2015-01-01 14:00:00
     */
    public static String StandardaddHHmmss(String starttime, String duration) {
        int durationtime = (int) DateUtil.standardtime2secondslong(duration);
        String endtime = starttime;
        // int starthour = Integer.parseInt(starttime.substring(11,12));
        // int startminute = Integer.parseInt(starttime.substring(14,15));
        //
        // int durationhour = Integer.parseInt(duration.substring(0,1));
        // int durationminute = Integer.parseInt(duration.substring(3,4));

        // starttime = starttime.substring(11,18);
        // if(starttime)
        // String endtime = ;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(starttime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.SECOND, durationtime);// 对秒数进行操作

            endtime = sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return endtime;
    }
    
    public static String formatDate(Date date,String formatter){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(formatter);
            return sdf.format(date);
        }catch(Throwable e){
            return "";
        }
    }

    public static String formatDateWithT(Date date,String formatter){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(formatter);
            String temp = sdf.format(date);
            temp = temp.replace(" ", "T");
            return temp;
        }catch(Throwable e){
            return "";
        }
    }
    
    public static Calendar formatString2Date(String date,String formateer){
        SimpleDateFormat format1 = new SimpleDateFormat(formateer);  
        try {
            Date temp =  format1.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(temp);
            return cal;
        } catch (ParseException e) {
            return null;
        }
    }
    
    public static Calendar formatString2DateWithT(String date,String formateer){
        date = date.replace("T", " ");
        SimpleDateFormat format1 = new SimpleDateFormat(formateer);  
        try {
            Date temp =  format1.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(temp);
            return cal;
        } catch (ParseException e) {
            return null;
        }
    }
    
    public static String getTransactionID(){
        Calendar cal = Calendar.getInstance();
        String transactionID = "" + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH) + cal.get(Calendar.DAY_OF_MONTH) + cal.get(Calendar.HOUR_OF_DAY) + cal.get(Calendar.MINUTE) + cal.get(Calendar.SECOND) ;
        return transactionID;
    }
    
    // 计算间隔时间
    public String intervalTime(String beginTime, String endTime) throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date beginTimeDate = df.parse(beginTime);
        java.util.Date endTimeDate = df.parse(endTime);
        
        long l = endTimeDate.getTime() - beginTimeDate.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        
        return day + "天" + hour + "小时" + min + "分" + s + "秒";
    }
    
    public static void main(String[] args) throws ParseException {
//        String startDate = "2014-09-22";
//        String startTime = "2014-09-22 09:05:02";
//        String endTime = "2014-09-22 12:00:00";

        System.out.println(yyyyMMdd2StandardDate("20140102"));
        System.out.println(HHmmss2StandardTime("151617"));
        System.out.println(StandardaddHHmmss("2014-12-31 23:00:00","15:00:00"));
        
        
        System.out.println("2015-04-24 11:00:00" + isreachStandard("2015-04-24 11:00:00"));
        System.out.println("2011-04-23" + isreachStandard("2011-04-23"));
        System.out.println("EMPTY" + isreachStandard(""));
        System.out.println("123" + isreachStandard("123"));
        System.out.println(isreachStandard(null));
//        
//        Calendar cal = Calendar.getInstance();
//        String s = formatDate(cal.getTime(),"yyyy-MM-ddThh:mm:ss");
//        String s1 = formatDateWithT(cal.getTime(),"yyyy-MM-dd HH:mm:ss");
//        
//        System.out.println(s + "\n" + s1 + "\n" + new Timestamp(cal.getTime().getTime()));
        System.out.println("2011-04-23".length());

        // 计算间隔时间
        DateUtil dateUtil = new DateUtil();
        String beginTime = "2009-02-28 11:30:41";
        String endTime = "2009-03-01 13:31:40";
        String str = dateUtil.intervalTime(beginTime,endTime);
        System.out.println(str);

    }
}