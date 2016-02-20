package org.jmu.multiinfo.base.dao;


import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
public class BaseDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	   //从spring注入原有的sqlSessionTemplate
	
	@Autowired
	@Qualifier(value="sqlSession")
    private SqlSessionTemplate sqlSessionTemplate;
	
}
