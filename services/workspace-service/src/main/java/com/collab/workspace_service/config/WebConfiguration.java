package com.collab.workspace_service.config;

import com.collab.workspace_service.adapter.in.web.filter.RequestCorrelationIdFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    @Bean
    public FilterRegistrationBean<RequestCorrelationIdFilter> correlationIdFilter() {
        FilterRegistrationBean<RequestCorrelationIdFilter> registrationBean =
                new FilterRegistrationBean<>();

        registrationBean.setFilter(new RequestCorrelationIdFilter());
        registrationBean.setOrder(1);

        return registrationBean;
    }
}