package cn.xiaofuge.x.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 基础设施层配置
 */
@Configuration
public class InfrastructureConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
