package com.task.app;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class SearchRequest {

    private List<String> titlePrefixes;
    private List<String> containsContents;
    private List<String> authorIds;
    private Instant createdFrom;
    private Instant createdTo;
}
