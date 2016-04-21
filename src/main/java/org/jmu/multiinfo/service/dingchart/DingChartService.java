package org.jmu.multiinfo.service.dingchart;

import org.jmu.multiinfo.dto.dingchart.DingChartCondition;
import org.jmu.multiinfo.dto.dingchart.DingChartDTO;


/***
 * *
 * 
 * 
 * @Title: DingChartService.java
 * @Package org.jmu.multiinfo.service.dingchart
 * @Description:  丁氏图
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月21日 下午4:18:57
 * @version V1.0
 *
 */
public interface DingChartService {
	public DingChartDTO ding(DingChartCondition condition);
}
