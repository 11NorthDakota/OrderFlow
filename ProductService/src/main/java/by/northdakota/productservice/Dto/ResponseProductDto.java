package by.northdakota.productservice.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProductDto {

    private Long id;
    private String name;
    private String sku; //stock_keeping_unit(code)
    private BigDecimal price;
    private String description;

}
