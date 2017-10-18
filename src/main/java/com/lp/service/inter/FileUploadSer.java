package com.lp.service.inter;

import com.lp.domain.Info;
import com.lp.domain.UploadPic;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface FileUploadSer {
    UploadPic uploadPic(HttpServletRequest request);
    Info updateAvatar(HttpServletRequest request);
    ResponseEntity<byte[]> gainPic(String dir, String picName);
}
