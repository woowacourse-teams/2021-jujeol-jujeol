package com.jujeol.commons.config.database;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.datasource")
public class CustomDataSourceProperties {

    private final Replica replica1 = new Replica();
    private final Replica replica2 = new Replica();

    private String name = "source";
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
}
