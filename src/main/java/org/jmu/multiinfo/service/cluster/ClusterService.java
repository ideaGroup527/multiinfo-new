package org.jmu.multiinfo.service.cluster;

import org.jmu.multiinfo.dto.cluster.PointGroupCondition;
import org.jmu.multiinfo.dto.cluster.PointGroupDTO;

/***
 * 
 * 聚类分析
 * @Title: ClusterService.java
 * @Package org.jmu.multiinfo.service.cluster
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年5月2日 下午4:34:48
 * @version V1.0
 *
 */
public interface ClusterService {
public PointGroupDTO pointGroup(PointGroupCondition condition);
}
