package network.optimize.tool.entity;

import java.util.Date;

public class File {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file.user_id
     *
     * @mbg.generated
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file.file_type_id
     *
     * @mbg.generated
     */
    private Long fileTypeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file.file_name
     *
     * @mbg.generated
     */
    private String fileName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file.position
     *
     * @mbg.generated
     */
    private String position;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file.id
     *
     * @return the value of file.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file.id
     *
     * @param id the value for file.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file.user_id
     *
     * @return the value of file.user_id
     *
     * @mbg.generated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file.user_id
     *
     * @param userId the value for file.user_id
     *
     * @mbg.generated
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file.file_type_id
     *
     * @return the value of file.file_type_id
     *
     * @mbg.generated
     */
    public Long getFileTypeId() {
        return fileTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file.file_type_id
     *
     * @param fileTypeId the value for file.file_type_id
     *
     * @mbg.generated
     */
    public void setFileTypeId(Long fileTypeId) {
        this.fileTypeId = fileTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file.file_name
     *
     * @return the value of file.file_name
     *
     * @mbg.generated
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file.file_name
     *
     * @param fileName the value for file.file_name
     *
     * @mbg.generated
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file.position
     *
     * @return the value of file.position
     *
     * @mbg.generated
     */
    public String getPosition() {
        return position;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file.position
     *
     * @param position the value for file.position
     *
     * @mbg.generated
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file.update_time
     *
     * @return the value of file.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file.update_time
     *
     * @param updateTime the value for file.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}