package com.lastminute.recruitment.domain;

import static com.lastminute.recruitment.domain.util.WikiUrlFormatter.formatHyperlinks;

import com.lastminute.recruitment.domain.error.WikiPageNotFound;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiScrapper {

    private static final Logger LOG = LoggerFactory.getLogger(WikiScrapper.class);

    private final WikiReader wikiReader;
    private final WikiPageRepository repository;

    public WikiScrapper(WikiReader wikiReader, WikiPageRepository repository) {
        this.wikiReader = wikiReader;
        this.repository = repository;
    }

    public void read(String link) {
        Set<String> visitedPages = new HashSet<>();
        Queue<String> pagesToVisit = new LinkedList<>();
        processRoot(link, visitedPages, pagesToVisit);
        processChildren(link, pagesToVisit, visitedPages);
    }

    private void processRoot(String link, Set<String> visitedPages, Queue<String> pagesToVisit) {
        WikiPage rootPage = wikiReader.read(link);
        visitedPages.add(link);
        pagesToVisit.addAll(formatHyperlinks(rootPage.getLinks()));
        repository.save(rootPage);
    }

    private void processChildren(String link, Queue<String> pagesToVisit, Set<String> visitedPages) {
        while(!pagesToVisit.isEmpty()) {
            String pageLink = pagesToVisit.poll();
            if(visitedPages.add(pageLink)) {
                try {
                    WikiPage page = wikiReader.read(pageLink);
                    pagesToVisit.addAll(formatHyperlinks(page.getLinks()));
                    repository.save(page);
                } catch (WikiPageNotFound ignore) {
                    LOG.info("Processing link : %s was unsuccessful, page not found".formatted(link));
                }
            }
        }
    }

}
