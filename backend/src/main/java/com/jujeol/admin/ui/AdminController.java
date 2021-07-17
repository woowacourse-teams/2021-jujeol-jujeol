package com.jujeol.admin.ui;

import com.jujeol.admin.PageResponseAssembler;
import com.jujeol.admin.ui.dto.AdminDrinkResponse;
import com.jujeol.admin.ui.dto.DrinkRequest;
import com.jujeol.commons.dto.CommonResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/drinks")
@RequiredArgsConstructor
public class AdminController {

    private final DrinkService drinkService;

    @GetMapping
    public ResponseEntity<CommonResponseDto<List<AdminDrinkResponse>>> drinks(Pageable pageable) {
        Page<AdminDrinkResponse> drinkResponses = drinkService.findDrinks(pageable);
        final CommonResponseDto<List<AdminDrinkResponse>> response = PageResponseAssembler
                .assemble(drinkResponses);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponseDto<Object>> bulkInsert(@RequestBody List<DrinkRequest> drinkRequests) {
        final List<AdminDrinkResponse> adminDrinkResponses = drinkService.saveDrinks(drinkRequests);
        return ResponseEntity.ok(CommonResponseDto.fromList(adminDrinkResponses));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeDrink(@PathVariable Long id) {
        drinkService.removeDrink(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public void updateDrink(@PathVariable Long id, @RequestBody DrinkRequest drinkRequest) {
        drinkService.updateDrink(id, drinkRequest);
    }
}
