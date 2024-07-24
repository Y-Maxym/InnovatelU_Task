package com.task.app;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Objects;

@Data
@Builder
public class Document {

    private String id;
    private String title;
    private String content;
    private Author author;
    private Instant created;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(getId(), document.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}