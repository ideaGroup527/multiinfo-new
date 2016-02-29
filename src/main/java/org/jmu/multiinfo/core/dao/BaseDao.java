package org.jmu.multiinfo.core.dao;

import java.util.Map;

import org.jmu.multiinfo.core.dto.PageDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;


public interface BaseDao {

    /**
     * 获取JdbcTemplate
     * 
     * @return JdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate();
    
    
    /**
     * 获取SqlSessionTemplate
     * 
     * @return SqlSessionTemplate
     */
    public SqlSessionTemplate getSqlSessionTemplate();
    
    /***
     * 
     * @param statement
     * @param currentPage 页码
     * @param pageSize 每页显示数量
     * @param params
     * @return
     */
    public <T> PageDTO<T> findListPage(String statement, Map<String,Object> params, int currentPage, int pageSize );
    

    /***
     * 
     * @param statement
     * @param currentPage 页码
     * @param pageSize 每页显示数量
     * @param count    是否进行count查询
     * @param params
     * @return
     */
    public <T> PageDTO<T> findListPage(String statement, Map<String,Object> params, int currentPage, int pageSize ,boolean count);

    /***
     * 
     * @param statement
     * @param currentPage 页码
     * @param pageSize 每页显示数量
     * @param orderBy  排序
     * @param params
     * @return
     */
    public <T> PageDTO<T> findListPage(String statement, Map<String,Object> params, int currentPage, int pageSize , String orderBy);


  
}
