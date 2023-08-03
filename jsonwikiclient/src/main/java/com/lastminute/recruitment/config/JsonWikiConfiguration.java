package com.lastminute.recruitment.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.recruitment.client.JsonWikiClient;
import com.lastminute.recruitment.domain.WikiReader;
import com.lastminute.recruitment.reader.JsonWikiPageConverter;
import com.lastminute.recruitment.reader.JsonWikiReader;
import com.lastminute.recruitment.util.JsonWikiPageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("json")
public class JsonWikiConfiguration {

    @Bean
    public WikiReader jsonWikiReader(JsonWikiClient jsonWikiClient, JsonWikiPageConverter jsonWikiPageConverter, JsonWikiPageHelper jsonWikiPageHelper) {
        return new JsonWikiReader(jsonWikiClient, jsonWikiPageConverter, jsonWikiPageHelper);
    }

    @Bean
    public JsonWikiClient jsonWikiClient() {
        return new JsonWikiClient();
    }

    @Bean
    public JsonWikiPageConverter jsonWikiPageConverter() {
        return new JsonWikiPageConverter();
    }

    @Bean
    public JsonWikiPageHelper jsonWikiPageHelper(ObjectMapper objectMapper) {
        return new JsonWikiPageHelper(objectMapper);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return  new ObjectMapper();
    }
}
