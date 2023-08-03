package com.lastminute.recruitment.reader;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.lastminute.recruitment.config.HtmlWikiConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = HtmlWikiConfiguration.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "json")
public class HtmlWikiReaderJsonProfileIT {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void checkConfig() {
        // Then
        assertThrows(NoSuchBeanDefinitionException.class,
            //When
            () -> applicationContext.getBean(HtmlWikiConfiguration.class));
    }
}