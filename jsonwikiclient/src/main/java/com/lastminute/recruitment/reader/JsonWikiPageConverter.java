package com.lastminute.recruitment.reader;

import com.lastminute.recruitment.domain.WikiPage;

public class JsonWikiPageConverter {

    WikiPage convert(JsonWikiPage jsonWikiPage) {
        return new WikiPage(jsonWikiPage.getTitle(), jsonWikiPage.getContent(), jsonWikiPage.getSelfLink(), jsonWikiPage.getLinks());
    }
}
