package com.lp.mapping;

import com.lp.domain.Project;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * mybatis的mapper
 * 映射所有项目操作的sql语句
 */
public interface ProjectMapper {

    @Select({
            "select name,url,tech,desp,date",
            "from project ",
            "limit #{st},5"
    })
    List<Project> select(@Param("st") int start) throws RuntimeException;

    @Select({
            "select id,name,url,tech,desp",
            "from project",
            "where id = #{id}",
            "limit 1"
    })
    Project selectById(@Param("id") int id) throws RuntimeException;

    @Select({
            "select id,name,url,tech,date",
            "from project"
    })
    List<Project> adminSelect() throws RuntimeException;

    @Select("select count(*) from project")
    int count() throws RuntimeException;

    @Insert({
            "insert into project(name,url,date,tech,desp) " +
                    "values(#{p.name},#{p.url},now(),#{p.tech},#{p.desp})"
    })
    int insert(@Param("p") Project project) throws RuntimeException;



    @Delete({
            "delete from project",
            "where id = #{id}",
            "limit 1"
    })
    int delete(@Param("id") int id) throws RuntimeException;


    @Update({
            "update project",
            "set name= #{p.name},url = #{p.url},",
            "tech=#{p.tech},desp=#{p.desp}",
            "where id = #{p.id}",
            "limit 1"
    })
    int Update(@Param("p") Project project) throws RuntimeException;
}
