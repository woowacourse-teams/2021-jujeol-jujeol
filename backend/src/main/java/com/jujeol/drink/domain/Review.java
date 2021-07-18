package com.jujeol.drink.domain;

import com.jujeol.member.domain.Member;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
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
    @Embedded
    private ReviewContent content;

    @ManyToOne
    @JoinColumn(name = "drink_id")
    private Drink drink;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public static Review from(String content, Drink drink, Member member) {
        return new Review(null, new ReviewContent(content), drink, member, null, null);
    }

    public void toDrink(Drink drink) {
        this.drink = drink;
    }

    public void editContent(String content) {
        this.content = new ReviewContent(content);
    }

    public boolean isReviewOf(Drink drink) {
        return this.drink.equals(drink);
    }

    public boolean isAuthor(Member member) {
        return this.member.equals(member);
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        modifiedAt = null;
    }

    @PreUpdate
    public void preUpdate() {
        modifiedAt = LocalDateTime.now();
    }

    public String getContent() {
        return content.getContent();
    }

    public Long getMemberId() {
        return member.getId();
    }
}
