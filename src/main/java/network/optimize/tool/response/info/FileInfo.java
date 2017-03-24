package network.optimize.tool.response.info;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 获取文件信息
 * @author jie.yao
 *
 */
public class FileInfo {

	private Long id;
	private Long userId;
	private Long fileTypeId;
	private String fileName;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date updateTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getFileTypeId() {
		return fileTypeId;
	}
	public void setFileTypeId(Long fileTypeId) {
		this.fileTypeId = fileTypeId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
