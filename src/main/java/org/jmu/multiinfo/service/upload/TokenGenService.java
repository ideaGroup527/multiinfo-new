package org.jmu.multiinfo.service.upload;

import org.jmu.multiinfo.dto.upload.DataToken;
import org.jmu.multiinfo.dto.upload.TokenDTO;

public interface TokenGenService {
public DataToken cacheData(String tokenId,Object data, TokenDTO tokenDTO);
}
