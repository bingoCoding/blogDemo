package com.lp.mapping;

import com.lp.domain.BlogView;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * mybatis的mapper
 * 映射所有博客操作的sql语句
 */
public interface BlogMapper {
      @Select({
              "select vid,title,tags",
              "from blog_view"
      })
      List<BlogView> findBlog();

      @Select("select count(*) from blog_view")
      int selectBlogNum();

      @Select("select distinct name from view_tag")
      @ResultType(String.class)
      List<String> selectTags();

      @Select({"select vid,date,title",
              "from blog_view",
              "order by date desc"})
      List<BlogView> selectArc();

      @Select({
              "select title,tags,md",
              "from blog_view",
              "where vid = #{id}",
              "limit 1"
      })
      BlogView selectAdmin(@Param("id") int id);

      @Select({
              "select title,article",
              "from blog_view",
              "where vid = #{id}",
              "limit 1"
      })
      BlogView selectView(@Param("id") int id);

      @Select({
              "select vid,title ",
              "from blog_view",
              "where vid < #{id}",
              "order by vid desc",
              "limit 1"
      })
      BlogView selectPreView(@Param("id") int vid);

      @Select({
              "select vid,title ",
              "from blog_view",
              "where vid > #{id}",
              "limit 1"
      })
      BlogView selectNextView(@Param("id") int vid);

      @Select({
              "select distinct vid",
              "from view_tag",
              "where name = #{tag}"
      })
      List<Integer> selectVidBytag(@Param("tag") String tagName);

      @Select({
              "select date,title",
              "from blog_view",
              "where vid = #{vid}",
              "limit 1"
      })
      BlogView selectTagView(@Param("vid") int vid);

      @Insert({"insert into blog_view " ,
              "(date,title,article,tags,md) " ,
              "values(#{bv.date},#{bv.title}," ,
              "#{bv.article},#{bv.tags},#{bv.md})"})
      @SelectKey(before=false,keyProperty="bv.vid",resultType=Integer.class,
              statementType= StatementType.STATEMENT,statement="SELECT LAST_INSERT_ID() AS id")
      int insertBlog(@Param("bv") BlogView blogView);

      @Insert("insert ignore into view_tag (name,vid) values(#{tn},#{id})")
      int insertViewTag(@Param("tn") String tagName, @Param("id") int vid);

      @Delete("delete from view_tag where vid = #{vid}")
      int deleteViewTag(@Param("vid") int vid);

      @Delete("delete from blog_view where vid =#{vid} limit 1")
      int deleteBlogView(@Param("vid") int vid);

      @Update({
            "update blog_view",
              "set title = #{bv.title},",
              "tags = #{bv.tags},",
              "md = #{bv.md},",
              "article = #{bv.article}",
              "where vid = #{bv.vid}"
      })
      void updateBlogView(@Param("bv") BlogView blogView);

}
