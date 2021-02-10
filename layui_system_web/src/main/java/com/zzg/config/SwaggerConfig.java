package com.zzg.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * swagger2 配置
 * @author zzg
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	private ApiInfo buildApiInf() {
		return new ApiInfoBuilder().title("layui系统后台").termsOfServiceUrl("https://blog.csdn.net/zhouzhiwengang")
				.description("API接口")
				.contact(new Contact("layui", "https://blog.csdn.net/zhouzhiwengang", "zhouzhiwengang@163.com"))
				.version("2.0").build();

	}
	
	@Bean
	public Docket buildDocket() {
		  ParameterBuilder tokenPar = new ParameterBuilder();
	        List<Parameter> pars = new ArrayList<>();
	        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
	        pars.add(tokenPar.build());

		
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).paths(PathSelectors.any())
				.build().globalOperationParameters(pars).apiInfo(buildApiInf());
	}

}
