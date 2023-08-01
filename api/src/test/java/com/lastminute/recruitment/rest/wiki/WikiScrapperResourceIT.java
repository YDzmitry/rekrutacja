package com.lastminute.recruitment.rest.wiki;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.recruitment.domain.WikiScrapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WikiScrapperResourceIT {

    @MockBean
    private WikiScrapper wikiScrapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @ValueSource(strings = {
        "  ",
        "http://wikiscrapper.test/site1",
        "\"http://wikiscrapper.test/site1",
        "http://wikiscrapper.test/site1\"",
        "\"http://wikiscrapper.dev/site1\"",
        "\"http://wikiscrapperdev/site1\""
    })
    void testScrapWikiPageInvalidWikiLink(String wikiLink) throws Exception {
        //Given
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post("/wiki/scrap")
            .content(wikiLink);

        // When
        JsonNode response = objectMapper.readValue(
            mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse().getContentAsString(),
            new TypeReference<>() {
            }
        );

        //Then
        assertThat(response).isNotNull();
        JSONAssert.assertEquals(
            """
                {
                    "type": "about:blank",
                    "title": "Bad Request",
                    "status": 400,
                    "instance": "/wiki/scrap",
                    "invalid-params": [
                      {
                        "name": "link",
                        "reason": "Invalid link"
                      }
                    ]
                }
                """,
            objectMapper.writeValueAsString(response),
            false);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "\"http://wikiscrapper.test/site1\"",
        "\"http://wikiscrapper.test/siff3\"",
        "\"http://wikiscrapper.test/st34f\"",
        "\"http://wikiscrapper.test/s\"",
        "\"http://wikiscrapper.test/site3\""
    })
    void testScrapWikiPageValidWikiLink(String wikiLink) throws Exception {
        //Given
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post("/wiki/scrap")
            .content(wikiLink);

        // When
        mockMvc.perform(request)
            .andExpect(status().isOk());

        //Then
        verify(wikiScrapper).read(wikiLink);
    }
}