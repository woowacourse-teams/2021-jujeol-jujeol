package com.jujeol.commons.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

    @Value("${elasticsearch.host:52.79.227.198}")
    private String host;
    @Value("${elasticsearch.port:9000}")
    private Integer port;
    @Value("${elasticsearch.user:}")
    private String user;
    @Value("${elasticsearch.password:}")
    private String password;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient elasticsearchClient() {
        final BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
        basicCredentialsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials(user, password)
        );

        return new RestHighLevelClient(
                RestClient.builder(new HttpHost(host, port, "http"))
                        .setHttpClientConfigCallback(httpClientBuilder -> {
                            httpClientBuilder.disableAuthCaching();
                            return httpClientBuilder.setDefaultCredentialsProvider(basicCredentialsProvider);
                        })
        );
    }
}
