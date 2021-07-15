package com.jujeol.admin.ui;

import com.jujeol.admin.PageResponseAssembler;
import com.jujeol.admin.ui.dto.AdminDrinkResponse;
import com.jujeol.commons.dto.CommonResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final DrinkService drinkService;


    @GetMapping("/drinks")
    public ResponseEntity<CommonResponseDto<List<AdminDrinkResponse>>> drinks(Pageable pageable) {
        Page<AdminDrinkResponse> drinkResponses = drinkService.findDrinks(pageable);
        final CommonResponseDto<List<AdminDrinkResponse>> response = PageResponseAssembler
                .assemble(drinkResponses);
        return ResponseEntity.ok(response);
    }
}
