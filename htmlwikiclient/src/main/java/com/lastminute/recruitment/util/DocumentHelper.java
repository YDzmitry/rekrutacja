package com.lastminute.recruitment.util;

import static java.util.Objects.isNull;

import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class DocumentHelper {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentHelper.class);

    public static Document getHtmlDocumentByFileName(String fileName) {
        if (StringUtils.isEmpty(fileName)) throw new IllegalArgumentException("Invalid filename");

        Document document;
        try {
            document = Jsoup.parse(new File(fileName));
        } catch (IOException e) {
            LOG.warn("Cannot proceed file with name : %s".formatted(fileName), e);
            throw new WikiPageNotFound();
        }
        return document;
    }

    public static String getTitle(Document document) {
        checkDocument(document);

        Element title = document.select("title").first();
        return Optional.ofNullable(title).map(Element::text).orElse(null);
    }

    public static String getContent(Document document) {
        checkDocument(document);

        Element content = document.select("p").first();
        return Optional.ofNullable(content).map(Element::text).orElse(null);
    }

    public static String getSelfLink(Document document) {
        checkDocument(document);

        Element selfLink = document.select("meta").first();
        return Optional.ofNullable(selfLink).map(lnk -> lnk.attr("selfLink")).orElse(null);
    }

    public static List<String> getLinks(Document document) {
        checkDocument(document);

        Elements linkElements = document.select("a");
        return linkElements.stream().map(linkElement -> linkElement.attr("href")).toList();
    }

    private static void checkDocument(Document document) {
        if (isNull(document)) throw new IllegalArgumentException("Invalid document");
    }

}
