package by.northdakota.productservice.Service.Impl;

import by.northdakota.productservice.Dto.CreateProductDto;
import by.northdakota.productservice.Dto.ProductValidationRequest;
import by.northdakota.productservice.Dto.ProductValidationResponse;
import by.northdakota.productservice.Dto.ResponseProductDto;
import by.northdakota.productservice.Entity.Product;
import by.northdakota.productservice.Event.ProductEventImpl.CreateProductEvent;
import by.northdakota.productservice.Event.ProductEventImpl.DeleteProductEvent;
import by.northdakota.productservice.Exception.AlreadyExistsException;
import by.northdakota.productservice.Exception.NotFoundException;
import by.northdakota.productservice.Mapper.ProductMapper;
import by.northdakota.productservice.Repository.ProductRepository;
import by.northdakota.productservice.Service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ProductMapper productMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public List<ResponseProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        log.info("products count: count= {}",products.size());
        return productMapper.toResponseDto(products);
    }

    @Override
    @Transactional
    public ResponseProductDto getProductById(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            throw new NotFoundException(id,Product.class);
        }
        Product product = productOpt.get();
        log.info("product with id={} :{} ",product.getId(),product);
        return productMapper.toResponseDto(product);
    }

    @Override
    @Transactional
    public Long createProduct(CreateProductDto createProductDto) {
        if(productRepository.existsBySku(createProductDto.getSku())){
            throw new AlreadyExistsException(Product.class, createProductDto.getSku());
        }
        Product product = productMapper.toEntity(createProductDto);
        product = productRepository.save(product);
        log.info("product {} created",product);

        CreateProductEvent event = productMapper.toCreateProductEvent(product);
        eventPublisher.publishEvent(event);
        return product.getId();
    }

    @Override
    @Transactional
    public ResponseProductDto updateProduct(Long id, CreateProductDto createProductDto) {
        if(!productRepository.existsById(id)){
            throw new NotFoundException(createProductDto.getSku(),Product.class);
        }
        Product product = productMapper.toEntity(createProductDto);
        product = productRepository.save(product);
        log.info("product with sku={} updated",product.getSku());
        return productMapper.toResponseDto(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)){
            throw new NotFoundException(id,Product.class);
        }
        productRepository.deleteById(id);
        log.info("product with id={} deleted",id);

        DeleteProductEvent event = new DeleteProductEvent(id);
        eventPublisher.publishEvent(event);
    }

    @Override
    @Transactional
    public ProductValidationResponse validateProducts(ProductValidationRequest request) {
        List<Product> existingProducts = productRepository.findExistingProductByProductsId(request.getProductIds());
        List<Long> existingIds = existingProducts.stream()
                .map(Product::getId)
                .toList();

        List<Long> missingIds = request.getProductIds().stream()
                .filter(id -> !existingIds.contains(id))
                .toList();
        if(missingIds.isEmpty()){
            return new ProductValidationResponse(true,existingIds,missingIds);
        }
        return new ProductValidationResponse(false,missingIds,existingIds);
    }


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishProductCreateEvent(CreateProductEvent event) {
        kafkaTemplate.send("product-event", String.valueOf(event.getId()), event);
    }
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishProductDeleteEvent(DeleteProductEvent event) {
        kafkaTemplate.send("product-event", String.valueOf(event.getId()), event);
    }
}
