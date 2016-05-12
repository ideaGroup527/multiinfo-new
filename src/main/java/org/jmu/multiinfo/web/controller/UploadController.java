package org.jmu.multiinfo.web.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.jmu.multiinfo.core.controller.BaseController;
import org.jmu.multiinfo.core.dto.DataVariety;
import org.jmu.multiinfo.core.util.TokenProcessor;
import org.jmu.multiinfo.dto.basestatistics.DataDTO;
import org.jmu.multiinfo.dto.upload.DataToken;
import org.jmu.multiinfo.dto.upload.ExcelDTO;
import org.jmu.multiinfo.dto.upload.TextDTO;
import org.jmu.multiinfo.dto.upload.TokenDTO;
import org.jmu.multiinfo.service.upload.TokenGenService;
import org.jmu.multiinfo.service.upload.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/***
 * 文件上传
 * @Title: UploadAction.java 
 * @Package org.jmu.multiinfo.component.controller 
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2015年11月3日 下午3:53:33 
 * @version V1.0
 */
@Api(value = "文件上传",tags="文件上传")  
@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public UploadService uploadService;
	
	@Autowired
	public TokenGenService tokenGenService;
	
	
	/***
	 * 根据token返回数据
	 * @param request
	 * @param response
	 * @param session
	 * @param token
	 * @param sheetNo
	 * @param isMultiSheet
	 * @param isFirstRowVar
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "上传token", notes = "返回数据对象",httpMethod="GET")  
	  @ApiResponses(value = {  
	            @ApiResponse(code = 200, message = "上传成功", response = DataToken.class), 
	            @ApiResponse(code = 400, message = "入参有误"),
	            @ApiResponse(code = 500, message = "内部报错")}  
	  )  
	@RequestMapping(value= "/file.do")
	@ResponseBody
	public Object uploadFile(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(required = true, name = "token", value = "token") @RequestParam("token") String token,
			@ApiParam(required = false, name = "sheetNo", value = "sheetNo",defaultValue="0") @RequestParam(required=false,value="sheetNo",defaultValue="0") int sheetNo,
			@ApiParam(required = true, name = "isMultiSheet", value = "是否是多sheet",defaultValue="false") @RequestParam("isMultiSheet") boolean isMultiSheet,
			@ApiParam(required = false, name = "isFirstRowVar", value = "是否第一行为变量") @RequestParam(required = false,value="isFirstRowVar") boolean isFirstRowVar) throws Exception{
		
		if(isMultiSheet){
			DataToken dataToken = tokenGenService.cacheData(token, null,null,sheetNo);
			if(dataToken.getData() == null){
				dataToken = tokenGenService.cacheData(token, null,null);
				ExcelDTO excelDto = (ExcelDTO)dataToken.getData();
				String tempFileName = excelDto.getTempFileName();
				File temp = new File(tempFileName);
				excelDto =  uploadService.readExcel(temp, excelDto.getFileName(), sheetNo, isFirstRowVar);
				excelDto.setTempFileName(tempFileName);
				dataToken.setData(excelDto);
				tokenGenService.freshData(token, sheetNo);
				
				 TokenDTO tokenDTO = new TokenDTO();
				 tokenDTO.setIsMultiSheet(true);
				 tokenDTO.setCreateTime(System.nanoTime());
			 tokenGenService.cacheData(token, excelDto,tokenDTO,sheetNo);
			 }
			return dataToken.getData();
		}else{
			DataToken dataToken = tokenGenService.cacheData(token, null,null);
			return dataToken.getData();
		}
		
		
	}
	
	/***
	 * 上传excel
	 * @param request
	 * @param response
	 * @param session
	 * @param file
	 * @param isFirstRowVar
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "上传excel", notes = "返回token对象", httpMethod="POST")  
	  @ApiResponses(value = {  
	            @ApiResponse(code = 200, message = "上传成功", response = TokenDTO.class),  
	            @ApiResponse(code = 400, message = "入参有误"),
	            @ApiResponse(code = 500, message = "内部报错")}  
	  )  
	@RequestMapping(value="/excel.do",method=RequestMethod.POST)
	@ResponseBody
	public TokenDTO uploadExcel(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(required = true, name = "data_file", value = "excel文件") @RequestPart("data_file") MultipartFile file,
			@ApiParam(required = false, name = "isFirstRowVar", value = "是否第一行为变量") @RequestParam(required = false,value="isFirstRowVar") boolean isFirstRowVar) throws Exception{
		TokenDTO tokenDTO = new TokenDTO();
		Long createTime = System.nanoTime();
		//生成token
		String token = TokenProcessor.getInstance()
				.generateToken(file.getOriginalFilename()+createTime+"");
		logger.debug("token:"+token);
		logger.debug("createTime:"+file.getOriginalFilename()+createTime);
		tokenDTO.setToken(token);
		tokenDTO.setCreateTime(createTime);
		
		String prefix = request.getServletContext().getRealPath("/upload");
		String fileName = prefix+File.separator+createTime+"";
		File temp = new File(fileName);
		
		file.transferTo(temp);
		ExcelDTO  data = uploadService.jdeExcelNum(temp,file.getOriginalFilename());
		data.setTempFileName(fileName);
		if(1==data.getSheetNum()){
			tokenDTO.setIsMultiSheet(false);
			 data =  uploadService.readExcel(temp, file.getOriginalFilename(), 0, isFirstRowVar);
			 FileUtils.deleteQuietly(temp);
		}else{
			tokenDTO.setIsMultiSheet(true);
		}
		tokenDTO.setFileName(data.getFileName());
		tokenDTO.setSheetNameList(data.getSheetNameList());
		tokenDTO.setVersion(data.getVersion());
		tokenDTO.setSheetNum(data.getSheetNum());
		tokenGenService.cacheData(token, data,tokenDTO);
		return tokenDTO;
	}
	
	/***
	 * 上传dat、data 二进制文本类型
	 * @param request
	 * @param response
	 * @param session
	 * @param file
	 * @param isFirstRowVar
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "上传text", notes = "返回token对象",httpMethod="POST")  
	  @ApiResponses(value = {  
	            @ApiResponse(code = 200, message = "上传成功", response = TokenDTO.class), 
	            @ApiResponse(code = 400, message = "入参有误"),
	            @ApiResponse(code = 500, message = "内部报错")}  
	  )  
	@RequestMapping(value="text.do",method=RequestMethod.POST)
	@ResponseBody
	public TokenDTO uploadText(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(required = true, name = "data_file", value = "text文件") @RequestPart("data_file") MultipartFile file,
			@ApiParam(required = false, name = "isFirstRowVar", value = "是否第一行为变量") @RequestParam(required = false,value="isFirstRowVar") boolean isFirstRowVar,
			@ApiParam(required = false, name = "charset", value = "文本编码",example="UTF-8",defaultValue="UTF-8") @RequestParam(required = false,value="charset",defaultValue="UTF-8") String charset)throws Exception{
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
		file.transferTo(temp);
		TextDTO data= uploadService.readText(temp,file.getOriginalFilename(),isFirstRowVar,charset);
		FileUtils.deleteQuietly(temp);
		tokenGenService.cacheData(token, data,tokenDTO);
		
		
		return tokenDTO;
	}
	
	@ApiOperation(value = "二维入参无法生成api。展示一下DataDTO", notes = "返回单个DataDTO对象",httpMethod="GET")  
	  @ApiResponses(value = {  
	            @ApiResponse(code = 200, message = "上传成功", response = DataDTO.class), 
	            @ApiResponse(code = 400, message = "入参有误"),
	            @ApiResponse(code = 500, message = "内部报错")}  
	  )  
	@RequestMapping(value="showDataDTO.do",method=RequestMethod.GET)
	@ResponseBody
	public DataDTO showDataDTO(){
		DataDTO i00 =new DataDTO();
		i00.setData("地点");
		i00.setPosition("A1");
		i00.setPositionDes("A,1");
		i00.setTypeDes("字符串型");
		i00.setType(DataVariety.DATA_TYPE_STRING);
		return i00;
		
	}
	
}
