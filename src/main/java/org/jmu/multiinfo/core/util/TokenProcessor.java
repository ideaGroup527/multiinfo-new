package org.jmu.multiinfo.core.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class TokenProcessor {
	  private static TokenProcessor instance = new TokenProcessor();  
	  
	  
	    protected TokenProcessor() {  
	    }  
	  
	    public static TokenProcessor getInstance() {  
	        return instance;  
	    }  
	  
	    public synchronized boolean isTokenValid(HttpServletRequest request) {  
	        return isTokenValid(request, false);  
	    }  
	  
	    public synchronized boolean isTokenValid(HttpServletRequest request,  
	            boolean reset) {  
	  
	        HttpSession session = request.getSession(false);  
	        if (session == null  )
	            return false;  
	        String saved = (String) session  
	                .getAttribute("cn.vicky.struts.action.TOKEN");  
	        if (saved == null)   
	            return false;  
	  
	        if (reset)   
	            resetToken(request);  
	        String token = request  
	                .getParameter("cn.vicky.struts.taglib.html.TOKEN");  
	        if (token == null)  
	            return false;  
	        else  
	            return saved.equals(token);   
	    }  
	  
	  
	    public synchronized void resetToken(HttpServletRequest request) {  
	        HttpSession session = request.getSession(false);  
	        if (session == null) {  
	            return;  
	        } else {  
	            session.removeAttribute("cn.vicky.struts.action.TOKEN");  
	            return;  
	        }  
	    }  
	  
	  
	    public synchronized void saveToken(HttpServletRequest request) {  
	        HttpSession session = request.getSession();  
	        String token = generateToken(request);  
	        if (token != null)  
	            session.setAttribute("cn.vicky.struts.action.TOKEN", token);  
	    }  
	  
	  
	    public synchronized String generateToken(HttpServletRequest request) {  
	        HttpSession session = request.getSession();  
	        return generateToken(session.getId());  
	    }  
	  
	  
	    public synchronized String generateToken(String id) {  
	        try {  
	            MessageDigest md = MessageDigest.getInstance("MD5");  
	            md.update(id.getBytes("UTF-8"));  
	            return toHex(md.digest());  
	        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {  
	            return null;  
	        }  
	    }  
	  
	      
	    private String toHex(byte buffer[]) {  
	        // 首先初始化一个字符数组，用来存放每个16进制字符  
	        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };  
	        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））  
	        char[] resultCharArray =new char[buffer.length * 2];  
	        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去  
	        int index = 0; 
	        for (byte b : buffer) {  
	           resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];  
	           resultCharArray[index++] = hexDigits[b& 0xf];  
	        }
	        // 字符数组组合成字符串返回  
	        String re = new String(resultCharArray);
	        return re;  
	  
	    }  
}
