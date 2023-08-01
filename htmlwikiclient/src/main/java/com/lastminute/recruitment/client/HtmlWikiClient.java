package com.lastminute.recruitment.client;

import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Optional;

public class HtmlWikiClient {

    private static final Logger LOG = LoggerFactory.getLogger(HtmlWikiClient.class);

    public String readFileName(String link) {
        String name = link.replace("\"", "")
                .replace("http://wikiscrapper.test/", "/wikiscrapper/") + ".html";
        Optional<String> fileName = Optional.ofNullable(getClass().getResource(name)).map(URL::getFile);

        if (fileName.isEmpty() || fileName.get().isEmpty()) {
            LOG.info("Cannot find file by provided link : %s".formatted(link));
            throw new WikiPageNotFound();
        } else {
            return fileName.get();
        }
    }
}
