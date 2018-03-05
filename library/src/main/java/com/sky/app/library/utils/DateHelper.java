package com.sky.app.library.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日期工具类
 */
public class DateHelper {
	private static String DEFAULT_PATTERN = "yyyy-MM-dd";
	public static final String FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 获取当前日期
	 * @param format
	 * @return
     */
	public static String getCurrentDate(String format){
		String curDateTime = null;
		try{
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
			Calendar c = new GregorianCalendar();
			curDateTime = mSimpleDateFormat.format(c.getTime());
		} catch (Exception e){
			e.printStackTrace();
		}
		return curDateTime;
	}

	/**
	 * 将时间戳格式化为时间
	 * @param format
	 * @param currentTimeMillis
     * @return
     */
	public static String formatTimestamp(String format, long currentTimeMillis){
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
		try{
			return mSimpleDateFormat.format(new Date(currentTimeMillis));
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据时间字符串转换为date
	 * @param format
	 * @param str
     * @return
     */
	public static Date getDate(String format, String str){
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
		try{
			return mSimpleDateFormat.parse(str);
		} catch (ParseException e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 得到今天的日期 默认格式：yyyy-MM-dd
	 * @return
	 */
	public static String getToday(){
		return getToday(DEFAULT_PATTERN);
	}

	/**
	 * 得到今天的日期
	 * @param pattern 格式：yy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getToday(String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date());
	}

	/**
	 * 得到昨天的日期 默认格式：yyyy-MM-dd
	 * @return
	 */
	public static String getYesterday(){
		return getYesterday(DEFAULT_PATTERN);
	}

	/**
	 * 获取天 前后天
	 * 默认格式：yyyy-MM-dd
	 * @param days 偏移量
	 * @return
     */
	public static String getDay(int days){
		return getDay(days, DEFAULT_PATTERN);
	}

	/**
	 * 获取天
	 * @param days 偏移量
	 * @param pattern 格式
     * @return
     */
	public static String getDay(int days, String pattern){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, days);
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(cal.getTime());
	}

	/**
	 * 得到昨天的日期
	 * @param pattern
	 * 格式：yy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getYesterday(String pattern){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(cal.getTime());
	}

	/**
	 * 得到指定月份
	 * @param offset 偏移量 -1:取得上一个月 0:取得本月 1:取得下一个月
	 * @param pattern 模式
	 * @return
	 * 
	 */
	public static String getMonth(int offset, String pattern){
		String yearMonth = "197101";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Calendar nowCal = Calendar.getInstance();
		nowCal.add(Calendar.MONTH, offset);
		yearMonth = format.format(nowCal.getTime());
		return yearMonth;
	}

	/**
	 * 得到当前月份的前后偏移月
	 * @param offset 偏移量
	 * @param pattern 格式
	 * @param currentMonth 当前月份
     * @return
     */
	public static String getMonth(int offset, String pattern, String currentMonth){
		String yearMonth = "197101";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try{
			Date startDate = format.parse(currentMonth);
			Calendar nowCal = Calendar.getInstance();
			nowCal.setTime(startDate);
			nowCal.add(Calendar.MONTH, offset);
			yearMonth = format.format(nowCal.getTime());
		}catch (ParseException e){
			e.printStackTrace();
		}
		return yearMonth;
	}

	/**
	 * 得到月份
	 * @param offset
	 * @param pattern
	 * @param currentMonth
     * @return
     */
	public static String getMonthDay(int offset, String pattern, String currentMonth){
		String yearMonth = "197101";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		SimpleDateFormat formatMM = new SimpleDateFormat("MM");
		try{
			Date startDate = format.parse(currentMonth);
			Calendar nowCal = Calendar.getInstance();
			nowCal.setTime(startDate);
			Calendar tomorrowCal = Calendar.getInstance();
			tomorrowCal.setTime(startDate);
			tomorrowCal.add(Calendar.DAY_OF_MONTH, 1);
			if(tomorrowCal.get(Calendar.MONTH) != nowCal.get(Calendar.MONTH)) {
				tomorrowCal.add(Calendar.MONTH, offset);
				tomorrowCal.add(Calendar.DAY_OF_MONTH, -1);
				yearMonth = format.format(tomorrowCal.getTime());
			} else {
				nowCal.add(Calendar.MONTH, offset);
				yearMonth = format.format(nowCal.getTime());
			}
		}catch (ParseException e){
			e.printStackTrace();
		}
		return yearMonth;
	}

	/**
	 * 得到一周第几天
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(Date date){
		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		aCalendar.setTime(date);
		int x = aCalendar.get(Calendar.DAY_OF_WEEK) - 1;
		if(x == 0) x = 7;
		return x;
	}

	/**
	 * 得到一个月第几天
	 * @param date
	 * @return
     */
	public static int getDayOfMonth(Date date){
		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		aCalendar.setTime(date);
		int x = aCalendar.get(Calendar.DAY_OF_MONTH);
		return x;
	}

	/**
	 * 得到月份的最后天
	 * @return
     */
	public static int getLastDayOfMonth(){
		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		int lastday = aCalendar.getActualMaximum(Calendar.DATE);
		return lastday;
	}

	/**
	 * 最后一周
	 * @param date
	 * @return
     */
	public static Date getNextWeekFirstDate(Date date){
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.setTime(date);
		int dayOfWeek = 0;
		
		for(int i = 1; i <= 7; i++){
			c.add(Calendar.DAY_OF_MONTH, 1);
			dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			if(dayOfWeek == 2) return c.getTime();
		}
		return date;
	}

	/**
	 * 得到最后一周
	 * @param date
	 * @return
     */
	public static String getNextWeekFirstDay(Date date){
		DateFormat format=  new SimpleDateFormat("yyyy-MM-dd");
		return format.format(getNextWeekFirstDate(date));
	}

	public static Date getNextWeekLastDate(Date date){
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.setTime(getNextWeekFirstDate(date));
		int dayOfWeek = 0;
		for(int i = 1; i <= 7; i++){
			c.add(Calendar.DAY_OF_MONTH, 1);
			dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			if(dayOfWeek == 1) return c.getTime();
		}
		return date;
	} 
	
	public static String getNextWeekLastDay(Date date){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(getNextWeekLastDate(date));
	}

	public static String getFirstDayOfMonth(Date date){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return format.format(c.getTime());
	}
	
	public static Date  getFirstDateOfMonth(Date date){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	/**
	 * 得到两个日期相差的天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getDaysDiff(Date date1, Date date2){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		return (int) Math.abs((c1.getTimeInMillis()-c2.getTimeInMillis()) / (24 * 3600 * 1000));
	}

	/**
	 * 得到两个日期相差的分钟数
	 * @param date1
	 * @param date2
     * @return
     */
	public static int getMinutesDiff(Date date1, Date date2){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		return (int) (c1.getTimeInMillis() - c2.getTimeInMillis()) / (60 * 1000);
	}

	/**
	 * 得到传入日期的周次 (在一个月内)
	 * @return
	 */
	public static int getWeekOfMonth(Date date){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		Date firstDateOfMonth = getFirstDateOfMonth(date);
		int dayOfWeek = getDayOfWeek(firstDateOfMonth);
		Date fisrtMondayOfMonth = null;
		if(dayOfWeek == 1){
			fisrtMondayOfMonth = firstDateOfMonth;
		}else{
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date);
			dayOfWeek = c1.get(Calendar.DAY_OF_WEEK);
			if(dayOfWeek == 1)
				c1.add(Calendar.DAY_OF_MONTH, -7);
			else
				c1.add(Calendar.DAY_OF_MONTH, -(c1.get(Calendar.DAY_OF_WEEK)-2));
			
			Date firstDateOfLastMonth=getFirstDateOfMonth(c1.getTime());
			fisrtMondayOfMonth=getFirstMondayOfMonth(firstDateOfLastMonth);
		}
		int diff = getDaysDiff(date,fisrtMondayOfMonth);
		int weekOfMonth = diff/7+1;
		return weekOfMonth;
	}

	public static Date getFirstMondayOfMonth(Date date){
		Date firstDate = getFirstDateOfMonth(date);
		Calendar c = Calendar.getInstance();
		c.setTime(firstDate);
		int dayOfWeek = 0;
		for(int i = 0; i < 30; i++){
			dayOfWeek=c.get(Calendar.DAY_OF_WEEK);
			if(dayOfWeek==2)
				return  c.getTime();
			  c.add(Calendar.DAY_OF_MONTH, 1);
		}
		return null;
	}
	
	public static String getDateString(Date date,String pattern){
		DateFormat format = new SimpleDateFormat(pattern);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return format.format(c.getTime());
	}
	
	public static Date getStringDate(String str,String pattern){
		DateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 得到当月第一天
	 * @param date
	 * @return
	 */
	public static String getFirstdayOfMonth(Date date){
		return getDateString(date,"yyyy-MM") + "-01";
	}

	/**
	 * 得到某月第一天
	 * @return
	 */
	public static String getFirstdayOfMonth(String month, String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String firstday = "";
		try {
			Date date = format.parse(month);
			firstday = getDateString(date,"yyyy-MM") + "-01";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return firstday;
	}

	/**
	 * 得到某月最后一天
	 * @return
	 */
	public static String getLastdayOfMonth(String month, String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);  
		String lastday = "";
		try {
			Date date = format.parse(month);  
			Calendar aCalendar = Calendar.getInstance(); 
			aCalendar.setTime(date); 
			aCalendar.add(Calendar.MONTH, 1);
			aCalendar.add(Calendar.DAY_OF_MONTH, -1); 
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			lastday = format1.format(aCalendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return lastday;
	}
	
	public int getWeekIndexOfYear(String day){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int index = 0;
		try{
			Date startDate = format.parse(day);
			Calendar nowCal = Calendar.getInstance();
			nowCal.setTime(startDate);
			index =  nowCal.get(Calendar.WEEK_OF_YEAR);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return index;
	}
	
	/**
	 * 获取一年的某周的第一天
	 * @param year
	 * @param week_index
	 * @return
	 */
	public static String getFirstDayOfWeek(String year,String week_index,String patten){
		SimpleDateFormat formatter = new  SimpleDateFormat("yyyy w");
		Date date = new Date();
		try {
			date = formatter.parse(year + " " + week_index);
		} catch (ParseException e) {
			return "";
		}
		SimpleDateFormat formatter2 = new SimpleDateFormat(patten);
		return formatter2.format(date);
	}
	
	public static String getLastDayOfWeek(String year,String week_index,String patten){
		String first_day = getFirstDayOfWeek(year, String.valueOf(Integer.valueOf(week_index) + 1), patten);
		return getOffsetDay(first_day, -1, patten);
		
	}
	
	public static String getOffsetDay(String currentDay, int offset, String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String day = "";
		try{
			Date startDate = format.parse(currentDay);
			Calendar startCal = Calendar.getInstance();
			startCal.setTime(startDate);
			startCal.add(Calendar.DAY_OF_MONTH, offset - 1);
			day = format.format(startCal.getTime());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return day;
	}

	/**
	 * 返回文字描述的日期
	 * @return
	 */
	private final static long minute = 60 * 1000;// 1分钟
	private final static long hour = 60 * minute;// 1小时
	private final static long day = 24 * hour;// 1天
	private final static long month = 31 * day;// 月
	private final static long year = 12 * month;// 年
	public static String showTime(String str) {
		if (str == null) {
			return "";
		}
		Date date;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return str;
		}
		long diff = new Date().getTime() - date.getTime();
		long r = 0;
		if (diff > year) {
			r = (diff / year);
			return r + "年前";
		}
		if (diff > month) {
			r = (diff / month);
			return r + "个月前";
		}
		if (diff > day) {
			r = (diff / day);
			return r + "天前";
		}
		if (diff > hour) {
			r = (diff / hour);
			return r + "个小时前";
		}
		if (diff > minute) {
			r = (diff / minute);
			return r + "分钟前";
		}
		return "刚刚";
	}
}
