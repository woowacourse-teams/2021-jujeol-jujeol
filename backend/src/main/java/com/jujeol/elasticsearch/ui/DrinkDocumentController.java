package com.jujeol.elasticsearch.ui;

import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.commons.dto.PageResponseAssembler;
import com.jujeol.drink.drink.application.dto.DrinkDto;
import com.jujeol.drink.drink.ui.dto.DrinkResponse;
import com.jujeol.elasticsearch.ui.dto.SearchRequest;
import com.jujeol.elasticsearch.application.DrinkDocumentService;
import com.jujeol.member.auth.ui.AuthenticationPrincipal;
import com.jujeol.member.auth.ui.LoginMember;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DrinkDocumentController {

    private final DrinkDocumentService documentService;

    @GetMapping("/search")
    public ResponseEntity<CommonResponse<List<DrinkResponse>>> showDrinksBySearch(
            @ModelAttribute SearchRequest searchRequest,
            @AuthenticationPrincipal LoginMember loginMember,
            @PageableDefault Pageable pageable
    ) {
        Page<DrinkDto> drinkDtos = documentService
                .showDrinksBySearch(searchRequest.toDto(), loginMember, pageable);

        return ResponseEntity
                .ok(PageResponseAssembler.assemble(drinkDtos.map(DrinkResponse::from)));
    }

    @GetMapping("/search/sync")
    public ResponseEntity<Void> sync() {
        documentService.sync();
        return ResponseEntity.ok().build();
    }
}
