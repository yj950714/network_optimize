package network.optimize.tool.mapper;

import java.util.List;

import network.optimize.tool.entity.TaskType;
import network.optimize.tool.entity.TaskTypeExample;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_type
     *
     * @mbg.generated
     */
    long countByExample(TaskTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_type
     *
     * @mbg.generated
     */
    int deleteByExample(TaskTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_type
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_type
     *
     * @mbg.generated
     */
    int insert(TaskType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_type
     *
     * @mbg.generated
     */
    int insertSelective(TaskType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_type
     *
     * @mbg.generated
     */
    List<TaskType> selectByExample(TaskTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_type
     *
     * @mbg.generated
     */
    TaskType selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_type
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TaskType record, @Param("example") TaskTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_type
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TaskType record, @Param("example") TaskTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_type
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TaskType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_type
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TaskType record);
}