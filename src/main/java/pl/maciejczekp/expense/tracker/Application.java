package pl.maciejczekp.expense.tracker;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.maciejczekp.expense.tracker.configuration.security.CsrfHeaderFilter;

@SpringBootApplication
public class Application implements InitializingBean{

    @Autowired
    private InitialDataLoad load;

    @Override
    public void afterPropertiesSet() throws Exception {
        load.load();
    }

    @Bean
    CsrfHeaderFilter csrfHeaderFilter(@Value("${server.contextPath}") String contextPath) {
        return new CsrfHeaderFilter(contextPath);
    }

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
    }

}