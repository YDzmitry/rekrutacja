package com.lastminute.recruitment.rest.wiki.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class WikiLinkValidator implements ConstraintValidator<WikiLink, String> {

    private static final String WIKI_LINK_PATTERN = "\"http://wikiscrapper.test/.*\"";

    @Override
    public boolean isValid(String link, ConstraintValidatorContext constraintValidatorContext) {
        return Optional.ofNullable(link).map(x -> x.matches(WIKI_LINK_PATTERN)).orElse(false);
    }
}
