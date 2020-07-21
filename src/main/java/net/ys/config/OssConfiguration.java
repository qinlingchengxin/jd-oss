package net.ys.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import net.ys.component.PostObjectSigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: NMY
 * @Date: 2020/7/21
 * @Time: 15:45
 */
@Configuration
public class OssConfiguration {

    @Autowired
    private OssConfig ossConfig;

    @Bean
    public AWSCredentials credentials() {
        return new BasicAWSCredentials(ossConfig.getAccessKey(), ossConfig.getAccessSecret());
    }

    @Bean
    public PostObjectSigner postObjectSigner() {
        return new PostObjectSigner();
    }
}
