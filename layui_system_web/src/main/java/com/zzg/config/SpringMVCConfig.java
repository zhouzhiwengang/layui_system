package com.zzg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.zzg.component.interceptor.FangshuaInterceptor;

@Configuration
@EnableWebMvc
public class SpringMVCConfig extends WebMvcConfigurerAdapter {
	/**
	 * 资源处理
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		// 资源图片映射(静态配置->基于数据库实现动态配置)
		registry.addResourceHandler("/upload_file/**").addResourceLocations("file:F:/data/upload_file/");
		super.addResourceHandlers(registry);
	}

	// 限流拦截器
	@Bean
	public FangshuaInterceptor accessLimitInterceptor() {
		return new FangshuaInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		// API限流拦截
		registry.addInterceptor(accessLimitInterceptor()).addPathPatterns("/**")
				.excludePathPatterns("/swagger-ui.html/**", "/swagger-resources/**", "/webjars/**", "/v2/api-docs/**");
	}

	// 解决vue 提示跨越问题
	@Bean
	public CorsFilter corsFilter() {
		// 1.添加CORS配置信息
		CorsConfiguration config = new CorsConfiguration();
		// 放行哪些原始域
		config.addAllowedOrigin("*");
		// 是否发送Cookie信息
		config.setAllowCredentials(true);
		// 放行哪些原始域(请求方式)
		config.addAllowedMethod("*");
		// 放行哪些原始域(头部信息)
		config.addAllowedHeader("*");
		// 暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
		// config.addExposedHeader("*");

		config.addExposedHeader("Content-Type");
		config.addExposedHeader("X-Requested-With");
		config.addExposedHeader("accept");
		config.addExposedHeader("Origin");
		config.addExposedHeader("Access-Control-Request-Method");
		config.addExposedHeader("Access-Control-Request-Headers");

		// 2.添加映射路径
		UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
		configSource.registerCorsConfiguration("/**", config);

		// 3.返回新的CorsFilter.
		return new CorsFilter(configSource);
	}

}
