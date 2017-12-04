package com.apigee.trashcans.inventory;

import com.googlecode.objectify.ObjectifyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import javax.servlet.Filter;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
  @Bean
  public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
    MessageDispatcherServlet servlet = new MessageDispatcherServlet();
    servlet.setApplicationContext(applicationContext);
    servlet.setTransformWsdlLocations(true);
    return new ServletRegistrationBean(servlet, "/ws/*");
  }

  @Bean
  public FilterRegistrationBean ObjectifyFilterRegistration() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(ObjectifyFilter());
    registration.addUrlPatterns("/ws/*");
    registration.setName("ObjectifyFilter");
    registration.setOrder(1);
    return registration;
  }

  public Filter ObjectifyFilter() {
    return new ObjectifyFilter();
  }

  @Bean(name = "trashcans")
  public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema trashcansSchema) {
    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
    wsdl11Definition.setPortTypeName("TrashcansPort");
    wsdl11Definition.setLocationUri("/ws");
    wsdl11Definition.setTargetNamespace("http://trashcans.apigee.com/inventory");
    wsdl11Definition.setSchema(trashcansSchema);
    return wsdl11Definition;
  }

  @Bean
  public XsdSchema trashcansSchema() {
    return new SimpleXsdSchema(new ClassPathResource("trashcans.xsd"));
  }
}
