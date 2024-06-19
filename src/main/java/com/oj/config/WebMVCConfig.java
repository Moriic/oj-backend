package com.oj.config;

import com.oj.interceptor.JwtTokenUserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

/**
 * WebMVC相关配置
 */

@Configuration
public class WebMVCConfig  extends WebMvcConfigurationSupport {

    @Resource
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;

    /**
     * 配置jwt拦截器
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/login");
    }

    /**
     * knife4j 配置静态资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /** 配置knife4j 显示文档 */
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        /** 公共部分内容 */
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");


        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:"+System.getProperty("user.dir") + "/src/main/resources/upload/");

    }

}
