package com.task.app.repository;

import com.task.app.Document;
import com.task.app.exception.DocumentDuplicationException;
import com.task.util.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryDocumentRepositoryTest {

    private final DocumentRepository documentRepository = new InMemoryDocumentRepository();

    @BeforeEach
    public void setUp() {
        documentRepository.findAll().clear();
    }

    @Test
    @DisplayName("Test save document functionality")
    void givenDocument_whenSave_thenSuccess() {
        //given
        Document document = DataUtils.getDocument1();
        //when
        documentRepository.save(document);
        //then
        assertTrue(documentRepository.findAll().contains(document));
    }

    @Test
    @DisplayName("Test save duplicate document functionality")
    void givenWrongDocument_whenSave_thenExceptionThrown() {
        //given
        Document document = DataUtils.getDocument1();
        documentRepository.save(document);
        //when
        //then
        DocumentDuplicationException exception = assertThrows(DocumentDuplicationException.class, () -> documentRepository.save(document));
        assertEquals("Document with this id already exists.", exception.getMessage());
    }

    @Test
    @SuppressWarnings("all")
    @DisplayName("Test find by id document functionality")
    void givenId_whenFindById_thenSuccess() {
        //given
        Document document = DataUtils.getDocument1();
        documentRepository.save(document);
        //when
        Optional<Document> obtainedDocument = documentRepository.findById("1");
        //then
        assertEquals(document, obtainedDocument.get());
    }

    @Test
    @DisplayName("Test find by id when document doesn't exist functionality")
    void givenId_whenFindById_thenEmptyOptional() {
        //given
        //when
        Optional<Document> obtainedDocument = documentRepository.findById("1");
        //then
        assertTrue(obtainedDocument.isEmpty());
    }

    @Test
    @DisplayName("Test find all documents functionality")
    void givenDocumentsList_whenFindAll_thenSuccess() {
        //given
        Document document1 = DataUtils.getDocument1();
        Document document2 = DataUtils.getDocument2();
        Document document3 = DataUtils.getDocument3();
        documentRepository.save(document1);
        documentRepository.save(document2);
        documentRepository.save(document3);
        //when
        List<Document> documents = documentRepository.findAll();
        //then
        assertEquals(3, documents.size());
        assertTrue(documents.contains(document1));
        assertTrue(documents.contains(document2));
        assertTrue(documents.contains(document3));
    }
}