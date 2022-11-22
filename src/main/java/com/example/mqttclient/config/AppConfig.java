package com.example.mqttclient.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class AppConfig {

    @Value("${app.name}")
    private String applicationName;

    @Value("${mqtt.host}")
    private String mqttHost;
}
