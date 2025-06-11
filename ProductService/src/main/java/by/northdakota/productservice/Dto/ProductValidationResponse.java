package by.northdakota.productservice.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductValidationResponse {
    private boolean allExist;
    private List<Long> existingProductIds;
    private List<Long> missingProductIds;
}
