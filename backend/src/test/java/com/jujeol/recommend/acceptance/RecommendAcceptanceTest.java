package com.jujeol.recommend.acceptance;

import static com.jujeol.drink.DrinkTestContainer.APPLE;
import static com.jujeol.drink.DrinkTestContainer.KGB;
import static com.jujeol.drink.DrinkTestContainer.OB;
import static com.jujeol.drink.DrinkTestContainer.STELLA;
import static com.jujeol.drink.DrinkTestContainer.TIGER_LEMON;

import com.jujeol.AcceptanceTest;
import com.jujeol.admin.acceptance.AdminAcceptanceTool;
import com.jujeol.drink.DrinkTestContainer;
import com.jujeol.drink.acceptance.DrinkAcceptanceTool;
import com.jujeol.member.acceptance.MemberAcceptanceTool;
import com.jujeol.member.domain.MemberRepository;
import com.jujeol.member.fixture.TestMember;
import javax.sql.DataSource;
import org.apache.mahout.cf.taste.impl.model.jdbc.SQL92JDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RecommendAcceptanceTest extends AcceptanceTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    MemberAcceptanceTool memberAcceptanceTool;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AdminAcceptanceTool adminAcceptanceTool;

    @Autowired
    DrinkAcceptanceTool drinkAcceptanceTool;

    @Test
    @DisplayName("hi")
    public void recommend() throws Exception{

        final Long obId = 주류_등록(OB);
        final Long stellaId = 주류_등록(STELLA);
        final Long kgbId = 주류_등록(KGB);
        final Long tigerId = 주류_등록(TIGER_LEMON);
        final Long appleId = 주류_등록(APPLE);


        memberAcceptanceTool.선호도_등록(obId, 2.0, TestMember.PIKA);
        memberAcceptanceTool.선호도_등록(stellaId, 5.0, TestMember.PIKA);
        memberAcceptanceTool.선호도_등록(kgbId, 4.5, TestMember.PIKA);

        memberAcceptanceTool.선호도_등록(obId, 1.5, TestMember.SOLONG);
        memberAcceptanceTool.선호도_등록(stellaId, 4.5, TestMember.SOLONG);
        memberAcceptanceTool.선호도_등록(kgbId, 4.2, TestMember.SOLONG);
        memberAcceptanceTool.선호도_등록(tigerId, 5.0, TestMember.SOLONG);
        memberAcceptanceTool.선호도_등록(appleId, 4.5, TestMember.SOLONG);

        memberAcceptanceTool.선호도_등록(obId, 1.0, TestMember.WEDGE);
        memberAcceptanceTool.선호도_등록(kgbId, 1.0, TestMember.WEDGE);
        memberAcceptanceTool.선호도_등록(tigerId, 4.7, TestMember.WEDGE);
        memberAcceptanceTool.선호도_등록(appleId, 4.0, TestMember.WEDGE);

        memberAcceptanceTool.선호도_등록(obId, 4.7, TestMember.CROFFLE);
        memberAcceptanceTool.선호도_등록(kgbId, 1.5, TestMember.CROFFLE);
        memberAcceptanceTool.선호도_등록(stellaId, 2.4, TestMember.CROFFLE);
        memberAcceptanceTool.선호도_등록(tigerId, 2.1, TestMember.CROFFLE);


        // 데이터를 어디서 가져올래?
        // infra layer => profile
        // member or recommend

        // 하나의 데이터 묶음집이다.
        DataModel dataModel = new SQL92JDBCDataModel(dataSource, "preference", "member_id", "drink_id", "rate", null);

        final UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
        // 맛 기준으로 유저간 서로 나눠지겠찌?
        // 맛 기준으로 아이템간 서로 나눠짐.
        final double 수치 = similarity.userSimilarity(1, 2);

        // A - B - C      A-C => 가중치,
        UserNeighborhood userNeighborhood = new ThresholdUserNeighborhood(0.1, similarity, dataModel);


        // Recommender (아이템 기반, 유저 기반)
        UserBasedRecommender userBasedRecommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);

//        DataModel dataModel = new FileDataModel(new File(classLoader.getResource("dataset_grade.csv").getFile()));

        // 상품쪽으로 가는 거잖아. 그러면 이거는 솔직히 추천이라고 해봤자 카테고리 가중치만 둬서 카테고리 추천정도?
        // 맛 기반 추천 (상품 유사성으로 추천해주기)

        // 도메인 => 멤버 아이디,

        // 협업필터링 유저 기반
        // 협업필터링 자체는 무조건 유저를 비교하기 떄문에 아이템 기반이든 유저 선호도 기반이든 다 똑같이 에러가 생기고

        // Similarity -> item, user(맛이 비슷한 사람들끼리 묶어) -> 아이템 ,태그(경우의 수), 1,0(유사) => 태그, 음료
        // Similarity -> 협업 필터링 (유저간의 비슷 한정도를 찾는 거?), 아이템 기반 필터링()
        // neighborhood -> item, user
        // basedRecommender -> item, user


        // 각 유저가 좋아하는 술이 들어있으니까. (술에 담긴 맛들이 존재함) 1xN(술) => 1xN(맛 기준) (행렬 변환) 테이블 형식으로 바꿔주는 알고리즘이 들어있겠지.




        for (RecommendedItem recommendedItem : userBasedRecommender.recommend(1, 10)) {
            System.out.println("#########################");
            System.out.println(recommendedItem.getItemID());
            System.out.println(recommendedItem.getValue());
            System.out.println("#########################");
        }
        // 인덱싱 도입
    }

    private Long 주류_등록(DrinkTestContainer drinkTestContainer) {
        adminAcceptanceTool.어드민_주류_데이터_등록(drinkTestContainer);
        return drinkAcceptanceTool.주류_아이디_조회(drinkTestContainer.getName());
    }
}
