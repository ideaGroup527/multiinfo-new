package org.jmu.multiinfo.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.jmu.multiinfo.core.dto.BaseDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public abstract class BaseController {
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
