package network.optimize.tool.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConvertUtil {
	/**
	 * 
	 * 将列表转换为另一个列表，由rowConverter实现每行转换
	 */
	public static <From,To> List convertList(Iterable fromList,RowConverter rowConverter){
		if(fromList==null) return null;
		ArrayList toList = new ArrayList();
		for(Object o:fromList){
			toList.add(rowConverter.convertRow(o));
		}
		return toList;
	}
	
	/**
	 * 
	 * 将列表转换为一个HashMap列表，由rowConverter实现每行转换
	 */
	public static List<HashMap> convertToMapList(Iterable fromList,MapConverter mapConverter){
		if(fromList==null) return null;
		ArrayList<HashMap> toList = new ArrayList<HashMap>();
		for(Object o:fromList){
			HashMap to = new HashMap();
			mapConverter.convertRow(o,to);
			toList.add(to);
		}
		return toList;
	}
	
	/**
	 * 返回字符串，若字符串为null返回空    
	 */
	public static String nullToBlank(String s){
		if(s==null) return "";
		return s;
	}
}
