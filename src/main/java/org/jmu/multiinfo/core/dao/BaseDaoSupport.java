package org.jmu.multiinfo.core.dao;

import java.util.Map;

import org.jmu.multiinfo.core.dto.PageDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import com.github.pagehelper.PageHelper;

public class BaseDaoSupport implements BaseDao{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	   //从spring注入原有的sqlSessionTemplate
	
	@Autowired
	@Qualifier(value="sqlSession")
 private SqlSessionTemplate sqlSessionTemplate;
	
 @Autowired
 private JdbcTemplate jdbcTemplate;

@Override
public JdbcTemplate getJdbcTemplate() {
	return jdbcTemplate;
}

@Override
public SqlSessionTemplate getSqlSessionTemplate() {
	return sqlSessionTemplate;
}
@SuppressWarnings({ "rawtypes", "unchecked" })
@Override
public <T> PageDTO<T> findListPage(String statement, Map<String,Object> params, int currentPage, int pageSize) {
	logger.warn("开始分页:");
	PageHelper.startPage(currentPage, pageSize);
	return new PageDTO(sqlSessionTemplate.selectList(statement, params));
}

@SuppressWarnings({ "rawtypes", "unchecked" })
@Override
public <T> PageDTO<T> findListPage(String statement, Map<String, Object> params, int currentPage, int pageSize,
		boolean count) {
	logger.warn("开始分页:");
	PageHelper.startPage(currentPage, pageSize,count);
	return new PageDTO(sqlSessionTemplate.selectList(statement, params));
}

@SuppressWarnings({ "rawtypes", "unchecked" })
@Override
public <T> PageDTO<T> findListPage(String statement, Map<String, Object> params, int currentPage, int pageSize,
		String orderBy) {
	logger.warn("开始分页:");
	PageHelper.startPage(currentPage, pageSize,orderBy);
	return new PageDTO(sqlSessionTemplate.selectList(statement, params));
}



}
