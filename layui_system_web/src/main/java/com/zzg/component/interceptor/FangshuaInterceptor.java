package com.zzg.component.interceptor;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.zzg.common.annotation.AccessLimit;
import com.zzg.common.entity.Result;
import com.zzg.common.util.IpUtils;
import com.zzg.redis.util.RedisUtil;

/**
 * 接口防刷拦截器
 * @author zzg
 *
 */
@Component
public class FangshuaInterceptor extends HandlerInterceptorAdapter {
	 @Autowired
	 private RedisUtil redisUtil;
	 
	    @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	 
	        //判断请求是否属于方法的请求
	        if(handler instanceof HandlerMethod){
	 
	            HandlerMethod hm = (HandlerMethod) handler;
	 
	            //获取方法中的注解,看是否有该注解
	            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
	            if(accessLimit == null){
	                return true;
	            }
	            int times = accessLimit.times();//请求次数
                int second = accessLimit.second();//请求时间范围
	            
	            //根据 IP + API 限流
                String key = IpUtils.getIpAddr(request) + request.getRequestURI();
                //根据key获取已请求次数
                Integer maxTimes = (Integer) redisUtil.get(key);
                
                if(maxTimes == null){
                    //set时一定要加过期时间
                	redisUtil.set(key, 1, second);
                }else if(maxTimes < times){
                	redisUtil.set(key, maxTimes+1, second);
                }else{
                	 //超出访问次数
                    render(response);
                    return false;
                }
	        }
	 
	        return true;
	 
	    }
	    private void render(HttpServletResponse response)throws Exception {
	        response.setContentType("application/json;charset=UTF-8");
	        OutputStream out = response.getOutputStream();
	        String str  = JSON.toJSONString(Result.error("接口请求过于频繁"));
	        out.write(str.getBytes("UTF-8"));
	        out.flush();
	        out.close();
	    }
}
