package com.lastminute.recruitment.rest.wiki;

import com.lastminute.recruitment.domain.WikiScrapper;
import com.lastminute.recruitment.rest.wiki.validation.WikiLink;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/wiki")
@RestController
@Validated
public class WikiScrapperResource {

    private final WikiScrapper wikiScrapper;

    public WikiScrapperResource(WikiScrapper wikiScrapper) {
        this.wikiScrapper = wikiScrapper;
    }

    @PostMapping("/scrap")
    public void scrapWikipedia(@RequestBody @Valid @WikiLink String link) {
        wikiScrapper.read(link);
    }

}
