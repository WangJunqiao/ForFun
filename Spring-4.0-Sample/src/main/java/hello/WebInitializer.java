package hello;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

// By converting this into a WAR file with no XML files, you need a different signal
// to the servlet container on how to launch the application.
// 这个类也是必须的，作为war运行时初始化的时候用的
public class WebInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

}
