package network.optimize.tool.util;

/**
 * 
 * @author liang.zhou
 * 将list中的每条记录转换为另一种记录
 * @param <From>输入类
 * @param <To>输出类
 * 
 */
public interface RowConverter<From,To> {
	public To convertRow(From from);
}
