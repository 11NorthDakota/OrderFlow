package by.northdakota.productservice.Controller;

import by.northdakota.productservice.Dto.CreateProductDto;
import by.northdakota.productservice.Dto.ProductValidationRequest;
import by.northdakota.productservice.Dto.ProductValidationResponse;
import by.northdakota.productservice.Dto.ResponseProductDto;
import by.northdakota.productservice.Service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ResponseProductDto>> getAllProducts(){
        log.info("Get all products");
        List<ResponseProductDto> responseProductDto = productService.getAllProducts();
        return new ResponseEntity<>(responseProductDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseProductDto> getProductById(@PathVariable Long id){
        log.info("Get product by id: {}", id);
        ResponseProductDto responseProductDto = productService.getProductById(id);
        return new ResponseEntity<>(responseProductDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createProduct(@RequestBody CreateProductDto product){
        log.info("Creating product with sku:{}", product.getSku());
        Long productId = productService.createProduct(product);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseProductDto> updateProduct(@PathVariable Long id,
                                                          @RequestBody CreateProductDto createProductDto){
        log.info("Updating product with id: {} and sku: {}", id, createProductDto.getSku());
        ResponseProductDto updateProductDto = productService.updateProduct(id, createProductDto);
        return new ResponseEntity<>(updateProductDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteProduct(@PathVariable Long id){
        log.info("Deleting product with id {}", id);
        productService.deleteProduct(id);
        return new ResponseEntity<>(id,HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<ProductValidationResponse> validateProducts(@RequestBody ProductValidationRequest request){
        ProductValidationResponse response = productService.validateProducts(request);
        log.info("validation result - existing: {}, missing {}",response.getExistingProductIds()
                ,response.getMissingProductIds());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
