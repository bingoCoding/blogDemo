package com.lp.service.inter;

/**
 * 监控页面的service接口
 */
public interface IMonitorService {
   // List<Visiter> getVisiters();//获取访问人数列表
    int getFreeMemory();//获取剩余内存百分比
}
