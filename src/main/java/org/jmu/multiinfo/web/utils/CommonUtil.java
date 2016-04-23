package org.jmu.multiinfo.web.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
	/**
	 * 验证是email格式是否合法
	 * 
	 * @param email
	 * @return 是否合法
	 */
	public static boolean isemail(String email) {
		boolean tag = true;
		final String pattern1 = "^([a-z0-9A-Z]+[-|//.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?//.)+[a-zA-Z]{2,}$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}

	/**
	 * 验证是否为手机号
	 * 
	 * @param str
	 * @return 是否合法
	 */
	public static boolean isNumer(String str) {
		Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断非空字符串
	 * 
	 * @param str
	 * @return 是否非空
	 */
	public static boolean isNull(String str) {
		return str != null && str.length() > 0;
	}

	/**
	 * 上传文件，默认路径为upload
	 * 
	 * @param filePath
	 * @param content
	 * @return 返回是否保存成功
	 */
	public static boolean saveFile(String filePath, byte[] content) {
		boolean flag = true;
		BufferedOutputStream bos = null;
		try {
			File file = new File(filePath);
			// 判断文件路径是否存在
			if (!file.getParentFile().exists()) {
				// 文件路径不存在时，创建保存文件所需要的路径
				file.getParentFile().mkdirs();
			}
			// 创建文件（这是个空文件，用来写入上传过来的文件的内容）
			file.createNewFile();
			bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(content);
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		} finally {
			if (null != bos) {
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	/**
	 * 上传文件，并返回文件对象
	 * 
	 * @param filePath
	 * @param content
	 * @return 返回是文件
	 */
	public static File saveFileAndReturn(String filePath, byte[] content) {
		BufferedOutputStream bos = null;
		File file = null;
		try {
			file = new File(filePath);
			// 判断文件路径是否存在
			if (!file.getParentFile().exists()) {
				// 文件路径不存在时，创建保存文件所需要的路径
				file.getParentFile().mkdirs();
			}
			// 创建文件（这是个空文件，用来写入上传过来的文件的内容）
			file.createNewFile();
			bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != bos) {
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return file;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param path
	 * @return 若存在则返回file对象
	 */
	public static File fileExists(String path) {
		File file = new File(path);
		if (file.exists()) {
			return file;// 如果存在输出结果
		} else {
			return null;
		}
	}

	/**
	 * 获取MD5值
	 * 
	 * @param file
	 * @return
	 */
	public static String getMD5(byte[] source) {
		String s = null;
		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				// >>> 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	private static boolean isMatch(String regex, String orginal) {
		if (orginal == null || orginal.trim().equals("")) {
			return false;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher isNum = pattern.matcher(orginal);
		return isNum.matches();
	}

	/***
	 * 正整数
	 * 
	 * @param orginal
	 * @return
	 */
	public static boolean isPositiveInteger(String orginal) {
		return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
	}

	/***
	 * 负整数
	 * 
	 * @param orginal
	 * @return
	 */
	public static boolean isNegativeInteger(String orginal) {
		return isMatch("^-[1-9]\\d*", orginal);
	}

	
	/***
	 * 整数
	 * 
	 * @param orginal
	 * @return
	 */
	public static boolean isWholeNumber(String orginal) {
		return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
	}

	/***
	 * 正小数
	 * @param orginal
	 * @return
	 */
	public static boolean isPositiveDecimal(String orginal) {
		return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
	}

	/***
	 * 负小数
	 * @param orginal
	 * @return
	 */
	public static boolean isNegativeDecimal(String orginal) {
		return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
	}

	/***
	 * 小数
	 * @param orginal
	 * @return
	 */
	public static boolean isDecimal(String orginal) {
		return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
	}

	
	/***
	 * 实数
	 * @param orginal
	 * @return
	 */
	public static boolean isRealNumber(String orginal) {
		return isWholeNumber(orginal) || isDecimal(orginal);
	}
	
	/***
	 * 是否是科学计数法表示的数
	 * @param orginal
	 * @return
	 */
	public static boolean isScienceNumber(String orginal) {
		return isMatch("^(([-+]{0,1}\\d+.?\\d*)[Ee]{1}(-?\\d+))$", orginal);
	}
	
	/***
	 * 是否是逗号型数值
	 * @param orginal
	 * @return
	 */
	public static boolean isVirgNumber(String orginal) {
		return isMatch("^([-+]{0,1}[1-9]([0-9]{1,2})?(,[0-9]{3})*(\\.[0-9]+)?)$", orginal);
	}
}
