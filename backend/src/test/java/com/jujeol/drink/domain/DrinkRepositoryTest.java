package com.jujeol.drink.domain;

import com.jujeol.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.domain.repository.ReviewRepository;
import com.jujeol.member.domain.MemberRepository;
import com.jujeol.member.domain.PreferenceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class DrinkRepositoryTest {
    @Autowired
    private PreferenceRepository preferenceRepository;
    @Autowired
    private DrinkRepository drinkRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TestEntityManager testEntityManager;

//    @Test
//    void findAllOrderByViewCountTest() {
//        Drink stella = Drink.from(
//                "스텔라", "stella", 5.5, "KakaoTalk_Image_2021-07-08-19-58-09_001.png", Category.BEER);
//        Drink saveDrink = drinkRepository.save(stella);
//
//        saveDrink.updateViewCount();
//
//        testEntityManager.flush();
//        testEntityManager.clear();
//
//        System.out.println("=============");
//        System.out.println(saveDrink.getViewCount());
//    }
}
