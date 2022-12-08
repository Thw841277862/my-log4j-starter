package my.log4j.starter.util;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.util.HashMap;
import java.util.Map;

public class MyLogFactory {
    /**
     * 存放Appender
     */

    private static Map<String, Appender> appenderMap = new HashMap<>();

    protected static Appender getAppender(String index) {
        Appender appender = appenderMap.get(index);
        //初始化appender
        if (appender == null) {
            synchronized (appenderMap) {
                appender = builderAppender(index);
                appenderMap.put(index, appender);
            }
        }
        return appender;
    }

    /**
     * 构建Appender
     *
     * @param index es 索引名称
     * @return RollingFileAppender
     */
    private static Appender builderAppender(String index) {
        return RollingFileAppender.newBuilder()
                .setName(index)
                .withFileName("logs-audit" + "/" + index + ".log")
                .withFilePattern("logs-audit" + "/%d{yyyy-MM}/" + index + "-%d{yyyy-MM-dd}-%i.log.gz")
                .withStrategy(DefaultRolloverStrategy.newBuilder().withMax("3").build())
                .withPolicy(SizeBasedTriggeringPolicy.createPolicy("50M"))
                .setLayout(PatternLayout.newBuilder().withPattern("dataTime=\"%d{yyyy-MM-dd HH:mm:ss.SSS}\" traceId=\"%traceId\" %m%n").build())
                .build();
    }
}
