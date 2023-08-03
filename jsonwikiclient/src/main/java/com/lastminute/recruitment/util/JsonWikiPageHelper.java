package com.lastminute.recruitment.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import com.lastminute.recruitment.reader.JsonWikiPage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class JsonWikiPageHelper {

    private static final Logger LOG = LoggerFactory.getLogger(JsonWikiPageHelper.class);

    private final ObjectMapper objectMapper;

    public JsonWikiPageHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JsonWikiPage readByFileName(String fileName) {
        if (StringUtils.isBlank(fileName)) throw new IllegalArgumentException("File name must be non-null");

        JsonWikiPage jsonWikiPage;
        try {
            jsonWikiPage = objectMapper.readValue(new File(fileName), JsonWikiPage.class);
        } catch (IOException e) {
            LOG.warn("Cannot proceed file with name : %s".formatted(fileName), e);
            throw new WikiPageNotFound();
        }
        return jsonWikiPage;
    }
}
