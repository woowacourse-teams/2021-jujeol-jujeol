package com.jujeol.elasticsearch.application;

import static java.util.stream.Collectors.toList;

import com.jujeol.drink.drink.application.dto.DrinkDto;
import com.jujeol.elasticsearch.application.dto.SearchDto;
import com.jujeol.drink.drink.domain.Drink;
import com.jujeol.drink.drink.domain.repository.DrinkRepository;
import com.jujeol.elasticsearch.domain.DrinkDocument;
import com.jujeol.elasticsearch.domain.reopsitory.DrinkDocumentRepository;
import com.jujeol.elasticsearch.infrastructure.DrinkSearchResult;
import com.jujeol.member.auth.ui.LoginMember;
import com.jujeol.preference.application.PreferenceService;
import com.jujeol.preference.domain.Preference;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DrinkDocumentService {

    private final PreferenceService preferenceService;
    private final DrinkRepository drinkRepository;
    private final DrinkDocumentRepository drinkDocumentRepository;
    private final SearchHelper searchHelper;

    @Transactional
    public void save(DrinkDocument drinkDocument) {
        drinkDocumentRepository.save(drinkDocument);
    }

    @Transactional
    public void delete(DrinkDocument drinkDocument) {
        drinkDocumentRepository.delete(drinkDocument);
    }

    public Page<DrinkDto> showDrinksBySearch(
            SearchDto searchDto,
            LoginMember loginMember,
            Pageable pageable
    ) {

        DrinkSearchResult drinkSearchResult = searchHelper
                .searchDrinkIds(searchDto.getKeyword(), pageable);

        List<DrinkDto> drinkDtos = drinkSearchResult.getDrinks()
                .stream()
                .map(drink -> DrinkDto.create(drink, Preference.create(drink, 0)))
                .collect(toList());

        addPreferenceRateForLoginMember(loginMember, drinkDtos);

        return new PageImpl<>(drinkDtos,
                drinkSearchResult.getPageable(),
                drinkSearchResult.getTotalElements());
    }


    private void addPreferenceRateForLoginMember(
            LoginMember loginMember,
            List<DrinkDto> sortedDrinkDtos
    ) {
        if (loginMember.isMember()) {
            for (DrinkDto drinkDto : sortedDrinkDtos) {
                drinkDto.addPreferenceRate(
                        preferenceService.showByMemberIdAndDrink(
                                loginMember.getId(),
                                drinkDto.getId())
                );
            }
        }
    }

    @Transactional
    public void sync() {
        drinkDocumentRepository.deleteAll();
        List<DrinkDocument> drinkDocuments = drinkRepository.findAll()
                .stream()
                .map(Drink::toDrinkDocument)
                .collect(toList());
        drinkDocumentRepository.saveAll(drinkDocuments);
    }
}
