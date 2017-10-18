package com.lp.controller;

import com.lp.service.inter.IMonitorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

/**
 * 后台管理中监控页面
 * 目前只包括内存占用的监控
 */
@Controller
public class MonitorController {
    @Resource
    private IMonitorService monitorService;

    @GetMapping({"/admin","/admin/minitor"})
    public String monitor(Model model){
        model.addAttribute("freeMemory", monitorService.getFreeMemory());
        return "admin/monitor/monitor";
    }
}
