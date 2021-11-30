package com.jujeol.commons.config.database;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.datasource")
public class CustomDataSourceProperties {

    private final Map<String, Replica> replicas = new HashMap<>();

    private String name = "SOURCE";
    private String url;
    private String username;
    private String password;

    @Getter
    @Setter
    public static class Replica {

        private String name;
        private String url;
        private String username;
        private String password;
    }

    public boolean hasOnlySourceDatabase() {
        return replicas.size() == 0;
    }
}
