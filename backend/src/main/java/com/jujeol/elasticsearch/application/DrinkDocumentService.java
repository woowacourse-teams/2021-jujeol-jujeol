package com.jujeol.elasticsearch.application;

import static java.util.stream.Collectors.toList;

import com.jujeol.drink.drink.application.dto.DrinkDto;
import com.jujeol.drink.drink.application.dto.SearchDto;
import com.jujeol.drink.drink.domain.Drink;
import com.jujeol.drink.drink.domain.repository.DrinkRepository;
import com.jujeol.elasticsearch.domain.DrinkDocument;
import com.jujeol.elasticsearch.domain.reopsitory.DrinkDocumentQueryBuilder;
import com.jujeol.elasticsearch.domain.reopsitory.DrinkDocumentRepository;
import com.jujeol.preference.domain.Preference;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DrinkDocumentService {

    private final DrinkRepository drinkRepository;
    private final DrinkDocumentRepository drinkDocumentRepository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public void save(DrinkDocument drinkDocument) {
        drinkDocumentRepository.save(drinkDocument);
    }

    public void delete(DrinkDocument drinkDocument) {
        drinkDocumentRepository.delete(drinkDocument);
    }

    public Page<DrinkDto> showDrinksBySearch(SearchDto searchDto, Pageable pageable) {
        final Query query = DrinkDocumentQueryBuilder.createQuery(
                searchDto.getSearch(),
                pageable
        );

        final SearchHits<DrinkDocument> searchHits = elasticsearchRestTemplate.search(query, DrinkDocument.class, IndexCoordinates.of("drink"));
        final SearchPage<DrinkDocument> searchHitsByPage = SearchHitSupport.searchPageFor(searchHits, query.getPageable());
        List<Long> drinkIds = searchHitsByPage.stream()
                .map(searchHit -> searchHit.getContent().getId())
                .collect(toList());

        // TODO : 선호도 0점 변경 요망
        List<DrinkDto> drinkDtos = drinkRepository.findByIds(drinkIds)
                .stream()
                .map(drink -> DrinkDto.create(
                        drink, Preference.create(drink, 0)))
                .collect(toList());

        return new PageImpl<>(drinkDtos, searchHitsByPage.getPageable(), searchHitsByPage.getTotalElements());
    }

    public void sync() {
        List<DrinkDocument> drinkDocuments = drinkRepository.findAll()
                .stream()
                .map(Drink::toDrinkDocument)
                .collect(toList());
        drinkDocumentRepository.saveAll(drinkDocuments);
    }
}
