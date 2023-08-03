package com.lastminute.recruitment.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class WikiScrapperTest {

    private final WikiReader wikiReader = mock(WikiReader.class);
    private final WikiPageRepository repository = mock(WikiPageRepository.class);

    private final WikiScrapper wikiScrapper = new WikiScrapper(wikiReader, repository);

    @Test
    void read() {
        // Given
        String link = "\"http://link.com/\"";
        WikiPage wikiPage = storedWiKiPage(link);

        when(wikiReader.read(any())).thenReturn(wikiPage);
        // When
        wikiScrapper.read(link);
        // Then
        verify(wikiReader).read(link);
        verify(repository).save(wikiPage);
    }

    @Test
    void readCircular() {
        // Given
        ArgumentCaptor<String> captorWikiReader = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<WikiPage> captorRepository = ArgumentCaptor.forClass(WikiPage.class);

        String firstLink = "\"http://link.com/1\"";
        String firstHyperLink = "http://link.com/1";
        String secondLink = "\"http://link.com/2\"";
        String secondHyperLink = "http://link.com/2";

        WikiPage firstWikiPage = storedWiKiPage(firstLink, secondHyperLink);
        WikiPage secondWikiPage = storedWiKiPage(secondLink, firstHyperLink);

        Map<String, WikiPage> pages = Map.of(
            firstLink, firstWikiPage,
            secondLink, secondWikiPage
        );

        when(wikiReader.read(any())).thenAnswer(invocation -> pages.get(invocation.getArgument(0)));
        // When
        wikiScrapper.read(firstLink);
        // Then
        verify(wikiReader, times(2)).read(captorWikiReader.capture());
        verify(repository, times(2)).save(captorRepository.capture());

        List<String> wikiReaderCapturedValues = captorWikiReader.getAllValues();
        assertEquals(2, wikiReaderCapturedValues.size());
        assertEquals(firstLink, wikiReaderCapturedValues.get(0));
        assertEquals(secondLink, wikiReaderCapturedValues.get(1));

        List<WikiPage> wikiRepositoryCapturedValues = captorRepository.getAllValues();
        assertEquals(2, wikiRepositoryCapturedValues.size());
        assertEquals(firstWikiPage, wikiRepositoryCapturedValues.get(0));
        assertEquals(secondWikiPage, wikiRepositoryCapturedValues.get(1));

    }

    private WikiPage storedWiKiPage(String link, String childLink) {
        WikiPage wikiPage = new WikiPage(
            "title of page %s".formatted(link),
            "content of page %s".formatted(link),
            link,
            Arrays.asList(childLink)
        );
        return wikiPage;
    }

    private WikiPage storedWiKiPage(String link) {
        WikiPage wikiPage = new WikiPage(
            "title of page %s".formatted(link),
            "content of page %s".formatted(link),
            link,
            new ArrayList<>()
        );
        return wikiPage;
    }
}