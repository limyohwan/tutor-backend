package com.yohwan.tutor.global.config;

import com.yohwan.tutor.global.auth.AuthorizationHeaderArgumentResolver;
import com.yohwan.tutor.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class SpringConfig implements WebMvcConfigurer {
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthorizationHeaderArgumentResolver(teacherRepository));
    }
}
