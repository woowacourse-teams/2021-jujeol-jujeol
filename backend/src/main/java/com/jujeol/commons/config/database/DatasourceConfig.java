package com.jujeol.commons.config.database;

import static com.jujeol.commons.config.database.DatabaseReplicaType.REPLICA1;
import static com.jujeol.commons.config.database.DatabaseReplicaType.REPLICA2;
import static com.jujeol.commons.config.database.DatabaseReplicaType.SOURCE;

import com.zaxxer.hikari.HikariDataSource;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Profile({"dev", "prod", "repliDummy"})
@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableConfigurationProperties(CustomDataSourceProperties.class)
public class DatasourceConfig {

    private static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private final CustomDataSourceProperties databaseProperty;

    public DatasourceConfig(CustomDataSourceProperties databaseProperty) {
        this.databaseProperty = databaseProperty;
    }

    @Primary
    @Bean
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource());
    }

    @Bean
    public DataSource routingDataSource() {
        Map<Object, Object> dataSources = createDataSources(databaseProperty);

        ReplicationRoutingDataSource replicationRoutingDataSource = new ReplicationRoutingDataSource();
        replicationRoutingDataSource.setDefaultTargetDataSource(dataSources.get(SOURCE));
        replicationRoutingDataSource.setTargetDataSources(dataSources);

        return replicationRoutingDataSource;
    }

    private Map<Object, Object> createDataSources(CustomDataSourceProperties databaseProperty) {
        Map<Object, Object> dataSourceMap = new LinkedHashMap<>();
        dataSourceMap.put(SOURCE,
            createDataSource(databaseProperty.getUrl(),
                databaseProperty.getUsername(),
                databaseProperty.getPassword())
        );

        dataSourceMap.put(REPLICA1,
            createDataSource(databaseProperty.getReplica1().getUrl(),
                databaseProperty.getReplica1().getUsername(),
                databaseProperty.getReplica2().getPassword())
        );

        dataSourceMap.put(REPLICA2,
            createDataSource(databaseProperty.getReplica2().getUrl(),
                databaseProperty.getReplica2().getUsername(),
                databaseProperty.getReplica2().getPassword())
        );

        return dataSourceMap;
    }

    public DataSource createDataSource(String url, String username, String password) {
        return DataSourceBuilder.create()
            .type(HikariDataSource.class)
            .url(url)
            .driverClassName(MYSQL_JDBC_DRIVER)
            .username(username)
            .password(password)
            .build();
    }

}
