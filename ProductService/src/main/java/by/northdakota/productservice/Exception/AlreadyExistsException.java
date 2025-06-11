package by.northdakota.productservice.Exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(Class<?> clazz,String stu) {
        super(clazz.getName() +" with "+stu + " already exists");
    }
}
