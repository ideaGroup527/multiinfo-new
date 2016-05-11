package org.jmu.multiinfo.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.jmu.multiinfo.core.dto.BaseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class BaseController {
	
	
 	@ExceptionHandler(HttpMessageNotReadableException.class)  
 	@ResponseStatus(value = HttpStatus.BAD_REQUEST)  
 	@ResponseBody
 	public BaseDTO handleUnexpectedServerError(HttpMessageNotReadableException e) {  
 		BaseDTO baseDTO = new BaseDTO();
        baseDTO.setRet_err(e.getMessage());
        baseDTO.setRet_msg("http请求错误");
        baseDTO.setRet_code("-1");
        return baseDTO;
 	}  
 	
	 	@ExceptionHandler
		@ResponseBody
	    public BaseDTO exception(HttpServletRequest request, Exception e) {  
	 		BaseDTO baseDTO = new BaseDTO();
	        request.setAttribute("exceptionMessage", e.getMessage());  
	        baseDTO.setRet_err(e.getMessage());
	        baseDTO.setRet_code("-1");
	        return baseDTO;
	    }  

}
