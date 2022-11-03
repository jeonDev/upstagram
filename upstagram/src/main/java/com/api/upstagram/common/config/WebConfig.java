package com.api.upstagram.common.config;

import com.api.upstagram.common.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Value("${cors.url}")
    private String allowOrigins;    // cors 허용 url

    @Value("${upload-path}")
    private String uploadPath;      // 외부파일 접근 경로

    @Value("${resource-path")
    private String resourePath;     // 외부파일 접근 경로

    /*
     * CORS 허용
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        
        registry.addMapping("/**")
            .allowedOrigins(allowOrigins)
            .maxAge(3600)
            .allowCredentials(true);
    }

    /*
     * 외부 파일접근 허용
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(uploadPath)
                .addResourceLocations(resourePath);
    }


    /*
    * 인터셉터 추가
    * */
    @Bean
    public LogInterceptor logInterceptor(){
        return new LogInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor())
                .addPathPatterns("/user/**", "/manage/**", "/admin/**");
    }
}
