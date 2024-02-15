package io.library.library_3.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.library.library_3.search.LinearSearchService;
import io.library.library_3.search.SearchService;

@ExtendWith(SpringExtension.class)
public class SearchServiceTest {
    @TestConfiguration
    static class SearchServiceTestConfig {
        @Bean
        @Autowired
        SearchService searchService() {
            return new LinearSearchService();
        }
    }

    @Autowired
    SearchService searchService;

    private static String[] arr;
    private static String[] vals1;
    private static String[] vals2;
    private static String[] vals3;

    @BeforeAll
    public static void setUp() {
        arr = new String[] { "A", "List", "of", "stuff" };
        vals1 = new String[] { "A", "List", "of", "stuff" };
        vals2 = new String[] { "a", "stUFf" };
        vals3 = new String[] { "No", "Match" };
    }

    @Test
    public void search_FullMatch() {
        assertTrue(searchService.search(arr, vals1));
    }

    @Test
    public void search_PartialMatch() {
        assertTrue(searchService.search(arr, vals2));
    }

    @Test
    public void search_NoMatch() {
        assertFalse(searchService.search(arr, vals3));
    }

}
