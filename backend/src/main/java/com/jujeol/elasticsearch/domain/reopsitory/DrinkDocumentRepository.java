package com.jujeol.elasticsearch.domain.reopsitory;

import com.jujeol.elasticsearch.domain.DrinkDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DrinkDocumentRepository extends ElasticsearchRepository<DrinkDocument, Long> {
}
