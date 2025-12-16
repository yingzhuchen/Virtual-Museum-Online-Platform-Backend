package org.openapitools.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {
    @Bean
    public static RestHighLevelClient restHighLevelClient(){
        HttpHost httpHost = new HttpHost("localhost",9200);
        RestClientBuilder clientBuilder = RestClient.builder(httpHost);
        RestHighLevelClient client = new RestHighLevelClient(clientBuilder);
        return  client;
    }
}


