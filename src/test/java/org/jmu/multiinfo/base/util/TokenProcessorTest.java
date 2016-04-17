package org.jmu.multiinfo.base.util;

import org.jmu.multiinfo.core.util.TokenProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

@RunWith(MyJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:conf/spring/applicationContext.xml"})
public class TokenProcessorTest {

	@Test
	public void seTest(){
		System.out.println(TokenProcessor.getInstance().generateToken("125vdgdfdg733"+ System.nanoTime()));
	}

}
