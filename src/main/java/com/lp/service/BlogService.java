package com.lp.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lp.domain.Archive;
import com.lp.domain.BlogView;
import com.lp.mapping.BlogMapper;
import com.lp.service.inter.IBlogService;
import com.lp.utils.TimeTools;
import com.lp.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BlogService implements IBlogService {

    @Resource
    private BlogMapper blogMapper;


    @Override
    public BlogView adminGetBlog(int vid){
        return blogMapper.selectAdmin(vid);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "archives",key = "1"),
            @CacheEvict(value = "tagList",key = "1"),
            @CacheEvict(value = "archivePageNum",key = "1")
    })
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addBlog(BlogView blogView){
        blogView.setDate(new Date(System.currentTimeMillis()));
        blogMapper.insertBlog(blogView);
        addViewTag(blogView.getTags(),blogView.getVid());
    }
    @Override
    public PageInfo<BlogView> getBlogPage(Integer pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<BlogView> list = blogMapper.findBlog();
        return new PageInfo<BlogView>(list);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "archives",key = "1"),
            @CacheEvict(value = "tagList",key = "1")
    })
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateBlog(BlogView blogView){
        blogMapper.updateBlogView(blogView);
        updateViewTag(blogView.getTags(),blogView.getVid());
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "archives",key = "1"),
            @CacheEvict(value = "tagList",key = "1"),
            @CacheEvict(value = "archivePageNum",key = "1")
    })
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteBlogById(int vid) {
        blogMapper.deleteBlogView(vid);
    }

    @Override
    @Cacheable(value = "tagList",key = "1")
    public List<String> getTagList(){
        return blogMapper.selectTags();
    }

    @Override
    @Cacheable(value = "archives",key = "1")
    public List<Archive> getArchive(){
        return bv2Ar(blogMapper.selectArc());
    }

    @Override
    @Cacheable(value = "archivePageNum",key = "1")
    public int getArchiveNum(){
        int blogNum=blogMapper.selectBlogNum();
        return blogNum%12==0?blogNum/12:blogNum/12+1;
    }

    @Override
    public BlogView getBlog(int vid){
        return blogMapper.selectView(vid);
    }

    @Override
    public BlogView getPrevBlog(int vid){
        return blogMapper.selectPreView(vid);
    }

    @Override
    public BlogView getNextBlog(int vid) {
        BlogView blogView=null;
        try {
            blogView=blogMapper.selectNextView(vid);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return blogView;
    }

    @Override
    public List<BlogView> getBlogByTag(String tagName){
        List<BlogView> views=new ArrayList<>();
            List<Integer> vids=blogMapper.selectVidBytag(tagName);
            for (int vid:vids){
                BlogView view=blogMapper.selectTagView(vid);
                if (view!=null){
                    view.setVid(vid);
                    String monthDay= TimeTools.getEdate(view.getDate());
                    view.setMonthDay(monthDay);
                    views.add(view);
                }
            }
        return views;
    }

    private List<Archive> bv2Ar(List<BlogView> views){
        List<Archive> archives=new ArrayList<>();
        Map<Integer,Archive> years2Ar=new HashMap<>();
        for (BlogView view:views){
            Date date=view.getDate();
            view.setMonthDay(TimeTools.getEdate(date));
            int year=TimeTools.getYear(date);
            if (years2Ar.containsKey(year)){
                years2Ar.get(year).getList().add(view);
            }else {
                Archive archive=new Archive(year,new ArrayList<BlogView>());
                years2Ar.put(year,archive);
                archive.getList().add(view);
                archives.add(archive);
            }
        }
        return archives;
    }
    private void addViewTag(String tagStr,int vid){
        List<String> tagList= Tools.getTagList(tagStr);
        for (String tag:tagList){
            blogMapper.insertViewTag(tag,vid);
        }

    }
    private void updateViewTag(String tagStr,int vid){
        blogMapper.deleteViewTag(vid);
        List<String> tagList=Tools.getTagList(tagStr);
        for (String tag:tagList){
            blogMapper.insertViewTag(tag,vid);
        }

    }

}
