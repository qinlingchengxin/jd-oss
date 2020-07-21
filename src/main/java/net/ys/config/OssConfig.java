package net.ys.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: NMY
 * @Date: 2020/7/21
 * @Time: 15:37
 */
@Data
@Component
@ConfigurationProperties(prefix = "system.oss")
public class OssConfig {
    private String accessKey;
    private String accessSecret;
    private String endPoint;
    private String region;
    private String bucketName;
    private long maxSize;
}
