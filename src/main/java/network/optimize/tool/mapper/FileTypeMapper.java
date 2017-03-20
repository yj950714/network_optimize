package network.optimize.tool.mapper;

import java.util.List;

import network.optimize.tool.entity.FileType;
import network.optimize.tool.entity.FileTypeExample;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FileTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_type
     *
     * @mbg.generated
     */
    long countByExample(FileTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_type
     *
     * @mbg.generated
     */
    int deleteByExample(FileTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_type
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_type
     *
     * @mbg.generated
     */
    int insert(FileType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_type
     *
     * @mbg.generated
     */
    int insertSelective(FileType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_type
     *
     * @mbg.generated
     */
    List<FileType> selectByExample(FileTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_type
     *
     * @mbg.generated
     */
    FileType selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_type
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") FileType record, @Param("example") FileTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_type
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") FileType record, @Param("example") FileTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_type
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(FileType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_type
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(FileType record);
}