package com.jujeol.admin.ui;

import com.jujeol.admin.ui.dto.AdminDrinkResponse;
import com.jujeol.admin.ui.dto.CategoryResponse;
import com.jujeol.admin.ui.dto.DrinkRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

public interface DrinkService {

    Page<AdminDrinkResponse> findDrinks(Pageable pageable);

    List<AdminDrinkResponse> saveDrinks(List<DrinkRequest> drinkRequests);

    void removeDrink(Long id);

    void updateDrink(Long id, DrinkRequest drinkRequest);

    @Service
    class DrinkServiceTemp implements DrinkService {

        private List<AdminDrinkResponse> drinkResponses = new ArrayList<>();
        private Long id = 8L;

        @Override
        public Page<AdminDrinkResponse> findDrinks(Pageable pageable) {
            return new Page<AdminDrinkResponse>() {
                @Override
                public int getTotalPages() {
                    return 5;
                }

                @Override
                public long getTotalElements() {
                    return 47;
                }

                @Override
                public <U> Page<U> map(
                        Function<? super AdminDrinkResponse, ? extends U> converter) {
                    return null;
                }

                @Override
                public int getNumber() {
                    return 0;
                }

                @Override
                public int getSize() {
                    return 0;
                }

                @Override
                public int getNumberOfElements() {
                    return 7;
                }

                @Override
                public List<AdminDrinkResponse> getContent() {
                    return DummyData.drinkResponses();
                }

                @Override
                public boolean hasContent() {
                    return false;
                }

                @Override
                public Sort getSort() {
                    return null;
                }

                @Override
                public boolean isFirst() {
                    return false;
                }

                @Override
                public boolean isLast() {
                    return false;
                }

                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public boolean hasPrevious() {
                    return false;
                }

                @Override
                public Pageable nextPageable() {
                    return null;
                }

                @Override
                public Pageable previousPageable() {
                    return null;
                }

                @Override
                public Iterator<AdminDrinkResponse> iterator() {
                    return null;
                }
            };
        }

        @Override
        public List<AdminDrinkResponse> saveDrinks(List<DrinkRequest> drinkRequests) {
            final List<AdminDrinkResponse> responses = drinkRequests.stream()
                    .map(drinkRequest -> new AdminDrinkResponse(
                            id++,
                            drinkRequest.getName(),
                            drinkRequest.getEnglishName(),
                            drinkRequest.getImageUrl(),
                            new CategoryResponse(drinkRequest.getCategory().getId(), drinkRequest.getCategory().getName()),
                            drinkRequest.getAlcoholByVolume())
                    )
                    .collect(Collectors.toList());
            this.drinkResponses.addAll(responses);
            return drinkResponses;
        }

        @Override
        public void removeDrink(Long id) {
            drinkResponses.removeIf(adminDrinkResponse -> adminDrinkResponse.getId().equals(id));
        }

        @Override
        public void updateDrink(Long id, DrinkRequest drinkRequest) {
            final AdminDrinkResponse adminDrinkResponse1 = drinkResponses.stream()
                    .filter(adminDrinkResponse -> adminDrinkResponse.getId().equals(id))
                    .findAny().orElseThrow(IllegalStateException::new);
            adminDrinkResponse1.changeInfo(drinkRequest);
        }
    }
}
