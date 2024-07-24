package com.task.app;

import com.task.app.exception.DocumentDuplicationException;
import com.task.app.repository.DocumentRepository;
import com.task.app.repository.InMemoryDocumentRepository;
import com.task.util.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DocumentManagerTest {

    private final DocumentRepository documentRepository = new InMemoryDocumentRepository();
    private final DocumentManager documentManager = new DocumentManager(documentRepository);

    @BeforeEach
    public void setUp() {
        documentRepository.findAll().clear();
    }

    @Test
    @DisplayName("Test save document functionality")
    void givenDocument_whenSaveDocument_thenDocumentSaved() {
        //given
        Document document = DataUtils.getDocumentTransient();
        //when
        documentManager.save(document);
        //then
        assertFalse(document.getId().isEmpty());
        assertTrue(documentRepository.findAll().contains(document));
    }

    @Test
    @DisplayName("Test save duplicate document functionality")
    void givenDocument_whenSaveDocument_thenExceptionIsThrown() {
        //given
        Document document = DataUtils.getDocumentTransient();
        documentManager.save(document);
        //when
        //then
        DocumentDuplicationException exception = assertThrows(DocumentDuplicationException.class, () -> documentManager.save(document));
        assertEquals("Document with this id already exists.", exception.getMessage());
    }

    @Test
    @DisplayName("Test search document functionality")
    void givenSearchRequest_whenSearchDocument_thenDocumentFound() {
        //given
        Document document1 = DataUtils.getDocument1();
        Document document2 = DataUtils.getDocument2();
        Document document3 = DataUtils.getDocument3();
        documentRepository.save(document1);
        documentRepository.save(document2);
        documentRepository.save(document3);

        SearchRequest searchRequest = DataUtils.getSearchRequest();
        //when
        List<Document> obtainedList = documentManager.search(searchRequest);
        //then
        assertFalse(obtainedList.isEmpty());
        assertEquals(1, obtainedList.size());
        assertEquals(document2, obtainedList.get(0));
    }

    @Test
    @SuppressWarnings("all")
    @DisplayName("Test find by id document functionality")
    void givenId_whenFindById_thenSuccess() {
        //given
        Document document = DataUtils.getDocument1();
        documentManager.save(document);
        //when
        Optional<Document> obtainedDocument = documentManager.findById("1");
        //then
        assertEquals(document, obtainedDocument.get());
    }

    @Test
    @DisplayName("Test find by id when document doesn't exist functionality")
    void givenId_whenFindById_thenEmptyOptional() {
        //given
        //when
        Optional<Document> obtainedDocument = documentManager.findById("1");
        //then
        assertTrue(obtainedDocument.isEmpty());
    }
}