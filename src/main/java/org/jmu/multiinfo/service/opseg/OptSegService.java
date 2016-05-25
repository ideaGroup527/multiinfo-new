package org.jmu.multiinfo.service.opseg;

import org.jmu.multiinfo.dto.opseg.OptArrDTO;
import org.jmu.multiinfo.dto.opseg.OptSegCondition;
import org.jmu.multiinfo.dto.opseg.OptSegDTO;

/**
 * 
 * @author Administrator
 *
 */
public interface OptSegService {
	/***
	 * 最优k分割
	 * @param N 样本数
	 * @param K 分割次数
	 * @param D 变差矩阵
	 * @return 
	 */
public OptArrDTO optimalKSegmentation(final int N,final int K,final double[][] D);

public OptSegDTO optimalKSegmentation(OptSegCondition condition);
}
