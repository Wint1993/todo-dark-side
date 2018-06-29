package com.iteratec.todo.tech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

//@Configuration
//@EnableResourceServer
public class ResourceServerConfig { //extends ResourceServerConfigurerAdapter {

  /*  private static final String RESOURCE_ID = "resource_id";


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.csrf().disable();

           http.requestMatchers().antMatchers("/rest/other/***")
                .and().authorizeRequests()
                .antMatchers("/rest/other/***").authenticated()
                .anyRequest().authenticated();

    }
*/
 /*   @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID).stateless(false);

    }*/
}

