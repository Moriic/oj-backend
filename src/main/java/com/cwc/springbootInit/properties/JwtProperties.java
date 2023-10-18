package com.cwc.springbootInit.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "init.jwt")
@Data
public class JwtProperties {
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;
}
