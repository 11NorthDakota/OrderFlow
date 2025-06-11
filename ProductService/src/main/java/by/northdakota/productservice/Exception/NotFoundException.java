package by.northdakota.productservice.Exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id,Class<?> clazz) {
        super(clazz.getName() + " with id " + id + " not found");
    }

    public NotFoundException(String sku,Class<?> clazz) {
        super(clazz.getName() + " with sku " + sku + " not found");
    }

}
