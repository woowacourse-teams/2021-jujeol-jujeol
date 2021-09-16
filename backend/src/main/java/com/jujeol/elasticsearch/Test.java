package com.jujeol.elasticsearch;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public class Test {

    @Value("${elasticsearch.url}")
    private String hostname;

    @Value("${elasticsearch.port}")
    private Integer port;

    public void insert() {
        try (RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(hostname, port, "http")))) {
            IndexRequest indexRequest = new IndexRequest("drink")
                    .id("1")
                    .source("user", "kimchy",
                            "message", "trying out Elasticsearch");

        } catch (Exception e) {
            System.out.println(11);
        }


    }

    public void get() {

    }

    public void search() {

    }
}
