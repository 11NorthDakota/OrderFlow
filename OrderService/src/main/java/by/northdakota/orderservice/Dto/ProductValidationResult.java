package by.northdakota.orderservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductValidationResult {
    private boolean allExist;
    private List<Long> existingProductIds;
    private List<Long> missingProductIds;
}
