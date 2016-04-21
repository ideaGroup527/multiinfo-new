package org.jmu.multiinfo.service.dingchart;

import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.core.util.DataFormatUtil;
import org.jmu.multiinfo.dto.basestatistics.DataDTO;
import org.jmu.multiinfo.dto.dingchart.DingChartCondition;
import org.jmu.multiinfo.dto.dingchart.DingChartDTO;
import org.jmu.multiinfo.service.basestatistics.BasicStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * *
 * 
 * 
 * @Title: DingChartServiceImpl.java
 * @Package org.jmu.multiinfo.service.dingchart
 * @Description:  丁氏图--其实就是数据极差正规化
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月21日 下午2:15:47
 * @version V1.0
 *
 */
@Service
public class DingChartServiceImpl implements DingChartService {

	@Autowired
	private BasicStatisticsService basicStatisticsService;
	@Override
	public DingChartDTO ding(DingChartCondition condition) {
		DingChartDTO dingDto = new DingChartDTO();
		Integer calculateMethod = condition.getCalculateMethod();
		DataDTO[][] dataGrid = condition.getDataGrid();
		double[][] dataArr =DataFormatUtil.converToDouble(dataGrid);
		double[][] resData = null;
		switch (calculateMethod) {
		case DingChartCondition.CALCULATE_METHOD_ROW:
			try {
				resData = new double[dataArr.length][dataArr[0].length];
				for (int i = 0; i < dataArr.length; i++) {
					resData[i] = basicStatisticsService.regularization(dataArr[i]);
				}
			} catch (DataErrException e) {
				dingDto.setRet_code("-1");
				e.printStackTrace();
				dingDto.setRet_err(e.getMessage());
				dingDto.setRet_msg(e.getMessage());
			}

			break;
		case DingChartCondition.CALCULATE_METHOD_COL:
			dataGrid = DataFormatUtil.transposition(dataGrid);
			dataArr =DataFormatUtil.converToDouble(dataGrid);
			try {
				resData = new double[dataArr.length][dataArr[0].length];
				for (int i = 0; i < dataArr.length; i++) {
					resData[i] = basicStatisticsService.regularization(dataArr[i]);
				}
				resData = DataFormatUtil.transposition(resData);
			} catch (DataErrException e) {
				dingDto.setRet_code("-1");
				e.printStackTrace();
				dingDto.setRet_err(e.getMessage());
				dingDto.setRet_msg(e.getMessage());
			}
			break;
		case DingChartCondition.CALCULATE_METHOD_ALL:
			try {
				basicStatisticsService.regularization(dataArr);
			} catch (DataErrException e) {
				dingDto.setRet_code("-1");
				e.printStackTrace();
				dingDto.setRet_err(e.getMessage());
				dingDto.setRet_msg(e.getMessage());
			}
			break;
		default:
			dingDto.setRet_code("-1");
			dingDto.setRet_msg("方法选择错误");
			break;
		}
		
		dingDto.setResData(resData);
		return dingDto;
	}
}
