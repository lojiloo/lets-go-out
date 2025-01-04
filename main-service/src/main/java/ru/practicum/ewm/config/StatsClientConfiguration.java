package ru.practicum.ewm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.practicum.StatsClient;

@Configuration
@PropertySource("classpath:application.properties")
public class StatsClientConfiguration {
    @Value("${stats-server.url}")
    String uri;

    @Bean
    public StatsClient statsClient() {
        return new StatsClient(uri);
    }
}
