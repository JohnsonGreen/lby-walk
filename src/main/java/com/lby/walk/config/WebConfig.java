package com.lby.walk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 更改静态资源的注册位置
     * 配置加载静态资源
     *
     * @param registry
     */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/templates/**")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/templates/");
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
        registry.addResourceHandler("/react/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/react/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations(
                        ResourceUtils.CLASSPATH_URL_PREFIX + "/META-INF/resources/webjars/");
    }
}

