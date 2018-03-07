package com.lp.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lp.domain.Archive;
import com.lp.domain.BlogView;
import com.lp.domain.Info;
import com.lp.service.inter.IBlogService;
import com.lp.service.inter.IInfoService;
import com.lp.service.inter.IProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 前端页面显示的控制器
 * 共包括archives,login,projects,tags,about,post,login这几个页面
 */
@Controller
public class FontEndCtrl {

    @Resource
    private IBlogService blogService;
    @Resource
    private IInfoService infoService;
    @Resource
    private IProjectService projectService;


    @ModelAttribute
    public void initUserInfo(Model model){
        model.addAttribute("info",infoService.getInfo());
    }

    @GetMapping("/")
    public String welcome(Model model) {
        return "index";
    }

    @GetMapping("/post/{id}")
    public String post(@PathVariable int id, Model model) {
        BlogView blogView=blogService.getBlog(id);
        BlogView prev=blogService.getPrevBlog(id);
        BlogView next=blogService.getNextBlog(id);
        model.addAttribute("prev",prev);
        model.addAttribute("next",next);
        model.addAttribute("article",blogView.getArticle());
        return "front/post";
    }
    @RequestMapping("/archives/{pageNo}")
    public String archives(@PathVariable int pageNo,Model model){
        PageHelper.startPage(pageNo,10);
        List<BlogView> views=blogService.selectArc();
        List<Archive> archiveList = blogService.getArchive(views);
        PageInfo<Archive> pageInfo=new PageInfo<Archive>(archiveList,5);
        model.addAttribute("pageInfo",pageInfo);
        return "front/archives";
    }

    @GetMapping("/projects")
    public String projects(Model model) {
        return "front/projects";
    }
    @GetMapping("/projects/{page}")
    public String projectPage(@PathVariable int page,Model model) {
        model.addAttribute("projects",projectService.getPros(page));
        model.addAttribute("pageNum",projectService.getPageNum());
        model.addAttribute("current",page);
        return "front/projects";
    }

    @GetMapping("/tags")
    public String tags(Model model) {
        model.addAttribute("tags",blogService.getTagList());
        return "front/tags";
    }
    @GetMapping("/tags/{tagName}")
    public String tagName(@PathVariable String tagName,Model model) {
        List<BlogView> views=blogService.getBlogByTag(tagName);
        model.addAttribute("views",views);
        model.addAttribute("tagName",tagName);
        return "front/tagView";
    }
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("resume",infoService.getResumeView());
        return "front/about";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        model.addAttribute("avatar",infoService.getInfo().getAvatar());
        String result = request.getParameter("result");
        if (result != null && result.equals("fail")) {
            model.addAttribute("success", 0);
        }
        return "admin/login";
    }

    @PostMapping("/login.action")
    public String doLogin(Info user, HttpServletRequest request) {
        boolean result = infoService.login(user);
        if (result) {
            infoService.addSession(request, user);
            return "redirect:/admin";
        } else {
            return "redirect:/login?result=fail";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        infoService.destroySession(request);
        return "redirect:/login";
    }
}
