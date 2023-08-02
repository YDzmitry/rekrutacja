package com.lastminute.recruitment.config;

import com.lastminute.recruitment.client.HtmlWikiClient;
import com.lastminute.recruitment.domain.WikiReader;
import com.lastminute.recruitment.reader.HtmlWikiPageConverter;
import com.lastminute.recruitment.reader.HtmlWikiReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!json")
public class HtmlWikiConfiguration {

    @Bean
    public WikiReader htmlWikiReader(HtmlWikiClient htmlWikiClient, HtmlWikiPageConverter htmlWikiPageConverter) {
        return new HtmlWikiReader(htmlWikiClient, htmlWikiPageConverter);
    }

    @Bean
    public HtmlWikiPageConverter htmlWikiPageConverter() {
        return new HtmlWikiPageConverter();
    }

    @Bean
    public HtmlWikiClient htmlWikiClient() {
        return new HtmlWikiClient();
    }
}
