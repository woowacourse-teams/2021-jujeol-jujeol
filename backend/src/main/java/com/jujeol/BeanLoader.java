package com.jujeol;

import com.jujeol.elasticsearch.domain.reopsitory.DrinkDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BeanLoader implements CommandLineRunner {

    private final DrinkDocumentRepository drinkDocumentRepository;

    @Override
    public void run(String... args) throws Exception {

    }
}
