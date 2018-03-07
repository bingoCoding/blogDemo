package com.lp.mapping;

import com.lp.domain.BlogView;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * mybatis的mapper
 * 映射所有博客操作的sql语句
 */
public interface BlogMapper {
      @Select("<script>select vid,title,tags,to_char(date,'yyyy-MM-dd HH24:mm:ss') date "+
              "from blog_view where to_char(date,'yyyy-MM-dd')>=#{startDate} and " +
              "to_char(date,'yyyy-MM-dd') <![CDATA[<=]]> #{endDate}" +
              "<if test=\"title !=null and title !='' \">" +
              " and title like #{title}" +
              "</if>" +
              " order by date desc </script>")
      List<BlogView> findBlog(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("title") String title);

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
      @Options(keyProperty="bv.vid",useGeneratedKeys=true,keyColumn = "vid")
      int insertBlog(@Param("bv") BlogView blogView);

      @Insert("<script>insert into view_tag (name,vid) values" +
              "<foreach collection=\"list\" item=\"item\" index= \"index\" separator =\",\">" +
              "(#{item},#{id})" +
              "</foreach>" +
              "</script>")
      int insertViewTag(@Param("list") List<String> tagName, @Param("id") int vid);

      @Delete("delete from view_tag where vid = #{vid}")
      int deleteViewTag(@Param("vid") int vid);

      @Delete("delete from blog_view where vid =#{vid}")
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
