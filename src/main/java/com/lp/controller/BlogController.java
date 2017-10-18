package com.lp.controller;

import com.github.pagehelper.PageInfo;
import com.lp.domain.BlogView;
import com.lp.service.inter.IBlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 后台管理中博客管理页面
 * 包括对博客的增,删,改
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Resource
    private IBlogService blogService;
    /**
     * 页面跳转
     */
    @GetMapping("/blog")
    public String blogList() {
        return "admin/blog/blog_list";
    }

    @PostMapping("/blog/list")
    @ResponseBody
    public PageInfo<BlogView> list(@RequestParam(defaultValue = "1")Integer pageNum, @RequestParam(defaultValue = "0")Integer pageSize){
        return blogService.getBlogPage(pageNum,pageSize);
    }

    /**
     * 后台管理中修改博客页面的控制器
     * @param id    要修改的博客的id
     * @return
     * */
    @GetMapping("/blog/update/{id:\\d+}")
    public String toUpdateBlog(@PathVariable int id,Model model){
        BlogView blogView=blogService.adminGetBlog(id);
        if (blogView==null){
            return "error";
        }else {
            blogView.setVid(id);
            model.addAttribute("blog",blogView);
            return "admin/blog/blog_update";
        }
    }

    /**
     * 更新博客的表单控制器
     *
     * @param view 表单中提交的博客信息,包括id,md页面，和md转成的html页面
     * @return     templates下的result页面，用于提示是否保存博客成功
     */
    @PostMapping("/update.action")
    public String update(@ModelAttribute("blogForm") BlogView view,Model model){
        blogService.updateBlog(view);
        return "redirect:/post/"+view.getVid();
    }

    /**
     * 删除博客的控制器
     *
     * @param id    要删除的博客id
     */
    @GetMapping("/blog/del/{id}")
    public String delete(@PathVariable int id,Model model){
        blogService.deleteBlogById(id);
        return "redirect:/admin/blog";
    }

    /**
     *后台管理中添加博客页面的控制器
     * @return
     */
    @GetMapping("/blog/add")
    public String toBlogAdd() {
        return "admin/blog/blog_add";
    }

    /**
     * 添加博客的表单控制器
     * @param view  表单中提交的博客信息,包括标题，标签，md页面，和md转成的html页面
     * @return
     */
    @PostMapping("/post.action")
    public String postAction(@ModelAttribute("blogForm") BlogView view, Model model){
        blogService.addBlog(view);
        return "redirect:/admin/blog";
    }
}
