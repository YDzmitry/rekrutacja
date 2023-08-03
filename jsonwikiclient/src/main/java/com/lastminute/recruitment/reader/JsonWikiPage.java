package com.lastminute.recruitment.reader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonWikiPage {

    private String title;
    private String content;
    private String selfLink;
    private List<String> links;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public List<String> getLinks() {
        return links;
    }
}
