package com.jujeol.replication;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "datasource")
public class CustomDataSourceProperties {

    private final Map<String, Slave> slave = new HashMap<>();

    private String url;
    private String username;
    private String password;

    @Getter
    @Setter
    public static class Slave {
        private String name;
        private String url;
        private String username;
        private String password;
    }
}
