
import my.log4j.starter.util.MyLogUtil;

import java.util.HashMap;
import java.util.Map;

public class TestDemo {
    /**
     * 打印审计日志
     */
    public void printingAuditLog() {
        // ES中的索引名
        String index = "login";
        // 日志参数
        Map<String, Object> params = new HashMap<>(5);
        // 用户Id
        params.put("id", "1000000001");
        // 用户名
        params.put("loginName", "admin");
        // 用户地址
        params.put("address", "湖南省长沙市岳麓区");
        // 用户状态
        params.put("status", "10");
        // 最后登录时间
        params.put("lastLoginTime", "2022-08-23 17:33:59");
        // 打印日志
        MyLogUtil.log(index, params);
    }


}
