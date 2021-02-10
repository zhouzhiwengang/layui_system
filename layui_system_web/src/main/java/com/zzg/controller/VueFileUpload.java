package com.zzg.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zzg.common.entity.Result;

@RestController
@RequestMapping("/vue/file")
public class VueFileUpload {
	@PostMapping(value = "/upload", produces = {"application/json;charset=UTF-8"})
	public Result uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {
		 String old_name = file.getOriginalFilename(); //原始文件名
         String ext_name = old_name.substring(old_name.lastIndexOf(".")); //扩展名
         String new_name = UUID.randomUUID().toString()+ext_name; //利用UUID生成新文件名
         String path = request.getRealPath("/upload/photos"); //上传位置：工程/static/upload/photos
         File fx = new File(path);
         if(!fx.exists()) {
             fx.mkdirs();
         }
         File n = new File(fx, new_name);
         file.transferTo(n);
	    return Result.ok().setDatas("path", n.getPath());
	}
}
