package com.lastminute.recruitment.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.lastminute.recruitment.config.JsonWikiConfiguration;
import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.WikiReader;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.CollectionUtils;

@ContextConfiguration(classes = JsonWikiConfiguration.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "json")
class JsonWikiReaderIT {

    @Autowired
    private WikiReader jsonWikiReader;

    @Test
    void read() {
        // Given
        String link = "\"http://wikiscrapper.test/site123\"";
        // When
        WikiPage wikiPage = jsonWikiReader.read(link);
        // Then
        assertNotNull(wikiPage);
        assertEquals("Content 123", wikiPage.getContent());
        assertEquals("Site 123", wikiPage.getTitle());
        assertEquals("http://wikiscrapper.test/site123", wikiPage.getSelfLink());
        assertFalse(CollectionUtils.isEmpty(wikiPage.getLinks()));
        assertEquals(2, wikiPage.getLinks().size());

        String firstLink = wikiPage.getLinks().get(0);
        assertEquals("http://wikiscrapper.test/site3", firstLink);

        String secondLink = wikiPage.getLinks().get(1);
        assertEquals("http://wikiscrapper.test/site4", secondLink);
    }

    @Test
    void readSiteWithNoContent() {
        // Given
        String link = "\"http://wikiscrapper.test/site-no-content\"";
        // When
        WikiPage wikiPage = jsonWikiReader.read(link);
        // Then
        assertNotNull(wikiPage);
        assertNull(wikiPage.getContent());
        assertEquals("Site no content", wikiPage.getTitle());
        assertEquals("http://wikiscrapper.test/site-no-content", wikiPage.getSelfLink());
        assertFalse(CollectionUtils.isEmpty(wikiPage.getLinks()));
        assertEquals(2, wikiPage.getLinks().size());

        String firstLink = wikiPage.getLinks().get(0);
        assertEquals("http://wikiscrapper.test/site3", firstLink);

        String secondLink = wikiPage.getLinks().get(1);
        assertEquals("http://wikiscrapper.test/site4", secondLink);
    }

    @Test
    void readSiteWithNoLinks() {
        // Given
        String link = "\"http://wikiscrapper.test/site-no-links\"";
        // When
        WikiPage wikiPage = jsonWikiReader.read(link);
        // Then
        assertNotNull(wikiPage);
        assertEquals("Content no links", wikiPage.getContent());
        assertEquals("Site no links", wikiPage.getTitle());
        assertEquals("http://wikiscrapper.test/site-no-links", wikiPage.getSelfLink());
        assertTrue(CollectionUtils.isEmpty(wikiPage.getLinks()));
    }

    @Test
    void readWithNoSelfLink() {
        // Given
        String link = "\"http://wikiscrapper.test/site-no-selflink\"";
        // When
        WikiPage wikiPage = jsonWikiReader.read(link);
        // Then
        assertNotNull(wikiPage);
        assertEquals("Content no selflink", wikiPage.getContent());
        assertEquals("Site site-no-selflink", wikiPage.getTitle());
        assertNull(wikiPage.getSelfLink());
        assertFalse(CollectionUtils.isEmpty(wikiPage.getLinks()));
        assertEquals(2, wikiPage.getLinks().size());

        String firstLink = wikiPage.getLinks().get(0);
        assertEquals("http://wikiscrapper.test/site3", firstLink);

        String secondLink = wikiPage.getLinks().get(1);
        assertEquals("http://wikiscrapper.test/site4", secondLink);
    }

    @Test
    void readWithNoTitle() {
        // Given
        String link = "\"http://wikiscrapper.test/site-no-title\"";
        // When
        WikiPage wikiPage = jsonWikiReader.read(link);
        // Then
        assertNotNull(wikiPage);
        assertEquals("Content no title", wikiPage.getContent());
        assertNull(wikiPage.getTitle());
        assertEquals("http://wikiscrapper.test/site-no-title", wikiPage.getSelfLink());
        assertFalse(CollectionUtils.isEmpty(wikiPage.getLinks()));
        assertEquals(2, wikiPage.getLinks().size());

        String firstLink = wikiPage.getLinks().get(0);
        assertEquals("http://wikiscrapper.test/site3", firstLink);

        String secondLink = wikiPage.getLinks().get(1);
        assertEquals("http://wikiscrapper.test/site4", secondLink);
    }

    @Test
    void readInvalidSite() {
        // Given
        String link = "\"http://wikiscrapper.test/site-invalid-json\"";
        // Then
        assertThrows(
            WikiPageNotFound.class,
            //When
            () -> jsonWikiReader.read(link)
        );
    }

}