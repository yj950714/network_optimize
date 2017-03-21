package network.optimize.tool.response;

import java.util.List;

import network.optimize.tool.util.ConvertUtil;
import network.optimize.tool.util.RowConverter;

import com.github.pagehelper.Page;

/**
 * 生成列表response，自动返回页码和总页数
 * @author liang.zhou
 *
 * @param <T> 每行初始类型
 */
public class PageResponse<T> extends BaseResponse{
	private int pageNum = 0;
	private int pages = 0;
	protected List<T> data;

	public PageResponse() {
	}

	/**
	 * 经过转换将list转换成返回map list
	 * @param dataList 源数据
	 * @param mapConverter 转换器，用来进行转换数据
	 * @throws Exception 
	 */
	public PageResponse(Page dataList,RowConverter rowConverter) throws Exception{
		pageNum = dataList.getPageNum();
		pages = dataList.getPages();
		data = ConvertUtil.convertList(dataList, rowConverter);
	}
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}
}
