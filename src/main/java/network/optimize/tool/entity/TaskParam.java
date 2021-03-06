package network.optimize.tool.entity;

public class TaskParam {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_param.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_param.task_id
     *
     * @mbg.generated
     */
    private Long taskId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_param.param_id
     *
     * @mbg.generated
     */
    private Long paramId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_param.param_value
     *
     * @mbg.generated
     */
    private String paramValue;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_param.id
     *
     * @return the value of task_param.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_param.id
     *
     * @param id the value for task_param.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_param.task_id
     *
     * @return the value of task_param.task_id
     *
     * @mbg.generated
     */
    public Long getTaskId() {
        return taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_param.task_id
     *
     * @param taskId the value for task_param.task_id
     *
     * @mbg.generated
     */
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_param.param_id
     *
     * @return the value of task_param.param_id
     *
     * @mbg.generated
     */
    public Long getParamId() {
        return paramId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_param.param_id
     *
     * @param paramId the value for task_param.param_id
     *
     * @mbg.generated
     */
    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_param.param_value
     *
     * @return the value of task_param.param_value
     *
     * @mbg.generated
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_param.param_value
     *
     * @param paramValue the value for task_param.param_value
     *
     * @mbg.generated
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
}