package com.pim.configuration;

import com.pim.PimApplication;
import com.pim.controller.ApplicationController;
import com.pim.dao.IEmployeeRepository;
import com.pim.dao.IGroupRepository;
import com.pim.dao.IProjectRepository;
import com.pim.service.EmployeeService;
import com.pim.service.GroupService;
import com.pim.service.IProjectService;
import com.pim.service.ProjectService;
import org.hibernate.SessionFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {PimApplication.class, ApplicationController.class, IProjectService.class})
public class PimConfiguration implements WebMvcConfigurer {
    @Bean
    public static ProjectService projectService(IProjectRepository projectRepository){
        return new ProjectService(projectRepository);
    }

    @Bean
    public static GroupService groupService(IGroupRepository groupRepository){
        return new GroupService(groupRepository);
    }

    @Bean
    public static EmployeeService employeeService(IEmployeeRepository employeeRepository){
        return new EmployeeService(employeeRepository);
    }

    @Bean(name = "localeResolver")
    public LocaleResolver getLocaleResolver()  {
        CookieLocaleResolver resolver= new CookieLocaleResolver();
        resolver.setCookieDomain("myAppLocaleCookie");
        // 60 minutes
        resolver.setCookieMaxAge(60*60);
        return resolver;
    }

    @Bean(name="messageSource")
    public MessageSource getMessageResource() {
        ReloadableResourceBundleMessageSource messageResource= new ReloadableResourceBundleMessageSource();
        // Read i18n/messages_xxx.properties file.
        // For example: messages_en.properties
        messageResource.setBasename("classpath:language/messages");
        messageResource.setDefaultEncoding("UTF-8");
        return messageResource;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");
        registry.addInterceptor(localeInterceptor).addPathPatterns("/**");
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
//        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
//        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
//    }

//    @Bean
//    public InternalResourceViewResolver viewResolver(){
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/WEB-INF/jsp/");
//        resolver.setSuffix(".jsp");
//
//        return resolver;
//    }
}
