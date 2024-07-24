package com.task.app.repository;

import com.task.app.Document;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository {

    void save(Document document);

    Optional<Document> findById(String id);

    List<Document> findAll();
}
