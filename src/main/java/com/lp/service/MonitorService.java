package com.lp.service;

import com.lp.service.inter.IMonitorService;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;

@Service
public class MonitorService implements IMonitorService {
    @Override
   public int getFreeMemory(){
           OperatingSystemMXBean osmxb = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
           long totalVirtualMemory = osmxb.getTotalPhysicalMemorySize();
           long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
           Double compare = (freePhysicalMemorySize * 1.0 / totalVirtualMemory) * 100;
           return compare.intValue();
   }
}
