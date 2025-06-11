package by.northdakota.productservice.Mapper;

import by.northdakota.productservice.Dto.CreateProductDto;
import by.northdakota.productservice.Dto.ResponseProductDto;
import by.northdakota.productservice.Entity.Product;
import by.northdakota.productservice.Event.ProductEventImpl.CreateProductEvent;
import by.northdakota.productservice.Event.ProductEventImpl.DeleteProductEvent;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    CreateProductDto toDto(Product product);
    Product toEntity(CreateProductDto createProductDto);
    List<CreateProductDto> toDto(List<Product> products);
    List<Product> toEntity(List<CreateProductDto> createProductDtos);
    ResponseProductDto toResponseDto(Product product);
    Product toEntity(ResponseProductDto responseProductDto);
    List<ResponseProductDto> toResponseDto(List<Product> products);

    CreateProductEvent toCreateProductEvent(Product product);
}
