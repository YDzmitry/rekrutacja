package com.lastminute.recruitment.domain.util;

import java.util.ArrayList;
import java.util.List;

public class WikiUrlFormatter {

    public static String formatHyperlink(String hyperlink) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        sb.append(hyperlink);
        sb.append("\"");

        return sb.toString();
    }

    public static List<String> formatHyperlinks(List<String> links) {
        List<String> formattedLinks = new ArrayList<>();
        for (String link: links) {
            formattedLinks.add(formatHyperlink(link));
        }
        return formattedLinks;
    }
}
