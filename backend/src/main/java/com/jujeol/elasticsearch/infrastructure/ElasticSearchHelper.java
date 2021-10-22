package com.jujeol.elasticsearch.infrastructure;

import static java.util.stream.Collectors.toList;

import com.jujeol.drink.drink.domain.Drink;
import com.jujeol.drink.drink.domain.repository.DrinkRepository;
import com.jujeol.elasticsearch.application.SearchHelper;
import com.jujeol.elasticsearch.domain.DrinkDocument;
import com.jujeol.elasticsearch.domain.reopsitory.DrinkDocumentQueryBuilder;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Profile("!test")
public class ElasticSearchHelper implements SearchHelper {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final DrinkRepository drinkRepository;

    @Override
    public DrinkSearchResult searchDrinkIds(String keyword, Pageable pageable) {
        final Query query = DrinkDocumentQueryBuilder.createQuery(keyword, pageable);

        final SearchHits<DrinkDocument> searchHits = elasticsearchRestTemplate
                .search(query, DrinkDocument.class, IndexCoordinates.of("drink"));
        final SearchPage<DrinkDocument> searchHitsByPage = SearchHitSupport
                .searchPageFor(searchHits, query.getPageable());

        List<Long> drinkIds = searchHitsByPage.stream()
                .map(searchHit -> searchHit.getContent().getId())
                .collect(toList());

        List<Drink> drinksByIds = drinkRepository.findByIds(drinkIds);
        return new DrinkSearchResult(
                sortDrinkBySearchHit(drinkIds, drinksByIds),
                pageable,
                searchHitsByPage.getTotalElements());
    }

    private List<Drink> sortDrinkBySearchHit(List<Long> drinkIds, List<Drink> drinks) {
        List<Drink> sortedDrinks = new ArrayList<>();
        for (Long id : drinkIds) {
            sortedDrinks.add(
                    drinks.stream()
                            .filter(drink -> id.equals(drink.getId()))
                            .findAny()
                            .orElseThrow()
            );
        }
        return sortedDrinks;
    }
}
