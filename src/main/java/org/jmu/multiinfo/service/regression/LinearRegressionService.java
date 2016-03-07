package org.jmu.multiinfo.service.regression;

import org.jmu.multiinfo.dto.regression.CommonCondition;
import org.jmu.multiinfo.dto.regression.CommonDTO;

public interface LinearRegressionService {
	
public CommonDTO calSingleLinearRegression(double[][] data);

public CommonDTO calOLSMultipleLinearRegression(double[] y, double[][] x);

public CommonDTO calLinearRegression(CommonCondition condition);
}
