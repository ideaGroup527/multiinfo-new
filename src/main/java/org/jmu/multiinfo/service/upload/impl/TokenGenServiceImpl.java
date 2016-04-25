package org.jmu.multiinfo.service.upload.impl;

import org.jmu.multiinfo.dto.upload.DataToken;
import org.jmu.multiinfo.dto.upload.TokenDTO;
import org.jmu.multiinfo.service.upload.TokenGenService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TokenGenServiceImpl implements TokenGenService{

	@Cacheable(key="#tokenId",value="tokenCache")
	@Override
	public DataToken cacheData(String tokenId, Object data, TokenDTO tokenDTO) {
		DataToken dataToken = new DataToken();
		dataToken.setData(data);
		dataToken.setCreateTime(tokenDTO.getCreateTime());
		return dataToken;
	}
	
	

	@Cacheable(key="#tokenId.concat(#sheetNo)",value="tokenCache")
	@Override
	public DataToken cacheData(String tokenId, Object data, TokenDTO tokenDTO, Integer sheetNo) {
		DataToken dataToken = new DataToken();
		dataToken.setData(data);
		if(tokenDTO != null)
		dataToken.setCreateTime(tokenDTO.getCreateTime());
		return dataToken;
	}


	@CacheEvict(key="#tokenId.concat(#sheetNo)",value="tokenCache")
	@Override
	public boolean freshData(String tokenId, Integer sheetNo) {
		return true;
	}
	
}
