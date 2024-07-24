package com.task.app;

import com.task.app.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class DocumentManager {

    private final DocumentRepository repository;

    /**
     * Upsert the document to your storage
     * And generate unique id if it does not exist
     *
     * @param document - document content and author data
     * @return saved document
     */
    @SuppressWarnings("all")
    public Document save(Document document) {
        if (isNull(document.getId()) || document.getId().isBlank()) {
            String uuid = UUID.randomUUID().toString();
            document.setId(uuid);
        }
        repository.save(document);
        return document;
    }

    /**
     * Find documents which match with request
     *
     * @param request - search request, each field could be null
     * @return list matched documents
     */
    public List<Document> search(SearchRequest request) {
        return repository.findAll().stream()
                .filter(doc -> filterByTitlePrefixes(doc, request.getTitlePrefixes()))
                .filter(doc -> filterByContainsContents(doc, request.getContainsContents()))
                .filter(doc -> filterByAuthorIds(doc, request.getAuthorIds()))
                .filter(doc -> filterByCreatedFrom(doc, request.getCreatedFrom()))
                .filter(doc -> filterByCreatedTo(doc, request.getCreatedTo()))
                .toList();
    }

    /**
     * Find document by id
     *
     * @param id - document id
     * @return optional document
     */
    public Optional<Document> findById(String id) {
        return repository.findById(id);
    }

    private boolean filterByTitlePrefixes(Document document, List<String> titlePrefixes) {
        if (isNull(titlePrefixes) || titlePrefixes.isEmpty()) return true;
        return titlePrefixes.stream().anyMatch(prefix -> document.getTitle().startsWith(prefix));
    }

    private boolean filterByContainsContents(Document document, List<String> containsContents) {
        if (isNull(containsContents) || containsContents.isEmpty()) return true;
        return containsContents.stream().anyMatch(content -> document.getContent().contains(content));
    }

    private boolean filterByAuthorIds(Document document, List<String> authorIds) {
        if (isNull(authorIds) || authorIds.isEmpty()) return true;
        return authorIds.stream().anyMatch(id -> document.getAuthor().getId().equals(id));
    }

    private boolean filterByCreatedFrom(Document document, Instant createdFrom) {
        if (isNull(createdFrom)) return true;
        Instant created = document.getCreated();
        return created.equals(createdFrom) || created.isAfter(createdFrom);
    }

    private boolean filterByCreatedTo(Document document, Instant createdTo) {
        if (isNull(createdTo)) return true;
        Instant created = document.getCreated();
        return created.equals(createdTo) || created.isBefore(createdTo);
    }
}