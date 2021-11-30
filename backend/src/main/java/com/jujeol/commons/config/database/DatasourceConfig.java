package com.jujeol.commons.config.database;

import static com.jujeol.commons.config.database.DatabaseReplicaType.SOURCE;

import com.jujeol.commons.config.database.CustomDataSourceProperties.Replica;
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

@Profile({"dev", "prod"})
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
        if (databaseProperty.hasOnlySourceDatabase()) {
            return createDataSource(
                databaseProperty.getUrl(),
                databaseProperty.getUsername(),
                databaseProperty.getPassword()
            );
        }

        return new LazyConnectionDataSourceProxy(routingDataSource());
    }

    @Bean
    public DataSource routingDataSource() {
        ReplicationRoutingDataSource replicationRoutingDataSource = new ReplicationRoutingDataSource();
        replicationRoutingDataSource
            .setDefaultTargetDataSource(createDefaultDatasource(databaseProperty));
        replicationRoutingDataSource.setTargetDataSources(createTargetDatasource(databaseProperty));

        return replicationRoutingDataSource;
    }

    private Object createDefaultDatasource(CustomDataSourceProperties databaseProperty) {
        return createDataSource(databaseProperty.getUrl(),
            databaseProperty.getUsername(),
            databaseProperty.getPassword()
        );
    }

    private Map<Object, Object> createTargetDatasource(
        CustomDataSourceProperties databaseProperty) {
        Map<Object, Object> dataSourceMap = new LinkedHashMap<>();

        dataSourceMap.put(SOURCE.name(),
            createDataSource(databaseProperty.getUrl(),
                databaseProperty.getUsername(),
                databaseProperty.getPassword())
        );

        databaseProperty.getReplicas().forEach(
            (name, replica) -> dataSourceMap.put(name, createDataSource(replica))
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

    private DataSource createDataSource(Replica replica) {
        return createDataSource(replica.getUrl(), replica.getUsername(), replica.getPassword());
    }
}
