package com.jujeol.elasticsearch;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@RequiredArgsConstructor
@Document(indexName = "drink")
@Setting(settingPath = "elastic-setting.json")
public class DrinkIndex {

    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Keyword)
    private String name;

    @Field(type = FieldType.Keyword)
    private String englishName;

    @Field(type = FieldType.Keyword)
    private String category;
}
