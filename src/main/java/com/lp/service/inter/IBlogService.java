package com.lp.service.inter;



import com.github.pagehelper.PageInfo;
import com.lp.domain.Archive;
import com.lp.domain.BlogView;

import java.util.List;

/**
 * 所有博客操作的service的接口
 */
public interface IBlogService {
    void addBlog(BlogView blogView);//添加博客
    BlogView adminGetBlog(int vid);//后台管理中更新博客时获取博客的markdown文本，title，tags
    PageInfo<BlogView> getBlogPage(Integer pageNum, int pageSize, String startDate, String endDate, String title);//用于后台管理中显示博客列表
    void updateBlog(BlogView blogView);//后台管理更新博客操作
    int deleteBlogById(int vid);//后台管理删除博客操作
    List<String> getTagList();//前端标签页面获取标签列表
    List<Archive> getArchive(List<BlogView> list);//前端archives页面获取archive
    List<BlogView> selectArc();
    int getArchiveNum();//前端显示获取archive页面总数
    BlogView getBlog(int vid);//前端博客显示界面获取博客html文本，对应post/id
    BlogView getPrevBlog(int vid);//前端获取上一篇博客的id和title
    BlogView getNextBlog(int vid);//前端获取下一篇博客的id和title
    List<BlogView> getBlogByTag(String tagName);//前端根据tag名称获取博客列表
}
