package com.task.app.repository;

import com.task.app.Document;
import com.task.app.exception.DocumentDuplicationException;

import java.util.*;

public class InMemoryDocumentRepository implements DocumentRepository {

    private final Map<String, Document> documents = new HashMap<>();

    @Override
    public void save(Document document) {
        if (documents.containsKey(document.getId()))
            throw new DocumentDuplicationException("Document with this id already exists.");
        documents.put(document.getId(), document);
    }

    @Override
    public Optional<Document> findById(String id) {
        return Optional.ofNullable(documents.get(id));
    }

    @Override
    public List<Document> findAll() {
        return new ArrayList<>(documents.values());
    }
}