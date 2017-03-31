package network.optimize.tool.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import network.optimize.tool.util.CommonUtil;
import network.optimize.tool.util.DatetimeUtil;

public class CheckUtil {

	public static boolean isNotBlank(Object b) {
		if (b == null) {
			return false;
		}
		String a = b.toString();
		if (a.equals("")) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isBlank(Object b) {
		if (b == null) {
			return true;
		}
		String a = b.toString();
		if (a.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isTrue(Object b) {
		if (b == null) {
			return false;
		}
		String a = b.toString();

		if (a.equals("1") || a.equals("true")) {
			return true;
		}

		return false;
	}

	// 判断是不是MD5
	public static boolean isMd5(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("^([a-fA-F0-9]{32,32})$");
		return pattern.matcher(str).matches();
	}

	public static boolean isNumber(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[0-9]+$");
		return pattern.matcher(str).matches();
	}

	public static boolean isNumberAndComma(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[0-9,]+$");
		return pattern.matcher(str).matches();
	}

	public static boolean isPositiveInteger(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("^+?[1-9][0-9]*$");
		return pattern.matcher(str).matches();
	}

	public static boolean isAlpha(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[a-zA-Z_0-9]+$");
		return pattern.matcher(str).matches();
	}

	public static boolean isAlphaAndComma(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[0-9a-zA-Z_,]+$");
		return pattern.matcher(str).matches();
	}

	// 判断是不是中文字符
	public static boolean isChineseCharacter(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
		return pattern.matcher(str).matches();
	}

	public static boolean isValidatedPath(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("^(/[0-9a-zA-Z_-]+)*/$");
		return pattern.matcher(str).matches();
	}
	
	// YYYYMM
	public static boolean isYearMonth(String str) {
		if (str == null || str.length() != 6) {
			return false;
		}
		Pattern pattern = Pattern.compile("^((19|20)[0-9]{2})(0?[1-9]|1[012])$");
		return pattern.matcher(str).matches();
	}

	// YYYY-MM-DD
	public static boolean isMinusDate(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("^((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$");
		return pattern.matcher(str).matches();
	}

	// YYYY-MM-DD HH:mm:ss
	public static boolean isDateTime(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}\\s[0-9]{2}:[0-9]{2}:[0-9]{2}$");
		return pattern.matcher(str).matches();
	}

	// comments xuan
	public static boolean isFromUserList(String str, List<Map> userList) {
		if (str == null) {
			return false;
		}
		for (int i = 0; i < userList.size(); i++) {
			if (str.equals(userList.get(i).get("id"))) {
				return true;
			}
		}
		return false;
	}

	// comments xuan
	public static boolean isFromBankList(String str, List<Map> bankList) {
		if (str == null) {
			return false;
		}

		for (int i = 0; i < bankList.size(); i++) {
			if (str.equals(bankList.get(i).get("iss_bank_nm"))) {
				return true;
			}
		}
		return false;
	}

	// regular pattern
	public static boolean regex(String str, String regex) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(str).matches();
	}

	// comments xuan
	public static boolean isIpAddress(String str) {
		if (str == null) {
			return false;
		}
		String[] splitIpAddress = str.split("\\.");
		if (splitIpAddress.length != 4) {
			return false;
		} else {
			for (int i = 0; i < splitIpAddress.length; i++) {
				if (Integer.parseInt(splitIpAddress[i]) < 0 || Integer.parseInt(splitIpAddress[i]) > 255) {
					return false;
				}
			}
			return true;
		}
	}

	public static boolean isYMDate(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("^20[0-9]{2}[0-9]{2}$");
		return pattern.matcher(str).matches();
	}

	// 验证用户密码：长度在8~18之间，密码必须包含大小写字母、数字和特殊字符(不含空格)
	public static boolean isPassword(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[`~!@#$%^&*()+=\\[\\]{}\\|;:'\",.<>?/_-]).{8,18}$");
		return pattern.matcher(str).matches();
	}

	// 验证用户邮箱,英文26个字母、10个阿拉伯数字、点、减号或下划线组成，只能以数字或字母开头和结尾
	public static boolean isEmail(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("^([a-z0-9A-Z]+[\\-\\.\\_]{0,})+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
		return pattern.matcher(str).matches();
	}

	public static boolean compareDate(String str1, String str2) {
		Date date1 = null;
		Date date2 = null;
		date1 = java.sql.Date.valueOf(str1); // 只保留日期部分，返回的是java.sql.Util,YYYY-MM-DD
		date2 = java.sql.Date.valueOf(str2);
		return date1.compareTo(date2) > 0 ? false : true;
	}

	public static String join(double[] a) {
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < a.length; i++) {
			b.append(a[i]).append(",");
		}
		b.deleteCharAt(b.length() - 1);
		return b.toString();
	}

	public static String join(String[] a) {
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < a.length; i++) {
			b.append(a[i]).append(",");
		}
		b.deleteCharAt(b.length() - 1);
		return b.toString();
	}

	public static String join(int[] a) {
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < a.length; i++) {
			b.append(a[i]).append(",");
		}
		b.deleteCharAt(b.length() - 1);
		return b.toString();
	}

	public static int randomInt(int max) {
		Random random = new Random(System.currentTimeMillis());// 指定种子数字
		return random.nextInt(max);
	}

	public static void printStack() {
		Throwable ex = new Throwable();
		StackTraceElement[] stackElements = ex.getStackTrace();
		if (stackElements != null) {
			for (int i = 0; i < stackElements.length; i++) {
				System.out.print(stackElements[i].getClassName() + "/t");
				System.out.print(stackElements[i].getFileName() + "/t");
				System.out.print(stackElements[i].getLineNumber() + "/t");
				System.out.println(stackElements[i].getMethodName());
				System.out.println("-----------------------------------");
			}
		}
	}

	public static String bytesToString(byte[] buf) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;
		for (i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10) {
				strbuf.append("0");
			}
			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}
		return strbuf.toString();
	}

	public static byte[] stringToBytes(String target) {
		byte[] buf = new byte[target.length() / 2];

		int i;
		for (i = 0; i < target.length(); i += 2) {
			byte a = (byte) target.charAt(i);
			byte b = (byte) target.charAt(i + 1);
			if (a >= '0' && a <= '9') {
				a = (byte) (a - '0');
			} else {
				a = (byte) (a - 'a' + 10);
			}

			if (b >= '0' && b <= '9') {
				b = (byte) (b - '0');
			} else {
				b = (byte) (b - 'a' + 10);
			}

			buf[i / 2] = (byte) (a * 16 + b);
		}
		return buf;
	}

	// validate card number
	public static boolean validateAccountNo(String cardNum) {
		if (isNumber(cardNum)) {
			return true;
		}
		return false;
	}
	
	// validate merchant
	public static boolean validateMchtCd(String mcht_cd) {
		if (isAlpha(mcht_cd)) {
			if (mcht_cd.length() == 15) {
				return true;
			}
		}
		return false;
	}

	// MD5 and hash card number to get %02d model
	public static String hashCardNum(String cardNum) {
		int hashvalue = 0;
		String concatCardNum = cardNum.length() + cardNum;
		hashvalue = getMD5(concatCardNum).hashCode() % 100;
		return String.format("%02d", Math.abs(hashvalue));
	}

	// Input String to get MD5
	public static String getMD5(String str) {
		return CommonUtil.getMd5(str);
	}

	// generate token
	public static String csrfToken() {
		Random rnd = new Random(System.nanoTime());
		// 构造一个随机生成的 BigInteger，它是在 0 到 (2numBits - 1)（包括）范围内均匀分布的值,返回此
		// BigInteger 的给定基数的字符串表示形式
		return (new BigInteger(165, rnd)).toString(36).toUpperCase();
	}

	public static String getDouble2DPercent(double x, double y) {
		double tempresult = (x - y) / y;
		// NumberFormat nf = NumberFormat.getPercentInstance(); 注释掉的也是一种方法
		// nf.setMinimumFractionDigits( 2 ); 保留到小数点后几位
		// ##.00%, 百分比格式，后面不足2位的用0补齐
		DecimalFormat df1 = new DecimalFormat("0.00%");
		return df1.format(tempresult);
	}

	/**
	 * 将每三个数字加上逗号处理（通常使用金额方面的编辑）,还可处理科学计数法
	 * 
	 * @param str
	 *            无逗号的数字
	 * @return 加上逗号的数字
	 */
	public static String addComma(String str) {
		if (str == null)
			return null;
		String decimals = "";
		String intNumber = "";
		StringBuffer strTempBuff = new StringBuffer();
		if (str.contains(".")) {// 处理小数问题，将小数先截取保留后添加
			int index = str.indexOf(".");
			decimals = str.substring(index);
			intNumber = str.substring(0, index);
		} else {
			intNumber = str;
		}
		// 将传进数字反转
		String reverseStr = new StringBuffer(intNumber).reverse().toString();
		for (int i = 0; i < reverseStr.length(); i++) {
			if (i * 3 + 3 > reverseStr.length()) {
				strTempBuff.append(reverseStr.substring(i * 3, reverseStr.length()));
				break;
			}
			strTempBuff.append(reverseStr.substring(i * 3, i * 3 + 3)).append(",");

		}
		// 将 【789,456,】 中最后一个【,】去除
		if (strTempBuff.toString().endsWith(",")) {
			strTempBuff = new StringBuffer(strTempBuff.substring(0, strTempBuff.length() - 1));
		}
		// 将数字重新反转,并加上小数
		return strTempBuff.reverse().toString() + decimals;
	}

	/**
	 * 
	 * @param str
	 * @param separator
	 * @param len
	 * @return use separator to format string according to len
	 */
	public static String addSeparator(String str, String separator, int len) {
		if (str == null)
			return null;
		StringBuffer strTempBuff = new StringBuffer();

		// 将传进数字反转
		String reverseStr = new StringBuffer(str).reverse().toString();
		for (int i = 0; i < reverseStr.length(); i++) {
			if (i * len + len > reverseStr.length()) {
				strTempBuff.append(reverseStr.substring(i * len, reverseStr.length()));
				break;
			}
			strTempBuff.append(reverseStr.substring(i * len, i * len + len)).append(separator);
		}
		// strTemp 中最后一个separator去除
		if (strTempBuff.toString().endsWith(separator)) {
			strTempBuff = new StringBuffer(strTempBuff.substring(0, strTempBuff.length() - 1));
		}
		// 将数字重新反转
		return strTempBuff.reverse().toString();
	}

	/**
	 * @description 将科学计算法的数字转换成数字，再加指定格式输出
	 * @param str
	 * @param numberFormat
	 * @return
	 */
	public static String reverseScientificCalculationToNumber(String str, String numberFormat) {
		if (str == null)
			return null;
		DecimalFormat df = new DecimalFormat(numberFormat);// 用于格式化十进制数字
		return df.format(Double.parseDouble(str));
	}

	/**
	 * @description 随机产生不重复的字符串
	 * @param Len
	 * @return
	 */
	public static String getRandomString(int len) {
		String[] baseString = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		StringBuffer sb = new StringBuffer();
		StringBuffer result = new StringBuffer();
		Random random = new Random();
		int length = baseString.length;
		for (int i = 0; i < length; i++) {
			sb.append(baseString[random.nextInt(length)]);
		}

		random = new Random(System.nanoTime());
		for (int i = 0; i < len; i++) {
			result.append(sb.charAt(random.nextInt(sb.length() - 1)));
		}
		return result.toString();
	}

	/**
	 * @description 随机产生不重复的字符串
	 * @param Len
	 * @return
	 */
	public static String getRandomTicket(String prefix, int len) {
		String[] baseString = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		StringBuffer sb = new StringBuffer();
		StringBuffer result = new StringBuffer();
		if (isBlank(prefix) || len < 1) {
			return null;
		}

		Random random = new Random();
		int length = baseString.length;
		for (int i = 0; i < length; i++) {
			sb.append(baseString[random.nextInt(length)]);
		}
		random = new Random(System.nanoTime());

		String ymd = DatetimeUtil.toFormatString(new Date(),"yyMMdd");
		result.append(prefix.toUpperCase()).append(ymd);
		for (int i = 0; i < len; i++) {
			result.append(sb.charAt(random.nextInt(sb.length() - 1)));
		}
		return result.toString();
	}

	/**
	 * @description 将字符串按照split的形式进行分隔成数组
	 * @param str
	 *            待分隔字符串
	 * @param split
	 *            分隔符
	 * @return 分隔之后的字符串数组
	 */
	public static String[] strToArray(String str, String split) {
		String[] arr = null;
		if (isBlank(str)) {
			return arr;
		}
		if (str.contains(split)) {
			arr = str.split(split);
		}
		return arr;
	}

	/**
	 * @description 将流数据转换成字符串
	 * @param ips
	 * @param codeFormat
	 * @return
	 */
	public static String getResponse(InputStream ips, String codeFormat) {
		StringBuffer sb = new StringBuffer();
		InputStreamReader isreader = null;
		try {
			isreader = new InputStreamReader(ips, codeFormat);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		BufferedReader bufferedReader = new BufferedReader(isreader);
		String line = null;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}
			bufferedReader.close();
			isreader.close();
			ips.close();
			ips = null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return sb.toString();
	}

	// Validate API URI
	public static boolean validateApiUri(String uri) {
		if (isBlank(uri)) {
			return false;
		}

		if (regex(uri, "^(/repoweb/api/[0-9a-zA-Z]+.do)$")) {
			return true;
		}
		return false;
	}
       
	    /** 
	     * 判断字符串是否为数字,0-9重复0次或者多次    
	     * @param strnum 
	     * @return 
	     */  
	    private static boolean isNumeric(String strnum) {  
	        Pattern pattern = Pattern.compile("[0-9]*");  
	        Matcher isNum = pattern.matcher(strnum);  
	        if (isNum.matches()) {  
	            return true;  
	        } else {  
	            return false;  
	        }  
	    }  
	   
	    /** 
	     * 功能：判断字符串出生日期是否符合正则表达式：包括年月日，闰年、平年和每月31天、30天和闰月的28天或者29天 
	     *  
	     * @param string 
	     * @return 
	     */ 
	    public static boolean isDate(String strDate) {  
	       
	        Pattern pattern = Pattern  
	                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$");  
	        Matcher m = pattern.matcher(strDate);  
	        if (m.matches()) {  
	            return true;  
	        } else {  
	            return false;  
	        }  
	    }
	    
	    public static String getFormatDate(Date date,String format) {  
	    	SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
	    	return df.format(date);
	    }
	    
	    public static String getFormatNowDate() {  
	    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	    	return df.format(new Date());
	    }
	    
}
