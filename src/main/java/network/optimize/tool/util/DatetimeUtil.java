package network.optimize.tool.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import network.optimize.tool.constant.ErrorCode;
import network.optimize.tool.constant.NetworkOptimizeConstant;
import network.optimize.tool.exception.WebBackendException;

public class DatetimeUtil {

	/**
	 * 日期操作，日期加上n天
	 */
	public static Date addDay(Date date, int dayNum){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, dayNum);
		return calendar.getTime();
	}
	
	/**
	 * 日期操作，月份加上n月
	 */
	public static Date addMonth(Date month, int monthNum){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(month);
		calendar.add(Calendar.MONTH, monthNum);
		return calendar.getTime();
	}
	
	/**
	 * 日期操作，时间变为当月第1天
	 */
	public static Date setDayOfMonth(Date date, int day){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}
	
	
	/**
	 * 获取时间戳
	 */
	public static String getQuickTimestamp(){
		return Long.toString(System.currentTimeMillis());
	}
	
	/**
	 * 获取当前紧凑的时间
	 */
	public static String getTimestamp(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
	
	/**
	 * 转换时间格式
	 */
	public static String toFormatDateTimeString(Date date){
		return toFormatString(date,NetworkOptimizeConstant.COMMON_DATETIME_FORMAT);
	}
	
	/**
	 * 转换时间格式
	 */
	public static String toFormatDateString(Date date){
		return toFormatString(date,NetworkOptimizeConstant.COMMON_DATE_FORMAT);
	}
	
	/**
	 * 转换时间格式
	 */
	public static String toFormatString(Date date,String dateFormat){
		if(date==null){
			return "";
		}
		SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}
	
	/**
	 * 返回所选日期0点时间
	 */
	public static Date getStartTime(Date date){
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		return date;
	}
	
	/**
	 * 返回今天0点时间
	 */
	public static Date getTodayStartTime(){
		Date date = new Date();
		return getStartTime(date);
	}

	/**
	 * 返回本月开始时间
	 */
	public static Date getMonthStartTime() {
		Date date = new Date();
		date.setDate(1);
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		return date;
	}
	
	/**
	 * 返回年
	 */
	public static String getYearStr(Date date) {
		return toFormatString(date,"yyyy");
	}
	
	/**
	 * 返回年月
	 */
	public static String getYearMonthStr(Date date) {
		return toFormatString(date,"yyyy-MM");
	}
	
	/**
	 * 返回年周
	 */
	public static String getYearWeekStr(Date date) {
		return toFormatString(date,"yyyy w");
	}
	/**
	 * 返回年月日
	 */
	public static String getDateStr(Date date) {
		return toFormatString(date,"yyyy-MM-dd");
	}
	/**
	 * 返回年月日时
	 */
	public static String getDateHourStr(Date date) {
		return toFormatString(date,"yyyy-MM-dd HH");
	}
	/**
	 * 返回年月日时分
	 */
	public static String getDateHourMinuteStr(Date date) {
		return toFormatString(date,"yyyy-MM-dd HH:mm");
	}
	/**
	 * 返回年月日时分秒
	 */
	public static String getDateHourSecondStr(Date date) {
		return toFormatString(date,"yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 转换指定格式字符串为时间
	 * @throws WebBackendException 
	 */
	public static Date getDateByString(String date,String dateFormat) throws WebBackendException{
		if(date==null){
			throw new WebBackendException(ErrorCode.NOT_VALID_DATE);
		}
		SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			throw new WebBackendException(ErrorCode.NOT_VALID_DATE);
		}
	}
	
}
