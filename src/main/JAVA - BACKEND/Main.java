import app.Api;
import app.Servlet;
import app.Web;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.net.URISyntaxException;


@EnableAutoConfiguration(exclude = {EmbeddedMongoAutoConfiguration.class,
        MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, DispatcherServletAutoConfiguration.class})
@ComponentScan(basePackages = {"configuration"})
public class Main {
    public Main() throws IOException {
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        start(Main.class, 8080).run(args);
    }

    private static SpringApplication start(Class<?> parent, int port) {
        return new SpringApplicationBuilder(parent)
                .bannerMode(Banner.Mode.OFF)
                .properties("app.port=${other.port:" + port + "}").build();
    }

    @Bean
    public ServletRegistrationBean apiGithub(){
        return Servlet.createServlet("api", Api.class, "/api/*" );
    }

    @Bean
    public ServletRegistrationBean webApp() {
        return Servlet.createServlet("web", Web.class, "/");
    }
}
