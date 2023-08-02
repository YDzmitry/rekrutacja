package com.lastminute.recruitment.reader;

import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.util.DocumentHelper;
import org.jsoup.nodes.Document;

import java.util.List;

public class HtmlWikiPageConverter {

    public WikiPage convert(Document document) {
        String title = DocumentHelper.getTitle(document);
        String content = DocumentHelper.getContent(document);
        String selfLink = DocumentHelper.getSelfLink(document);
        List<String> links = DocumentHelper.getLinks(document);

        return new WikiPage(title, content, selfLink, links);
    }
}
