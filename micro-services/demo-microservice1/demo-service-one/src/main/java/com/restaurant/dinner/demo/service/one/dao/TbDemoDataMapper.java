package com.restaurant.dinner.demo.service.one.dao;

import com.restaurant.dinner.service.one.demo.api.pojo.po.TbDemoData;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
public interface TbDemoDataMapper {
    @Insert({
        "insert into tb_demo_data (id, demo_data)",
        "values (#{id,jdbcType=INTEGER}, #{demoData,jdbcType=VARCHAR})"
    })
    int insert(TbDemoData record);

    int insertSelective(TbDemoData record);

    @Select({
        "select",
        "id, demo_data",
        "from tb_demo_data",
        "where id = #{id,jdbcType=INTEGER}"
    })

    @ResultMap("BaseResultMap")
    TbDemoData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbDemoData record);

    @Update({
        "update tb_demo_data",
        "set demo_data = #{demoData,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TbDemoData record);
}