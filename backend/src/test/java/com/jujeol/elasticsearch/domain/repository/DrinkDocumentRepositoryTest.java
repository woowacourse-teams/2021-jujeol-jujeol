package com.jujeol.elasticsearch.domain.repository;

import com.jujeol.elasticsearch.domain.DrinkDocument;
import com.jujeol.elasticsearch.domain.reopsitory.DrinkDocumentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class DrinkDocumentRepositoryTest {

    @Autowired
    private DrinkDocumentRepository drinkDocumentRepository;

    @Test
    @DisplayName("전체 삭제 테스트")
    @Rollback(false)
    public void deleteAllTest() throws Exception {
        //given
        DrinkDocument nabomBeer = DrinkDocument.builder()
                .id(1L)
                .name("나봄 맥주")
                .englishName("nabom beer")
                .category("맥주")
                .build();
        drinkDocumentRepository.save(nabomBeer);

        //when
//        drinkDocumentRepository.deleteById(1L);

        //then

    }
}
