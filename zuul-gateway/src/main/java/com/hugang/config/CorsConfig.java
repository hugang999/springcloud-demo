package com.hugang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * zuul跨域配置，也可使用第二种方法：在需要支持跨域访问的方法上添加@CrossOrigin注解
 *
 * @author: hg
 * @date: 2019/9/27 09:28
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        final CorsConfiguration corsConfiguration = new CorsConfiguration();

        //设置支持携带cookie跨域访问
        corsConfiguration.setAllowCredentials(true);
        //设置原始域,可写：http://www.a.com, http://www.b.com
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        //设置允许的头
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        //设置允许的方法，如get，post等
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        //表示在此时间(单位秒)内，相同的跨域请求将不再进行检查
        corsConfiguration.setMaxAge(300L);

        Map<String, CorsConfiguration> corsConfigurationMap = new HashMap<>();
        //表示对所有域名进行配置
        corsConfigurationMap.put("/**", corsConfiguration);
        source.setCorsConfigurations(corsConfigurationMap);

        return new CorsFilter(source);
    }
}
