package com.lastminute.recruitment.domain.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class WikiUrlFormatterTest {

    @Test
    void formatHyperlink() {
        // Given
        String hyperlink = "http://link.com/1";
        // When
        String result = WikiUrlFormatter.formatHyperlink(hyperlink);
        // Then
        assertEquals("\"" + hyperlink + "\"", result);
    }

    @Test
    void formatHyperlinks() {
        // Given
        String firstHyperLink = "http://link.com/1";
        String secondHyperLink = "http://link.com/2";
        // When
        List<String> result = WikiUrlFormatter.formatHyperlinks(Arrays.asList(firstHyperLink, secondHyperLink));
        // Then
        assertEquals("\"" + firstHyperLink + "\"", result.get(0));
        assertEquals("\"" + secondHyperLink + "\"", result.get(1));
    }
}