package com.lastminute.recruitment.reader;

import com.lastminute.recruitment.client.HtmlWikiClient;
import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.WikiReader;
import com.lastminute.recruitment.util.DocumentHelper;
import org.jsoup.nodes.Document;

public class HtmlWikiReader implements WikiReader {

    private final HtmlWikiClient htmlWikiClient;
    private final HtmlWikiPageConverter htmlWikiPageConverter;

    public HtmlWikiReader(
        HtmlWikiClient htmlWikiClient,
        HtmlWikiPageConverter htmlWikiPageConverter
    ) {
        this.htmlWikiClient = htmlWikiClient;
        this.htmlWikiPageConverter = htmlWikiPageConverter;
    }

    @Override
    public WikiPage read(String link) {
        String fileName = htmlWikiClient.readFileName(link);
        Document document = DocumentHelper.getHtmlDocumentByFileName(fileName);
        return htmlWikiPageConverter.convert(document);
    }

}
