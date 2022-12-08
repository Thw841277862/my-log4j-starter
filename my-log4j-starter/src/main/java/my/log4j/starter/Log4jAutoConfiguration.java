package my.log4j.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Log4jAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(LogLevelEnvironmentChangeEvent.class)
    public LogLevelEnvironmentChangeEvent logLevelEnvironmentChangeEvent() {
        return new LogLevelEnvironmentChangeEvent();
    }
}
