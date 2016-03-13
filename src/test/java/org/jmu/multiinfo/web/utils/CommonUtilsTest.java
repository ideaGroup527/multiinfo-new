package org.jmu.multiinfo.web.utils;

import org.junit.Assert;
import org.junit.Test;

public class CommonUtilsTest {

    /** 
     * Test method for {@link org.jmu.multiinfo.web.utils.CommonUtil#isPositiveInteger(java.lang.String)} 
     */  
    //correct test case: 1, 87653521123567  
    //wrong test case: 0.1, 0, 0123, -1, -0.1, ab  
    @Test  
    public void testIsPositiveInteger() {  
        Assert.assertTrue(CommonUtil.isPositiveInteger("1"));  
        Assert.assertTrue(CommonUtil.isPositiveInteger("+12"));  
        Assert.assertTrue(CommonUtil.isPositiveInteger("87653521123567"));  
        Assert.assertFalse(CommonUtil.isPositiveInteger("0.1"));  
        Assert.assertFalse(CommonUtil.isPositiveInteger("0"));  
        Assert.assertFalse(CommonUtil.isPositiveInteger("0123"));  
        Assert.assertFalse(CommonUtil.isPositiveInteger("-1"));  
        Assert.assertFalse(CommonUtil.isPositiveInteger("-0.1"));  
        Assert.assertFalse(CommonUtil.isPositiveInteger("ab"));  
    }  
  
    /** 
     * Test method for {@link org.jmu.multiinfo.web.utils.CommonUtil#isNegativeInteger(java.lang.String)} 
     */  
    //correct test case: -1, -87653521123567  
    //wrong test case: 0.1, 0, 0123, 1, -0.1, -ab  
    @Test  
    public void testIsNegativeInteger() {  
        Assert.assertTrue(CommonUtil.isNegativeInteger("-1"));  
        Assert.assertTrue(CommonUtil.isNegativeInteger("-87653521123567"));  
        Assert.assertFalse(CommonUtil.isNegativeInteger("0.1"));  
        Assert.assertFalse(CommonUtil.isNegativeInteger("0"));  
        Assert.assertFalse(CommonUtil.isNegativeInteger("0123"));  
        Assert.assertFalse(CommonUtil.isNegativeInteger("1"));  
        Assert.assertFalse(CommonUtil.isNegativeInteger("-0.1"));  
        Assert.assertFalse(CommonUtil.isNegativeInteger("ab"));  
    }  
  
    /** 
     * Test method for {@link org.jmu.multiinfo.web.utils.CommonUtil#isWholeNumber(java.lang.String)}. 
     */  
    //correct test case: -1, 0, 1, 8673434231, -282464334  
    //wrong test case: 0.1, 0123, -0.1, ab  
    @Test  
    public void testIsWholeNumber() {  
        Assert.assertTrue(CommonUtil.isWholeNumber("-1"));  
        Assert.assertTrue(CommonUtil.isWholeNumber("0"));  
        Assert.assertTrue(CommonUtil.isWholeNumber("1"));  
        Assert.assertTrue(CommonUtil.isWholeNumber("+12"));  
        Assert.assertTrue(CommonUtil.isWholeNumber("8673434231"));  
        Assert.assertTrue(CommonUtil.isWholeNumber("-282464334"));  
        Assert.assertFalse(CommonUtil.isWholeNumber("0123"));  
        Assert.assertFalse(CommonUtil.isWholeNumber("0.1"));  
        Assert.assertFalse(CommonUtil.isWholeNumber("-0.1"));  
        Assert.assertFalse(CommonUtil.isWholeNumber("ab"));  
    }  
  
    /** 
     * Test method for {@link org.jmu.multiinfo.web.utils.CommonUtil#isPositiveDecimal(java.lang.String)} 
     */  
    //correct test case: 0.1, 0.132213, 1.0  
    //wrong test case: 1, 0.0, 0123, -1, -0.1  
    @Test  
    public void testIsPositiveDecimal() {  
        Assert.assertTrue(CommonUtil.isPositiveDecimal("0.1"));  
        Assert.assertTrue(CommonUtil.isPositiveDecimal("0.132213"));  
        Assert.assertTrue(CommonUtil.isPositiveDecimal("30.00"));  
        Assert.assertTrue(CommonUtil.isDecimal("0."));  
        Assert.assertTrue(CommonUtil.isPositiveDecimal("+12.0"));  
        Assert.assertFalse(CommonUtil.isPositiveDecimal("0123"));  
        Assert.assertFalse(CommonUtil.isPositiveDecimal("1"));  
        Assert.assertFalse(CommonUtil.isPositiveDecimal("0.0"));  
        Assert.assertFalse(CommonUtil.isPositiveDecimal("ab"));  
        Assert.assertFalse(CommonUtil.isPositiveDecimal("-1"));  
        Assert.assertFalse(CommonUtil.isPositiveDecimal("-0.1"));  
    }  
  
    /** 
     * Test method for {@link org.jmu.multiinfo.web.utils.CommonUtil#isNegativeDecimal(java.lang.String)} 
     */  
    //correct test case: -0.132213, -1.0  
    //wrong test case: 1, 0, 0123, -1, 0.1  
    @Test  
    public void testIsNegativeDecimal() {  
        Assert.assertTrue(CommonUtil.isNegativeDecimal("-0.132213"));  
        Assert.assertTrue(CommonUtil.isNegativeDecimal("-1.0"));  
        Assert.assertTrue(CommonUtil.isDecimal("-0."));  
        Assert.assertFalse(CommonUtil.isNegativeDecimal("1"));  
        Assert.assertFalse(CommonUtil.isNegativeDecimal("0"));  
        Assert.assertFalse(CommonUtil.isNegativeDecimal("0123"));  
        Assert.assertFalse(CommonUtil.isNegativeDecimal("0.0"));  
        Assert.assertFalse(CommonUtil.isNegativeDecimal("ab"));  
        Assert.assertFalse(CommonUtil.isNegativeDecimal("-1"));  
        Assert.assertFalse(CommonUtil.isNegativeDecimal("0.1"));  
    }  
  
    /** 
     * Test method for {@link org.jmu.multiinfo.web.utils.CommonUtil#isDecimal(java.lang.String)}. 
     */  
    //correct test case: 0.1, 0.00, -0.132213  
    //wrong test case: 1, 0, 0123, -1,  0., ba  
    @Test  
    public void testIsDecimal() {  
        Assert.assertTrue(CommonUtil.isDecimal("0.1"));  
        Assert.assertTrue(CommonUtil.isDecimal("0.00"));  
        Assert.assertTrue(CommonUtil.isDecimal("+0.0"));  
        Assert.assertTrue(CommonUtil.isDecimal("-0.132213"));  
        Assert.assertTrue(CommonUtil.isDecimal("0."));  
        Assert.assertFalse(CommonUtil.isDecimal("1"));  
        Assert.assertFalse(CommonUtil.isDecimal("0123"));  
        Assert.assertFalse(CommonUtil.isDecimal("0"));  
        Assert.assertFalse(CommonUtil.isDecimal("ab"));  
        Assert.assertFalse(CommonUtil.isDecimal("-1"));  
          
    }  
  
    /** 
     * Test method for {@link org.jmu.multiinfo.web.utils.CommonUtil#isRealNumber(java.lang.String)}. 
     */  
    //correct test case: 0.032213, -0.234, 0.0, 1, -1, 0  
    //wrong test case: 00.13, ab, +0.14  
    @Test  
    public void testIsRealNumber() {  
        Assert.assertTrue(CommonUtil.isRealNumber("0.032213"));  
        Assert.assertTrue(CommonUtil.isRealNumber("-0.234"));  
        Assert.assertTrue(CommonUtil.isRealNumber("0.0"));  
        Assert.assertTrue(CommonUtil.isRealNumber("1"));  
        Assert.assertTrue(CommonUtil.isRealNumber("+0.14"));  
        Assert.assertTrue(CommonUtil.isRealNumber("-1"));  
        Assert.assertTrue(CommonUtil.isRealNumber("0.0"));  
        Assert.assertFalse(CommonUtil.isRealNumber(" 00.13"));  
        Assert.assertFalse(CommonUtil.isRealNumber("A1"));  
          
    }  
    
    /** 
     * Test method for {@link org.jmu.multiinfo.web.utils.CommonUtil#isScienceNumber(java.lang.String)}. 
     */ 
    //correct test case: 1.238761976E-10,+2e3,6E-6,0.000000006E3,-1e3,0e3
    //wrong test case: 3e0.3,00.13,ab
    @Test
    public void testIsScienceNumber(){
        Assert.assertTrue(CommonUtil.isScienceNumber("1.238761976E-10"));  
        Assert.assertTrue(CommonUtil.isScienceNumber("+2e3"));  
        Assert.assertTrue(CommonUtil.isScienceNumber("6E-6"));  
        Assert.assertTrue(CommonUtil.isScienceNumber("0.000000006E3"));  
        Assert.assertTrue(CommonUtil.isScienceNumber("-1e3"));  
        Assert.assertTrue(CommonUtil.isScienceNumber("0e3"));  
        Assert.assertFalse(CommonUtil.isScienceNumber("3e0.3"));  
        Assert.assertFalse(CommonUtil.isScienceNumber("00.13"));  
        Assert.assertFalse(CommonUtil.isScienceNumber("ab"));  
    	
    }
    
    /** 
     * Test method for {@link org.jmu.multiinfo.web.utils.CommonUtil#isVirgNumber(java.lang.String)}. 
     */ 
    //correct test case: 2,999,999,333   -2,999,999,333   +333,333.3 1  2,222  -1
    //wrong test case: 0,222  0.0 33333,333  ab
    @Test
    public void testIsVirgNumber(){
        Assert.assertTrue(CommonUtil.isVirgNumber("2,999,999,333"));  
        Assert.assertTrue(CommonUtil.isVirgNumber("-2,999,999,333"));  
        Assert.assertTrue(CommonUtil.isVirgNumber("+333,333.3"));  
        Assert.assertTrue(CommonUtil.isVirgNumber("1"));  
        Assert.assertTrue(CommonUtil.isVirgNumber("2,222"));  
        Assert.assertFalse(CommonUtil.isVirgNumber("0,222"));  
        Assert.assertFalse(CommonUtil.isVirgNumber("0.0"));  
        Assert.assertFalse(CommonUtil.isVirgNumber("33333,333"));  
        Assert.assertFalse(CommonUtil.isVirgNumber("ab"));  
    	
    }
    
    
}
