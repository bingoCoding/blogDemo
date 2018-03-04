package com.lp.service;

import com.lp.domain.Project;
import com.lp.mapping.ProjectMapper;
import com.lp.service.inter.IProjectService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProjectService implements IProjectService {

    @Resource
    private ProjectMapper projectMapper;

    @Override
    @Cacheable(value = "projects",condition = "#page==1",key = "1")
    public List<Project> getPros(int page){
        int start = (page - 1) * 5;
        return projectMapper.select(start);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "projects",key = "1"),
            @CacheEvict(value = "projectPageNum",key = "1")
    })
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public int addPro(Project project){
        return projectMapper.insert(project);
    }

    @Override
    public List<Project> adminGetPros(){
        return projectMapper.adminSelect();
    }

    @Override
    public int adminGetPageNum(){
        int count = projectMapper.count();
        return count % 10 == 0 ? count / 10 : count / 10 + 1;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "projects",key = "1"),
            @CacheEvict(value = "projectPageNum",key = "1")
    })
    public int deletePro(int id){
         return  projectMapper.delete(id);
    }

    @Override
    public Project getProById(String idStr){
        int id = Integer.valueOf(idStr);
        return  projectMapper.selectById(id);
    }

    @Override
    @CacheEvict(value = "projects",key = "1")
    public int updatePro(Project project) {
         return projectMapper.Update(project);
    }

    @Override
    @Cacheable(value = "projectPageNum",key = "1")
    public int getPageNum(){
        int count = projectMapper.count();
        return count % 5 == 0 ? count / 5 : count / 5 + 1;
    }
}
