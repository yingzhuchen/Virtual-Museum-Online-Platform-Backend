package org.openapitools.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:/application.properties")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    //在开发阶段将其设为true，让我们可以随时访问任意URI
    //当开发结束进行项目部署时，再将其设为false，以启用SpringSecurity服务
    @Value("${SpringSecurity.isForbidden}")
    boolean isForbidden;

    @Value("${JwtToken.jwtSecret}")
    String jwtSecret;

    @Value("${spring.server.internalPassword}")
    String internalPassword;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if(isForbidden){
            // 允许所有请求，即禁用了认证
            http.authorizeRequests().anyRequest().permitAll();
            //禁用CSRF服务，即无需token也可访问，方便测试
            http.csrf().disable();
        }
        else{
            super.configure(http);
        }
    }

    public String getJwtSecret(){
        return jwtSecret;
    }

    public String getInternalPassword(){return internalPassword;}

}