package network.optimize.tool.response;

import java.util.List;

import network.optimize.tool.util.ConvertUtil;
import network.optimize.tool.util.RowConverter;



public class ListResponse<T> extends BaseResponse{
	protected List<T> data;

	public ListResponse(List dataList,RowConverter rowConverter) throws Exception{
		data = ConvertUtil.convertList(dataList, rowConverter);
	}
	
	public ListResponse() throws Exception{
		
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}
}
