package org.jmu.multiinfo.service.reducingdim;

import org.jmu.multiinfo.dto.reducingdim.FactorAnalysisCondition;
import org.jmu.multiinfo.dto.reducingdim.FactorAnalysisDTO;

/**
 * 因子分析
 * @Title: FactorAnalysisService.java
 * @Package org.jmu.multiinfo.service.reducingdim
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年5月24日 上午10:32:20
 * @version V1.0
 *
 */
public interface FactorAnalysisService {
public FactorAnalysisDTO factorAnalysis(FactorAnalysisCondition condition);
}
