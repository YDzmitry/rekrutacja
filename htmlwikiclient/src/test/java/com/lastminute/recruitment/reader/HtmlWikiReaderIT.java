package com.lastminute.recruitment.reader;

import static org.junit.jupiter.api.Assertions.*;

import com.lastminute.recruitment.config.HtmlWikiConfiguration;
import com.lastminute.recruitment.domain.WikiPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.CollectionUtils;

@ContextConfiguration(classes = {
    HtmlWikiConfiguration.class,
})
@ExtendWith(SpringExtension.class)
@Profile("html")
class HtmlWikiReaderIT {

    @Autowired
    private HtmlWikiReader htmlWikiReader;

    @Test
    void read() {
        // Given
        String link = "\"http://wikiscrapper.test/site123\"";
        // When
        WikiPage wikiPage = htmlWikiReader.read(link);
        // Then
        assertNotNull(wikiPage);
        assertEquals("Content 123", wikiPage.getContent());
        assertEquals("Site 123", wikiPage.getTitle());
        assertEquals("http://wikiscrapper.test/site123", wikiPage.getSelfLink());
        assertFalse(CollectionUtils.isEmpty(wikiPage.getLinks()));
        assertEquals(2, wikiPage.getLinks().size());

        String firstLink = wikiPage.getLinks().get(0);
        assertEquals("http://wikiscrapper.test/site1234", firstLink);

        String secondLink = wikiPage.getLinks().get(1);
        assertEquals("http://wikiscrapper.test/site1234", secondLink);
    }

    @Test
    void readSiteWithNoContent() {
        // Given
        String link = "\"http://wikiscrapper.test/site-no-content\"";
        // When
        WikiPage wikiPage = htmlWikiReader.read(link);
        // Then
        assertNotNull(wikiPage);
        assertNull(wikiPage.getContent());
        assertEquals("Site Without Content", wikiPage.getTitle());
        assertEquals("http://wikiscrapper.test/site-no-content", wikiPage.getSelfLink());
        assertFalse(CollectionUtils.isEmpty(wikiPage.getLinks()));
        assertEquals(2, wikiPage.getLinks().size());

        String firstLink = wikiPage.getLinks().get(0);
        assertEquals("http://wikiscrapper.test/site1234", firstLink);

        String secondLink = wikiPage.getLinks().get(1);
        assertEquals("http://wikiscrapper.test/site1234", secondLink);
    }

    @Test
    void readSiteWithNoLinks() {
        // Given
        String link = "\"http://wikiscrapper.test/site-no-links\"";
        // When
        WikiPage wikiPage = htmlWikiReader.read(link);
        // Then
        assertNotNull(wikiPage);
        assertEquals("Content No Links", wikiPage.getContent());
        assertEquals("Site site-no-links", wikiPage.getTitle());
        assertEquals("http://wikiscrapper.test/site-no-links", wikiPage.getSelfLink());
        assertTrue(CollectionUtils.isEmpty(wikiPage.getLinks()));
    }

    @Test
    void readWithNoSelfLink() {
        // Given
        String link = "\"http://wikiscrapper.test/site-no-selflink\"";
        // When
        WikiPage wikiPage = htmlWikiReader.read(link);
        // Then
        assertNotNull(wikiPage);
        assertEquals("Content site-no-selflink", wikiPage.getContent());
        assertEquals("Site site-no-selflink", wikiPage.getTitle());
        assertNull(wikiPage.getSelfLink());
        assertFalse(CollectionUtils.isEmpty(wikiPage.getLinks()));
        assertEquals(2, wikiPage.getLinks().size());

        String firstLink = wikiPage.getLinks().get(0);
        assertEquals("http://wikiscrapper.test/site1234", firstLink);

        String secondLink = wikiPage.getLinks().get(1);
        assertEquals("http://wikiscrapper.test/site1234", secondLink);
    }

    @Test
    void readWithNoTitle() {
        // Given
        String link = "\"http://wikiscrapper.test/site-no-title\"";
        // When
        WikiPage wikiPage = htmlWikiReader.read(link);
        // Then
        assertNotNull(wikiPage);
        assertEquals("Content site-no-title", wikiPage.getContent());
        assertNull(wikiPage.getTitle());
        assertEquals("http://wikiscrapper.test/site-no-title", wikiPage.getSelfLink());
        assertFalse(CollectionUtils.isEmpty(wikiPage.getLinks()));
        assertEquals(2, wikiPage.getLinks().size());

        String firstLink = wikiPage.getLinks().get(0);
        assertEquals("http://wikiscrapper.test/site1234", firstLink);

        String secondLink = wikiPage.getLinks().get(1);
        assertEquals("http://wikiscrapper.test/site1234", secondLink);
    }
}