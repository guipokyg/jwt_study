package com.springboot.demo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springboot.demo.filter.MyFilter;

@Configuration
public class FilterConfig {
    //일반필터를 걸려면 이걸만들면됨. 대신 시큐리티필터 뒤에 실행이됨.
    @Bean
    public FilterRegistrationBean<MyFilter> filter1(){
        FilterRegistrationBean<MyFilter> bean = new FilterRegistrationBean<>(new MyFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(0);
        System.out.println("filter1");
        return bean;
    }
}
