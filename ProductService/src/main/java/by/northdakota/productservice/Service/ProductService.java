package by.northdakota.productservice.Service;

import by.northdakota.productservice.Dto.CreateProductDto;
import by.northdakota.productservice.Dto.ProductValidationRequest;
import by.northdakota.productservice.Dto.ProductValidationResponse;
import by.northdakota.productservice.Dto.ResponseProductDto;

import java.util.List;

public interface ProductService {
    List<ResponseProductDto> getAllProducts();
    ResponseProductDto getProductById(Long id);
    Long createProduct(CreateProductDto product);
    ResponseProductDto updateProduct(Long id, CreateProductDto product);
    void deleteProduct(Long id);
    ProductValidationResponse validateProducts(ProductValidationRequest request);
}
