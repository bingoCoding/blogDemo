package com.lp.controller;

import com.lp.domain.Info;
import com.lp.service.InfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class InfoController {

    @Resource
    private InfoService infoService;


    @GetMapping("/info")
    public String info(Model model){
        Info info = infoService.getInfo();
        model.addAttribute("info", info);
        return "/admin/info/info";
    }

    @PostMapping("/info.action")
    @ResponseBody
    public Model updateInfo(@ModelAttribute("infoForm") Info info, Model model) {
        boolean result = infoService.updateInfo(info);
        model.addAttribute("targetUrl", "/admin/info/info");
        model.addAttribute("info", info);
        if (result) {
            model.addAttribute("result", 1);
        } else {
            model.addAttribute("result", 0);
        }
        return model;
    }

    @PostMapping("/pass.action")
    public String passModify(@RequestParam String old_pass, @RequestParam String new_pass, HttpServletRequest request){
        int result = infoService.modifyPw(old_pass, new_pass);
        if (result == 0) {
            infoService.destroySession(request);
        }
        return "redirect:/admin/info?result=" + result;
    }

    @GetMapping("/resume")
    public String resume(Model model) {
        model.addAttribute("md", infoService.getResumeMd());
        return "/admin/resume";
    }

    @PostMapping("/resume.action")
    public String resumeUpdate(@ModelAttribute("resumeForm") Info info){
        infoService.updateResume(info);
        return "redirect:/admin/resume";
    }
}
