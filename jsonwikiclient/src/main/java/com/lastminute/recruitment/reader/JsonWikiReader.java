package com.lastminute.recruitment.reader;

import com.lastminute.recruitment.client.JsonWikiClient;
import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.WikiReader;
import com.lastminute.recruitment.util.JsonWikiPageHelper;

public class JsonWikiReader implements WikiReader {

    private final JsonWikiClient wikiClient;
    private final JsonWikiPageConverter jsonWikiPageConverter;
    private final JsonWikiPageHelper jsonWikiPageHelper;

    public JsonWikiReader(
        JsonWikiClient wikiClient,
        JsonWikiPageConverter jsonWikiPageConverter,
        JsonWikiPageHelper jsonWikiPageHelper
    ) {
        this.wikiClient = wikiClient;
        this.jsonWikiPageConverter = jsonWikiPageConverter;
        this.jsonWikiPageHelper = jsonWikiPageHelper;
    }

    @Override
    public WikiPage read(String link) {
        String fileName = wikiClient.readFileName(link);
        JsonWikiPage jsonWikiPage = jsonWikiPageHelper.readByFileName(fileName);
        return jsonWikiPageConverter.convert(jsonWikiPage);
    }
}
