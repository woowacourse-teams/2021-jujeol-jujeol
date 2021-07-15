package com.jujeol.admin.ui;

import com.jujeol.admin.ui.dto.AdminDrinkResponse;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

public interface DrinkService {

    Page<AdminDrinkResponse> findDrinks(Pageable pageable);

    @Service
    class DrinkServiceTemp implements DrinkService {

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
    }
}
