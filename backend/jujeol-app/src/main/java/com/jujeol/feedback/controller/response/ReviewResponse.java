package com.jujeol.feedback.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponse {

    private Long id;
    private MemberSimpleResponse author;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public ReviewResponse(Long id, MemberSimpleResponse author, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    @Getter
    public static class MemberSimpleResponse {
        private Long id;
        private String name;

        @Builder
        public MemberSimpleResponse(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
