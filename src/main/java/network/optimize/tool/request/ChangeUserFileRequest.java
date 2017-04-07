package network.optimize.tool.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class ChangeUserFileRequest {
	
	@NotBlank
	private String action;
	@NotNull
	private Long File_Id;
	
	private String File_Name;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Long getFile_Id() {
		return File_Id;
	}
	public void setFile_Id(Long file_Id) {
		File_Id = file_Id;
	}
	public String getFile_Name() {
		return File_Name;
	}
	public void setFile_Name(String file_Name) {
		File_Name = file_Name;
	}
	
	

}
