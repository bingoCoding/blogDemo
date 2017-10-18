package com.lp.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lp.domain.Project;
import com.lp.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ProjectController {

    @Resource
    private ProjectService projectService;

    @GetMapping("/{target}/{pageview}")
    public String pageview(@PathVariable String pageview,@PathVariable String target){
        return "admin/"+target+"/"+target+"_"+pageview;
    }
    @GetMapping("/{target}/{pageview}/{id}")
    public String pageView(@PathVariable String pageview,@PathVariable String target,@PathVariable String id,Model model){
        model.addAttribute("pro",projectService.getProById(id));
        return "admin/"+target+"/"+target+"_"+pageview;
    }

    @PostMapping("/project/list")
    @ResponseBody
    public PageInfo<Project> project(@RequestParam(defaultValue = "1") int pageNo, Model model){
        PageHelper.startPage(pageNo,10);
        List<Project> proList = projectService.adminGetPros();
        PageInfo<Project> pageInfo = new PageInfo<>(proList,5);
        return pageInfo;
    }


    @PostMapping("/project/add.action")
    @ResponseBody
    public int addProject(Project pro){
        return projectService.addPro(pro);
    }
    @GetMapping("/project/delete/{id}")
    @ResponseBody
    public int deletePro(@PathVariable int id){
        return projectService.deletePro(id);
    }

    @PostMapping("/project/update.action")
    @ResponseBody
    public int updatePro(@ModelAttribute("updateForm") Project pro){
        return projectService.updatePro(pro);
    }
}
