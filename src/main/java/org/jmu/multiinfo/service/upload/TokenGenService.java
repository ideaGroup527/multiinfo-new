package org.jmu.multiinfo.service.upload;

import org.jmu.multiinfo.dto.upload.DataToken;
import org.jmu.multiinfo.dto.upload.TokenDTO;
/**
 * 数据token缓存
 * @Title: TokenGenService.java
 * @Package org.jmu.multiinfo.service.upload
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月17日 下午2:20:06
 * @version V1.0
 *
 */
public interface TokenGenService {
public DataToken cacheData(String tokenId,Object data, TokenDTO tokenDTO);

public boolean freshData(String tokenId,Integer sheetNo);

public DataToken cacheData(String tokenId,Object data, TokenDTO tokenDTO,Integer sheetNo);
}
