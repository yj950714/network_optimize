package network.optimize.tool.mapper;

import java.util.List;

import network.optimize.tool.entity.Auth;
import network.optimize.tool.entity.AuthExample;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth
     *
     * @mbg.generated
     */
    long countByExample(AuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth
     *
     * @mbg.generated
     */
    int deleteByExample(AuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth
     *
     * @mbg.generated
     */
    int insert(Auth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth
     *
     * @mbg.generated
     */
    int insertSelective(Auth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth
     *
     * @mbg.generated
     */
    List<Auth> selectByExample(AuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth
     *
     * @mbg.generated
     */
    Auth selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Auth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Auth record);
}