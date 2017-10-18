package com.lp.service.inter;


import com.lp.domain.Project;

import java.util.List;

/**
 * project的service接口
 */
public interface IProjectService {
    List<Project> getPros(int page);//前端project页面获取项目列表
    int addPro(Project project);//后台管理添加项目
    List<Project> adminGetPros();//后台管理中获取项目列表
    int adminGetPageNum();//后台管理中获取项目页面总数量
    int getPageNum();//前端获取项目页面总数
    int deletePro(int id);//后台管理删除项目
    Project getProById(String idStr);//后台管理获取项目所有信息
    int updatePro(Project project);//后台管理更新博客信息
}
