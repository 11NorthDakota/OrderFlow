package by.northdakota.productservice.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto {

    private String name;
    @NotBlank
    private String sku; //stock_keeping_unit(code)
    private BigDecimal price;
    private String description;

}
