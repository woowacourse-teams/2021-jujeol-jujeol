package com.jujeol.model;

import com.jujeol.feedback.domain.model.Review;
import com.jujeol.member.domain.model.Member;
import lombok.Getter;

@Getter
public class ReviewWithMember {

    private final Review review;
    private final Member member;

    public ReviewWithMember(Review review, Member member) {
        this.review = review;
        this.member = member;
    }

    public static ReviewWithMember create(Review review, Member member) {
        return new ReviewWithMember(review, member);
    }
}
