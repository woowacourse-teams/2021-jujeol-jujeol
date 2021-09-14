package com.jujeol.commons.exception;

import com.jujeol.drink.category.exception.NotFoundCategoryException;
import com.jujeol.drink.drink.exception.InvalidAlcoholByVolumeException;
import com.jujeol.drink.drink.exception.InvalidDescriptionException;
import com.jujeol.drink.drink.exception.InvalidDrinkNameException;
import com.jujeol.drink.drink.exception.InvalidEnglishNameException;
import com.jujeol.drink.drink.exception.InvalidSortByException;
import com.jujeol.drink.drink.exception.NotFoundDrinkException;
import com.jujeol.drink.drink.exception.NotFoundViewCountException;
import com.jujeol.member.auth.exception.InvalidTokenException;
import com.jujeol.member.auth.exception.KakaoAccessException;
import com.jujeol.member.auth.exception.UnauthorizedUserException;
import com.jujeol.member.member.exception.InvalidUserBiographyLengthException;
import com.jujeol.member.member.exception.InvalidUserNicknameCharacterException;
import com.jujeol.member.member.exception.InvalidUserNicknameLengthException;
import com.jujeol.member.member.exception.NoSuchMemberException;
import com.jujeol.review.exception.CreateReviewLimitException;
import com.jujeol.review.exception.CreateReviewNoPreferenceException;
import com.jujeol.review.exception.InvalidReviewContentException;
import com.jujeol.review.exception.NotExistReviewInDrinkException;
import com.jujeol.review.exception.NotFoundReviewException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.servlet.NoHandlerFoundException;

@AllArgsConstructor
@Getter
public enum ExceptionCodeAndDetails {
    NOT_FOUND_ERROR_CODE("0001", "발생한 에러의 에러코드를 찾을 수 없습니다.", NotFoundErrorCodeException.class),
    NOT_FOUND_API("0002", "해당 경로에 대한 응답 API를 찾을 수 없습니다.", NoHandlerFoundException.class),

    KAKAO_ACCESS("1002", "카카오 로그인 서버에 접근 중 예외가 발생했습니다.", KakaoAccessException.class),
    INVALID_TOKEN("1003", "access token이 유효하지 않습니다.", InvalidTokenException.class),
    NO_SUCH_MEMBER("1004", "해당 id의 유저가 없습니다.", NoSuchMemberException.class),
    UNAUTHORIZED_USER("1005", "권한이 없는 유저입니다.", UnauthorizedUserException.class),
    INVALID_USER_NICKNAME_LENGTH("1006", "닉네임이 비어있거나 너무 깁니다.",
            InvalidUserNicknameLengthException.class),
    INVALID_USER_NICKNAME_CHARACTER("1007", "닉네임에 잘 못 된 문자가 들어있습니다.",
            InvalidUserNicknameCharacterException.class),
    INVALID_USER_BIOGRAPHY_LENGTH("1008", "자기 소개가 너무 깁니다.",
            InvalidUserBiographyLengthException.class),

    INVALID_ALCOHOL_BY_VOLUME("2001", "해당 주류의 도수가 잘 못 되었습니다.",
            InvalidAlcoholByVolumeException.class),
    INVALID_DRINK_NAME("2002", "해당 주류의 이름이 형식에 맞지 않습니다.", InvalidDrinkNameException.class),
    NOT_FOUND_DRINK("2003", "해당하는 id의 상품을 찾을 수 없습니다.", NotFoundDrinkException.class),
    INVALID_ENGLISH_DRINK_NAME("2004", "해당 주류의 영문명이 형식에 맞지 않습니다.",
            InvalidEnglishNameException.class),
    NOT_FOUND_REVIEW("2005", "해당하는 id의 리뷰를 찾을 수 없습니다.", NotFoundReviewException.class),
    NOT_EXIST_REVIEW_IN_DRINK("2006", "해당 주류에 존재하지 않는 리뷰입니다.",
            NotExistReviewInDrinkException.class),
    CREATE_REVIEW_LIMIT("2007", "동일 상품에 대한 리뷰는 하루에 하나만 작성할 수 있습니다.",
            CreateReviewLimitException.class),
    INVALID_CONTENT_LENGTH("2008", "리뷰는 빈 공백이거나 300자를 넘을 수 없습니다.",
            InvalidReviewContentException.class),
    NOT_EXIST_CATEGORY("2009", "해당 카테고리가 존재하지 않습니다.", NotFoundCategoryException.class),

    NOT_FOUND_VIEW_COUNT("2011", "해당 주류에 대한 조회수를 찾을 수 없습니다.", NotFoundViewCountException.class),
    CREATE_REVIEW_NO_PREFERENCE("2012", "리뷰를 생성하려면 선호도를 입력해야합니다.",
            CreateReviewNoPreferenceException.class),
    INVALID_DESCRIPTION("2013", "유효하지 않은 상세설명입니다.",
            InvalidDescriptionException.class),
    INVALID_SORT_BY("2014", "유효하지 않은 정렬 기준입니다.",
            InvalidSortByException.class);

    private String code;
    private String message;
    private Class<? extends Exception> type;

    public static ExceptionCodeAndDetails findByClass(Class<? extends Exception> type) {
        return Arrays.stream(ExceptionCodeAndDetails.values())
                .filter(code -> code.type.equals(type))
                .findAny()
                .orElseThrow(NotFoundErrorCodeException::new);
    }

}
