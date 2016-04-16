package org.jmu.multiinfo.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jmu.multiinfo.core.controller.BaseController;
import org.jmu.multiinfo.core.dto.BaseDTO;
import org.jmu.multiinfo.core.util.TokenProcessor;
import org.jmu.multiinfo.dto.upload.DataToken;
import org.jmu.multiinfo.dto.upload.ExcelDTO;
import org.jmu.multiinfo.dto.upload.TextDTO;
import org.jmu.multiinfo.dto.upload.TokenDTO;
import org.jmu.multiinfo.service.upload.TokenGenService;
import org.jmu.multiinfo.service.upload.UploadService;
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
@RequestMapping("/upload.do")
public class UploadController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public UploadService uploadService;
	
	@Autowired
	public TokenGenService tokenGenService;
	
	@RequestMapping(params = { "method=file" })
	@ResponseBody
	public Object uploadFile(HttpServletRequest request, HttpServletResponse response,HttpSession session,
			@RequestParam("token") String token) throws Exception{
		DataToken dataToken = tokenGenService.cacheData(token, null,null);
		return dataToken.getData();
	}
	
	
	@RequestMapping(params = { "method=excel" },method=RequestMethod.POST)
	@ResponseBody
	public TokenDTO uploadExcel(HttpServletRequest request, HttpServletResponse response,HttpSession session,
			@RequestParam("data_file") MultipartFile file,@RequestParam(required=false,value="sheetNo",defaultValue="0") int sheetNo,
			@RequestParam(required = false,value="isFirstRowVar") boolean isFirstRowVar) throws Exception{
		TokenDTO tokenDTO = new TokenDTO();
		Long createTime = System.nanoTime();
		//生成token
		String token = TokenProcessor.getInstance()
				.generateToken(file.getOriginalFilename()+createTime+"");
		tokenDTO.setToken(token);
		tokenDTO.setCreateTime(createTime);
		
		String prefix = request.getServletContext().getRealPath("/upload");
		String fileName = prefix+File.separator+createTime+"";
		File temp = new File(fileName);
		FileOutputStream fos = FileUtils.openOutputStream(temp); 
		IOUtils.copy(file.getInputStream(), fos); 
		ExcelDTO  data = uploadService.readExcel(temp,file.getOriginalFilename(),sheetNo,isFirstRowVar);
		fos.close();
		FileUtils.deleteQuietly(temp);
		tokenGenService.cacheData(token, data,tokenDTO);
		
		return tokenDTO;
	}
	
	
	
	@RequestMapping(params = { "method=text" },method=RequestMethod.POST)
	@ResponseBody
	public TokenDTO uploadText(HttpServletRequest request, HttpServletResponse response,HttpSession session,
			@RequestParam("data_file") MultipartFile file,@RequestParam(required = false,value="isFirstRowVar") boolean isFirstRowVar)throws Exception{
		TokenDTO tokenDTO = new TokenDTO();
		Long createTime = System.nanoTime();
		//生成token
		String token = TokenProcessor.getInstance()
				.generateToken(file.getOriginalFilename()+createTime+"");
		tokenDTO.setToken(token);
		tokenDTO.setCreateTime(createTime);
		
		String prefix = request.getServletContext().getRealPath("/upload");
		String fileName = prefix+File.separator+createTime+"";
		File temp = new File(fileName);
		FileOutputStream fos = FileUtils.openOutputStream(temp); 
		IOUtils.copy(file.getInputStream(), fos); 
		TextDTO data= uploadService.readText(temp,file.getOriginalFilename(),isFirstRowVar);
		fos.close();
		FileUtils.deleteQuietly(temp);
		tokenGenService.cacheData(token, data,tokenDTO);
		
		
		return tokenDTO;
	}
}
