package com.jujeol.elasticsearch.domain.reopsitory;

import org.elasticsearch.index.query.MultiMatchQueryBuilder.Type;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

public class DrinkDocumentQueryBuilder {

    public static Query createQuery(String searchWords, Pageable pageable) {
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        createBoolQuery(query, searchWords);
        return query.withPageable(pageable)
                .build();
    }

    private static void createBoolQuery(NativeSearchQueryBuilder query, String queryString) {
        query.withQuery(QueryBuilders.boolQuery()
                .should(QueryBuilders.matchPhraseQuery("name", queryString).slop(1).boost(2f))
                .should(QueryBuilders.matchPhraseQuery("englishName", queryString).slop(1).boost(2f))
                .should(QueryBuilders.multiMatchQuery(queryString, "name", "englishName").type(Type.BEST_FIELDS).tieBreaker(0.3f))
                .should(QueryBuilders.matchQuery("category", queryString).boost(0.5f))
        );
    }
}
