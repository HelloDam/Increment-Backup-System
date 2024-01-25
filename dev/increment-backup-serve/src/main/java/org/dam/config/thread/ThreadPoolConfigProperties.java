package org.dam.config.thread;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 用来读取yaml配置文件的数据
 * @Author dam
 * @create 2024/1/24 10:53
 */
@ConfigurationProperties(prefix = "ibs.thread")
@Component//将该配置放到容器中
@Data
public class ThreadPoolConfigProperties {

    private Integer coreSize;
    private Integer maxSize;
    private Integer keepAliveTime;
    private Integer queueCapacity;

}
