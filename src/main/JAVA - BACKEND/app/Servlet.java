package app;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class Servlet {
    public static ServletRegistrationBean createServlet(String name, Class<?> register, String path) {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(register);
        dispatcherServlet.setApplicationContext(applicationContext);
        ServletRegistrationBean servletRegistrationBean =
                new ServletRegistrationBean(dispatcherServlet, path);
        servletRegistrationBean.setName(name);
        return servletRegistrationBean;
    }
}
