package my.log4j.starter.util;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.message.MapMessage;

import java.util.Map;

public class MyLogUtil {
    /**
     * 打印日志
     *
     * @param index  索引名称
     * @param params 参数
     */
    public static void log(String index, Map<String, Object> params) {
        //获取appender
        Appender appender = MyLogFactory.getAppender(index);
        appender.append(Log4jLogEvent.newBuilder().setMessage(new MapMessage<>(params))
                .build());
    }
}
