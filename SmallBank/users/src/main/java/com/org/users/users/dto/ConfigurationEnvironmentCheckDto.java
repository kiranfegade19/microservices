package com.org.users.users.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties(prefix = "data")
public class ConfigurationEnvironmentCheckDto {

    private String envApplicationConfiguration;
}
