package org.jmu.multiinfo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jmu.multiinfo.base.dto.BaseDto;
import org.jmu.multiinfo.service.UploadService;
import org.jmu.multiinfo.upload.dto.ExcelDto;
import org.jmu.multiinfo.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


/***
 * 
* @Title: UploadAction.java 
* @Package org.jmu.multiinfo.component.controller 
* @Description: 文件上传
* @author  <a href="mailto:www_1350@163.com">Absurd</a>
* @date 2015年11月3日 下午3:53:33 
* @version V1.0
 */
@Controller
@RequestMapping("/data")
public class UploadController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public UploadService uploadService;
	
	@RequestMapping(value = "/file",method=RequestMethod.POST)
	@ResponseBody
	public BaseDto uploadFile(HttpServletRequest request, HttpServletResponse response,HttpSession session,
			@RequestParam("data_file") MultipartFile file,@RequestParam(required=false,value="sheetNo",defaultValue="0") int sheetNo) throws IOException{
		logger.warn(CommonUtil.getFullURL(request));
		Map<String, Object> map = new HashMap<String, Object>();
		BaseDto baseDto = new BaseDto();
		baseDto.setRet_code("0");
		
		FileOutputStream fos = FileUtils.openOutputStream(new File(file.getOriginalFilename())); 
		IOUtils.copy(file.getInputStream(), fos); 
		ExcelDto  data = uploadService.readExcel(new File(file.getOriginalFilename()),sheetNo);
		baseDto.setData(data);
		return baseDto;
	}
}
