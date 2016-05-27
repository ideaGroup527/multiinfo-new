package org.jmu.multiinfo.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
///*测试需要把这块注释
@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan(basePackages ={"org.jmu.multiinfo.web.controller"})
//*/
public class SwaggerConfig {
	 /**
	    * Every Docket bean is picked up by the swagger-mvc framework - allowing for multiple
	    * swagger groups i.e. same code base multiple swagger resource listings.
	    */
	   @Bean
	   public Docket customDocket(){
	      return new Docket(DocumentationType.SWAGGER_2);

	   }
}

