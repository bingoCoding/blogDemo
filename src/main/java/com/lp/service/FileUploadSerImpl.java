package com.lp.service;

import com.lp.domain.Info;
import com.lp.domain.SystemSetting;
import com.lp.domain.UploadPic;
import com.lp.service.inter.FileUploadSer;
import com.lp.service.inter.IInfoService;
import com.lp.utils.FileTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Slf4j
@Service
public class FileUploadSerImpl implements FileUploadSer {

    @Resource
    private IInfoService infoSer;

    private SystemSetting setting;


    @Override
    public UploadPic uploadPic(HttpServletRequest request){

        String picUrl="";
        try {
            picUrl= FileTools.updatePic("/pic/",setting.getPicHome(),request);
        } catch (Exception e) {
           log.warn("上传图片时发生错误:"+e.getLocalizedMessage());
        }
        final UploadPic upload = new UploadPic();
        if ("".equals(picUrl)){
            upload.setSuccess(0);
            upload.setMessage("Upload picture fail!");
            upload.setUrl("");
            return upload;
        }
        upload.setSuccess(1);
        upload.setMessage("Upload picture success!");
        upload.setUrl(picUrl);
        return upload;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Info updateAvatar(HttpServletRequest request){
        String url=uploadPic(request).getUrl();
        if (!"".equals(url)){
            infoSer.updateAvatar(url);
        }
        return infoSer.getInfo();
    }

    @Override
    public ResponseEntity<byte[]> gainPic(String dir, String picName){
        File file=new File(setting.getPicHome()+File.separatorChar+dir+File.separatorChar+picName);
        byte[] bytes;
        try {
            bytes= FileTools.readFileToByteArray(file);
        } catch (Exception e) {
            log.warn(e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", picName);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    public SystemSetting getSetting() {
        return setting;
    }

    public void setSetting(SystemSetting setting) {
        this.setting = setting;
    }
}
