package my.log4j.starter;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Set;

/**
 * 动态修改日志级别是基于nacos来完成此功能的，如果你想在项目运行过程中修改日志级别，请在nacos中新增output.log.level=Debug参数即可
 *
 * 实现原理：当nacos参数修改后，会触发EnvironmentChangeEvent事件，根据此事件来完成动态修改日志级别
 */
@Slf4j
public class LogLevelEnvironmentChangeEvent implements ApplicationListener<EnvironmentChangeEvent> {
    private static final String OUTPUT_LOG_LEVEL = "output.log.level";

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        //日志无变化
        if (!isOutputLogLevelChange(event.getKeys())){
            return;
        }
        //获取变化后的日志级别
        String outputLogLevelChangeAfter = getOutputLogLevelChangeAfter(event.getSource());
        //修改日志级别
        updateLogLevel(outputLogLevelChangeAfter);
    }

    /**
     * 输出的日志级别是否变化
     *
     * @param keys 变化的参数
     * @return 返回output.log.level参数是否变化
     */
    private boolean isOutputLogLevelChange(Set<String> keys) {
        return keys != null && keys.contains(OUTPUT_LOG_LEVEL);
    }

    /**
     * 获取output.log.level变化后的值
     *
     * @param source spring应用程序上下文
     * @return 返回变化后的值
     */
    private String getOutputLogLevelChangeAfter(Object source) {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) source;
        ConfigurableEnvironment environment = context.getEnvironment();
        return environment.getProperty(OUTPUT_LOG_LEVEL);
    }

    /**
     * 修改日志级别
     *
     * @param outputLogLevelChangeAfter 日志级别
     */
    private void updateLogLevel(String outputLogLevelChangeAfter) {
        try {
            LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            LoggerConfig loggerConfig = ctx.getConfiguration().getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
            loggerConfig.setLevel(Level.valueOf(outputLogLevelChangeAfter));
            ctx.updateLoggers();
        } catch (Exception e) {
            log.info("failed to modify log level", e);
        }
    }
}
