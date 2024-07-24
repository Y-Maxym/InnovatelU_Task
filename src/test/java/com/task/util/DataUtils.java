package com.task.util;

import com.task.app.Author;
import com.task.app.Document;
import com.task.app.SearchRequest;

import java.time.Instant;
import java.util.Arrays;

public class DataUtils {

    public static Author getJohnDouAuthor() {
        return Author.builder()
                .id("1")
                .name("John Doe")
                .build();
    }

    public static Author getMikeSmithAuthor() {
        return Author.builder()
                .id("2")
                .name("Mike Smith")
                .build();
    }

    public static Document getDocument1() {
        return Document.builder()
                .id("1")
                .title("Spring Framework")
                .content("This is a document about Spring Framework")
                .author(getJohnDouAuthor())
                .created(Instant.parse("2023-01-01T10:00:00Z"))
                .build();
    }

    public static Document getDocument2() {
        return Document.builder()
                .id("2")
                .title("Java Concurrency")
                .content("Concurrency in Java")
                .author(getJohnDouAuthor())
                .created(Instant.parse("2023-02-01T10:00:00Z"))
                .build();
    }

    public static Document getDocument3() {
        return Document.builder()
                .id("3")
                .title("REST API")
                .content("Designing RESTful APIs")
                .author(getMikeSmithAuthor())
                .created(Instant.parse("2023-10-01T10:00:00Z"))
                .build();
    }

    public static Document getDocumentTransient() {
        return Document.builder()
                .title("Blank")
                .content("Blank")
                .author(getMikeSmithAuthor())
                .created(Instant.parse("2023-09-01T10:00:00Z"))
                .build();
    }

    public static SearchRequest getSearchRequest() {
        return SearchRequest.builder()
                .titlePrefixes(Arrays.asList("Spring", "Java"))
                .authorIds(Arrays.asList("1", "2"))
                .createdFrom(Instant.parse("2023-02-01T00:00:00Z"))
                .createdTo(Instant.parse("2023-12-31T23:59:59Z"))
                .build();
    }
}
