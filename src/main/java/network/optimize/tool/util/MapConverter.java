package network.optimize.tool.util;

import java.util.HashMap;

/**
 * 
 * @author liang.zhou
 * 将list中的每条记录转换为HashMap
 * @param <From>输入List中元素范型
 * 
 */
public interface MapConverter<From> {
	public void convertRow(From from, HashMap to);
}
