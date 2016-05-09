package org.jmu.multiinfo.base.util;

import org.jmu.multiinfo.core.dto.DataVariety;
import org.jmu.multiinfo.core.util.DataFormatUtil;
import org.jmu.multiinfo.dto.basestatistics.DataDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
@RunWith(MyJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:conf/spring/applicationContext.xml"})
public class DataFormatUtilTest {
	Logger logger = LoggerFactory.getLogger(DataFormatUtilTest.class);
	
	DataDTO dataDTO = null;
	@Before
	public void init(){
		dataDTO = new DataDTO();
		dataDTO.setPosition("A3");
		dataDTO.setPositionDes("A,3");
	}
@Test
public void converForString(){
	dataDTO.setData("上海");
	dataDTO.setType(DataVariety.DATA_TYPE_STRING);
	DataFormatUtil.converToDouble(dataDTO);
}

@Test
public void converForNumber(){
	dataDTO.setData("1.545");
	dataDTO.setType(DataVariety.DATA_TYPE_NUMERIC);
	Double  data  =DataFormatUtil.converToDouble(dataDTO);
	logger.debug(data+"");
}

@Test
public void converForScience(){
	dataDTO.setData("1.5E+1");
	dataDTO.setType(DataVariety.DATA_TYPE_NUMERIC_SCIENCE);
	Double  data  =DataFormatUtil.converToDouble(dataDTO);
	logger.debug(data+"");
}


@Test
public void converForVirg(){
	dataDTO.setData("-1,500,800");
	dataDTO.setType(DataVariety.DATA_TYPE_NUMERIC_VIRG);
	Double  data  =DataFormatUtil.converToDouble(dataDTO);
	logger.debug(data+"");
}
}
