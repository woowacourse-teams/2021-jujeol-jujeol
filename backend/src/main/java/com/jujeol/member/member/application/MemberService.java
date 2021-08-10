package com.jujeol.member.member.application;

import com.jujeol.drink.drink.application.dto.DrinkDto;
import com.jujeol.member.member.application.dto.MemberDto;
import com.jujeol.member.member.domain.Biography;
import com.jujeol.member.member.domain.Member;
import com.jujeol.member.member.domain.nickname.Nickname;
import com.jujeol.member.member.domain.repository.MemberRepository;
import com.jujeol.member.member.exception.NoSuchMemberException;
import com.jujeol.preference.application.PreferenceService;
import com.jujeol.preference.domain.Preference;
import com.jujeol.review.application.dto.ReviewDto;
import com.jujeol.review.domain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    @Value("${file-server.url:}")
    private String fileServerUrl;

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final PreferenceService preferenceService;

    public MemberDto findMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(NoSuchMemberException::new);
        return MemberDto.create(member);
    }

    @Transactional
    public void updateMember(MemberDto memberDto) {
        Member member = memberRepository.findById(memberDto.getId())
                .orElseThrow(NoSuchMemberException::new);

        member.updateNicknameAndBiography(
                Nickname.create(memberDto.getNickname()),
                Biography.create(memberDto.getBio())
        );
    }

    public Page<DrinkDto> findDrinks(Long memberId, Pageable pageable) {
        final Page<DrinkDto> map = preferenceService
                .showPreferenceByMemberId(memberId, pageable)
                .map(preference -> DrinkDto.create(
                        preference.getDrink(),
                        preference,
                        fileServerUrl
                        )
                );
        return map;
    }

    public Page<ReviewDto> findReviews(Long memberId, Pageable pageable) {
        return reviewRepository.findReviewsOfMine(memberId, pageable)
                .map(review -> ReviewDto.create(
                        review,
                        DrinkDto.create(
                                review.getDrink(),
                                Preference.create(review.getDrink(), 3.5),
                                fileServerUrl
                        )
                ));
    }
}
