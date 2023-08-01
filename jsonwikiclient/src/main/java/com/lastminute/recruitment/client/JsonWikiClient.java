package com.lastminute.recruitment.client;

import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Optional;

public class JsonWikiClient {

    private static final Logger LOG = LoggerFactory.getLogger(JsonWikiClient.class);

    public String readFileName(String link) {
        String name = link.replace("\"", "")
                .replace("http://wikiscrapper.test/", "/wikiscrapper/") + ".json";
        Optional<String> fileName = Optional.ofNullable(getClass().getResource(name)).map(URL::getFile);

        if (fileName.isEmpty() || fileName.get().isEmpty()) {
            LOG.info("Cannot find file by provided link : %s".formatted(link));
            throw new WikiPageNotFound();
        } else {
            return fileName.get();
        }
    }
}
