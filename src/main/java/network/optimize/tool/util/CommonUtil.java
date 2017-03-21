package network.optimize.tool.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpSession;

import network.optimize.tool.constant.ErrorCode;
import network.optimize.tool.exception.WebBackendException;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class CommonUtil {

	/** 处理参数校验错误
	 * @param bindingResult 校验信息
	 * @throws WebBackendException
	 */
	public static void handleValidation(BindingResult bindingResult)
			throws WebBackendException {
		if(bindingResult.hasErrors()){
    		List<ObjectError>  list = bindingResult.getAllErrors();
            for(ObjectError  error:list){
            	WebBackendException e = new WebBackendException(ErrorCode.NOT_VALID_PARAM);
            	e.setErrorInfo(error.getDefaultMessage());
            	throw e;
            }
    	}
	}
	
	/**
	 * MD5加密
	 */
	public static String getMd5(String str){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] byteDigest = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < byteDigest.length; offset++) {
				i = byteDigest[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// 32位加密
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 为用户生成令牌
	 */
	public static String getNewToken(HttpSession session) {
		// 用户名与当前session拼接起来，取md5
		String now = new Date().toString();
		// 使用sessionId防止外界拿到算法算出session
		String seed = session.getId() + now;
		String token = getMd5(seed);
		return token;
	}
	
	
	/**
	 * null转空字符串
	 */
	public static String NullToBlank(String str) {
		return str==null?"":str;
	}
	
	
	/**
	 * 判断是否为空
	 */
	public static boolean isBlank(Object b) {
		if (b == null) {
			return true;
		}
		return b.toString().equals("");
	}
	
	/**
	 * 转换数字至Int,错误转为null
	 */
	public static Integer parseIntegerDefaultNull(Object b) {
		if (b == null) {
			return null;
		}
		try{
			return Integer.parseInt(b.toString());
		} catch (Exception e){
			return null;
		}
	}
	
	
	/**
	 * 转换数字至Int,错误转为0
	 */
	public static Integer parseIntegerDefaultZero(Object b) {
		if (b == null) {
			return 0;
		}
		try{
			return Integer.parseInt(b.toString());
		} catch (Exception e){
			return 0;
		}
	}
	
	/**
	 * 转换数字至Long,错误转为null
	 */
	public static Long parseLongDefaultNull(Object b) {
		if (b == null) {
			return null;
		}
		try{
			return Long.parseLong(b.toString());
		} catch (Exception e){
			return null;
		}
	}
	
	/**
	 * 转换数字至Long,错误转为0
	 */
	public static Long parseLongDefaultZero(Object b) {
		if (b == null) {
			return 0L;
		}
		try{
			return Long.parseLong(b.toString());
		} catch (Exception e){
			return 0L;
		}
	}
	
    /**
     * 将map转成键值List,默认为[{key=k1,value=v1},{key=k2,value=v2}]形式
     */
	public static List<HashMap> convertMapToList(
		Map map) {
		return convertMapToList(map,"key","value");
	}
	
    /**
     * 将map转成键值List
     * @param map
     * @param key
     * @param value
     * @return
     */
	public static List<HashMap> convertMapToList(
			Map map, String key, String value) {
		List<HashMap> list = new ArrayList<HashMap>();
		Set<Entry> entrySet = map.entrySet();
		for(Entry entry:entrySet){
			HashMap entryMap = new HashMap();
			entryMap.put(key, entry.getKey());
			entryMap.put(value, entry.getValue());
			list.add(entryMap);
		}
		return list;
	}
	
	/**
	 * 判断字符串是否能转double
	 */
    public static boolean isDouble(String str) {  
        try{
        	Double.parseDouble(str);
        } catch(Exception e){
        	return false;
        }
        return true;  
    }  
    
	/**
	 * 判断字符串是否是数字
	 */
    public final static boolean isNumeric(String str) {  
        if (str != null && !"".equals(str.trim())){ 
            return str.matches("^[0-9.]*$");
        }
        return false;  
    }
    
    /**
     * 返回列表的第一个元素
     */
    public static <T> T getFirst(List<T> list){
    	if(list==null||list.size()==0) return null;
    	return list.get(0);
    }
    
    /**
     * 判断列表是否为空
     */
    public static boolean isEmptyList(List list){
    	return list==null||list.isEmpty();
    }
}
