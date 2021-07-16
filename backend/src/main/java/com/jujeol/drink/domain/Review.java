package com.jujeol.drink.domain;

import com.jujeol.member.domain.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "drink_id")
    private Drink drink;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public static Review from(String content, Drink drink, Member member) {
        return new Review(null, content, drink, member);
    }

    public void toDrink(Drink drink) {
        this.drink = drink;
    }

    public void editContent(String content) {
        this.content = content;
    }

    public boolean isReviewOf(Drink drink) {
        return this.drink.equals(drink);
    }

    public boolean isAuthor(Member member) {
        return this.member.equals(member);
    }
}
